package com.edteam.backend.producto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductoResponse(
    Long id,
    String nombre,
    String descripcion,
    BigDecimal precio,
    LocalDateTime fecha,
    String imagen,
    String vendedorUid
) {
    public static ProductoResponse from(Producto producto) {
        return new ProductoResponse(
            producto.getId(),
            producto.getNombre(),
            producto.getDescripcion(),
            producto.getPrecio(),
            producto.getFecha(),
            producto.getImagen(),
            producto.getVendedorUid()
        );
    }
}
