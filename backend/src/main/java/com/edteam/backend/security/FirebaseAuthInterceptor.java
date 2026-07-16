package com.edteam.backend.security;

import com.edteam.backend.config.FirebaseProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class FirebaseAuthInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(FirebaseAuthInterceptor.class);

    private final FirebaseProperties properties;
    private final ObjectMapper objectMapper;

    public FirebaseAuthInterceptor(FirebaseProperties properties, ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Falta Authorization Bearer token");
            return false;
        }

        String idToken = header.substring(7).trim();
        if (idToken.isEmpty()) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token vacío");
            return false;
        }

        try {
            String uid = resolveUid(idToken);
            request.setAttribute(AuthContext.FIREBASE_UID_ATTR, uid);
            return true;
        } catch (Exception ex) {
            log.warn("Token Firebase inválido: {}", ex.getMessage());
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token inválido");
            return false;
        }
    }

    private String resolveUid(String idToken) throws Exception {
        if (properties.isVerifyTokens() && !FirebaseApp.getApps().isEmpty()) {
            FirebaseToken decoded = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return decoded.getUid();
        }

        // Modo local: decodifica el payload del JWT sin verificar firma.
        String[] parts = idToken.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("JWT mal formado");
        }
        byte[] decodedBytes = Base64.getUrlDecoder().decode(parts[1]);
        JsonNode payload = objectMapper.readTree(new String(decodedBytes, StandardCharsets.UTF_8));
        JsonNode sub = payload.get("user_id");
        if (sub == null || sub.asText().isBlank()) {
            sub = payload.get("sub");
        }
        if (sub == null || sub.asText().isBlank()) {
            throw new IllegalArgumentException("El token no contiene UID");
        }

        String audience = payload.path("aud").asText("");
        if (!audience.isBlank() && !audience.equals(properties.getProjectId())) {
            throw new IllegalArgumentException("Audience del token no coincide con el proyecto");
        }

        log.debug("UID resuelto en modo local (sin verificar firma): {}", sub.asText());
        return sub.asText();
    }
}
