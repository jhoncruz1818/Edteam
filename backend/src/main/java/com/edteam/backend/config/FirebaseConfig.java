package com.edteam.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@EnableConfigurationProperties(FirebaseProperties.class)
public class FirebaseConfig {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConfig.class);

    private final FirebaseProperties properties;

    public FirebaseConfig(FirebaseProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) {
            return;
        }

        if (properties.getCredentialsPath() == null || properties.getCredentialsPath().isBlank()) {
            log.warn(
                "Firebase credentials no configuradas. "
                    + "verify-tokens={} — en producción configura FIREBASE_CREDENTIALS.",
                properties.isVerifyTokens()
            );
            return;
        }

        try (FileInputStream serviceAccount = new FileInputStream(properties.getCredentialsPath())) {
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId(properties.getProjectId())
                .build();
            FirebaseApp.initializeApp(options);
            log.info("Firebase Admin inicializado para proyecto {}", properties.getProjectId());
        }
    }
}
