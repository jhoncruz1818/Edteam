CREATE TABLE clientes (
    firebase_uid     NVARCHAR(128)  NOT NULL,
    nombre           NVARCHAR(100)  NOT NULL,
    apellido_paterno NVARCHAR(100)  NOT NULL,
    apellido_materno NVARCHAR(100)  NOT NULL,
    correo           NVARCHAR(255)  NOT NULL,
    telefono         NVARCHAR(30)   NOT NULL,
    creado_en        DATETIME2      NOT NULL CONSTRAINT df_clientes_creado_en DEFAULT SYSUTCDATETIME(),
    CONSTRAINT pk_clientes PRIMARY KEY (firebase_uid),
    CONSTRAINT uk_clientes_correo UNIQUE (correo)
);
