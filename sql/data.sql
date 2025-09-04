-- Example for estado
INSERT INTO estado (name, descripcion, u_creacion, u_actualizacion, f_creacion, f_actualizacion)
VALUES
  ('APROBADO', 'Estado aprobado', 'ADMIN', null, NOW(),NULL),
  ('PENDIENTE', 'Pendiente revisión', 'ADMIN', null, NOW(),NULL),
  ('RECHAZADO', 'Estado rechazado', 'ADMIN', NULL, NOW() ,NULL);

-- Example for tipo_prestamo
INSERT INTO tipo_prestamo (nombre, monto_minimo, monto_maximo, tasa_interes, validacion_automatica, u_creacion, u_actualizacion, f_creacion, f_actualizacion)
VALUES
  ('PERSONAL', 50000, 250000, 5.5, 'SI', 'ADMIN', NULL, NOW(), NULL),
  ('EMPRENDIMIENTO', 3000000, 10000000, 5.5, 'SI', 'ADMIN', NULL, NOW(), NULL),
  ('CONSUMO', 500000, 2500000, 3.2, 'NO', 'ADMIN', NULL, NOW(), NULL);