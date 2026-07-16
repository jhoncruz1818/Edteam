package com.edteam.backend.cliente;

import com.edteam.backend.security.AuthContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse create(@Valid @RequestBody ClienteRequest request, HttpServletRequest httpRequest) {
        String uid = currentUid(httpRequest);

        if (clienteRepository.existsById(uid)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El cliente ya existe");
        }

        Cliente cliente = new Cliente();
        cliente.setFirebaseUid(uid);
        cliente.setNombre(request.nombre().trim());
        cliente.setApellidoPaterno(request.apellidoPaterno().trim());
        cliente.setApellidoMaterno(request.apellidoMaterno().trim());
        cliente.setCorreo(request.correo().trim().toLowerCase());
        cliente.setTelefono(request.telefono().trim());
        cliente.setCreadoEn(LocalDateTime.now());

        return ClienteResponse.from(clienteRepository.save(cliente));
    }

    @GetMapping("/me")
    public ClienteResponse me(HttpServletRequest httpRequest) {
        String uid = currentUid(httpRequest);
        return clienteRepository.findById(uid)
            .map(ClienteResponse::from)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }

    @GetMapping("/{firebaseUid}")
    public ClienteResponse getByUid(@PathVariable String firebaseUid) {
        return clienteRepository.findById(firebaseUid)
            .map(ClienteResponse::from)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }

    private static String currentUid(HttpServletRequest request) {
        Object uid = request.getAttribute(AuthContext.FIREBASE_UID_ATTR);
        if (uid == null || uid.toString().isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autenticado");
        }
        return uid.toString();
    }
}
