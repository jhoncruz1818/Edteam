package com.edteam.backend.producto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductoRequest(
    @NotBlank String nombre,
    @NotBlank String descripcion,
    @NotNull @DecimalMin("0.01") BigDecimal precio,
    String imagen
) {
}
