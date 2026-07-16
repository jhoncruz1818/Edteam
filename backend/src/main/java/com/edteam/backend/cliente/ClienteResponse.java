package com.edteam.backend.cliente;

import java.time.LocalDateTime;

public record ClienteResponse(
    String firebaseUid,
    String nombre,
    String apellidoPaterno,
    String apellidoMaterno,
    String correo,
    String telefono,
    LocalDateTime creadoEn
) {
    public static ClienteResponse from(Cliente cliente) {
        return new ClienteResponse(
            cliente.getFirebaseUid(),
            cliente.getNombre(),
            cliente.getApellidoPaterno(),
            cliente.getApellidoMaterno(),
            cliente.getCorreo(),
            cliente.getTelefono(),
            cliente.getCreadoEn()
        );
    }
}
