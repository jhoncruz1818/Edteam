CREATE TABLE productos (
    id            BIGINT         NOT NULL AUTO_INCREMENT,
    nombre        VARCHAR(200)   NOT NULL,
    descripcion   TEXT           NOT NULL,
    precio        DECIMAL(10, 2) NOT NULL,
    fecha         DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    imagen        VARCHAR(500)   NOT NULL,
    vendedor_uid  VARCHAR(128)   NOT NULL,
    PRIMARY KEY (id),
    KEY idx_productos_fecha (fecha),
    KEY idx_productos_vendedor (vendedor_uid),
    CONSTRAINT fk_productos_vendedor
        FOREIGN KEY (vendedor_uid) REFERENCES clientes (firebase_uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
