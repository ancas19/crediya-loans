
CREATE TABLE estado (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL UNIQUE,
    descripcion VARCHAR UNIQUE,
    u_creacion VARCHAR,
    u_actualizacion VARCHAR,
    f_creacion TIMESTAMP,
    f_actualizacion TIMESTAMP
);

CREATE TABLE tipo_prestamo (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR NOT NULL UNIQUE,
    monto_minimo NUMERIC,
    monto_maximo NUMERIC,
    tasa_interes NUMERIC,
    validacion_automatica VARCHAR,
    u_creacion VARCHAR,
    u_actualizacion VARCHAR,
    f_creacion TIMESTAMP,
    f_actualizacion TIMESTAMP
);

CREATE TABLE solicitud (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    monto NUMERIC,
    plazo NUMERIC,
    identification VARCHAR,
    id_estado UUID NOT NULL,
    tipo_prestamos UUID NOT NULL,
    u_creacion VARCHAR,
    u_actualizacion VARCHAR,
    f_creacion TIMESTAMP,
    f_actualizacion TIMESTAMP,
    CONSTRAINT fk_solicitud_estado FOREIGN KEY (id_estado) REFERENCES estado (id),
    CONSTRAINT fk_solicitud_tipoprestamo FOREIGN KEY (tipo_prestamos)REFERENCES tipo_prestamo (id)
);

CREATE INDEX idx_estado_name ON estado(name);
CREATE INDEX idx_tipo_prestamo_nombre ON tipo_prestamo(nombre);
CREATE INDEX idx_solicitud_id_estado ON solicitud(id_estado);
CREATE INDEX idx_solicitud_tipo_prestamos ON solicitud(tipo_prestamos);
CREATE INDEX idx_solicitud_identification ON solicitud(identification);