CREATE TABLE productos (
    id            BIGINT IDENTITY(1,1) NOT NULL,
    nombre        NVARCHAR(200)        NOT NULL,
    descripcion   NVARCHAR(MAX)        NOT NULL,
    precio        DECIMAL(10, 2)       NOT NULL,
    fecha         DATETIME2            NOT NULL CONSTRAINT df_productos_fecha DEFAULT SYSUTCDATETIME(),
    imagen        NVARCHAR(500)        NOT NULL,
    vendedor_uid  NVARCHAR(128)        NOT NULL,
    CONSTRAINT pk_productos PRIMARY KEY (id),
    CONSTRAINT fk_productos_vendedor
        FOREIGN KEY (vendedor_uid) REFERENCES clientes (firebase_uid)
);

CREATE INDEX idx_productos_fecha ON productos (fecha);
CREATE INDEX idx_productos_vendedor ON productos (vendedor_uid);
