CREATE TABLE clientes (
    firebase_uid     VARCHAR(128)  NOT NULL,
    nombre           VARCHAR(100)  NOT NULL,
    apellido_paterno VARCHAR(100)  NOT NULL,
    apellido_materno VARCHAR(100)  NOT NULL,
    correo           VARCHAR(255)  NOT NULL,
    telefono         VARCHAR(30)   NOT NULL,
    creado_en        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (firebase_uid),
    UNIQUE KEY uk_clientes_correo (correo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
