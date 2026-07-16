package com.edteam.backend.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(
    @NotBlank String nombre,
    @NotBlank String apellidoPaterno,
    @NotBlank String apellidoMaterno,
    @NotBlank @Email String correo,
    @NotBlank String telefono
) {
}
