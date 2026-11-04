-- Script de Creación y Carga Inicial de Datos Comunes

DROP TABLE IF EXISTS turnos;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS servicios;

CREATE TABLE clientes (
    codigo INTEGER PRIMARY KEY,
    nombre TEXT NOT NULL,
    telefono TEXT NOT NULL,
    estado TEXT NOT NULL
);

CREATE TABLE servicios (
    codigo INTEGER PRIMARY KEY,
    nombre TEXT NOT NULL,
    estado TEXT NOT NULL
);

CREATE TABLE turnos (
    numero INTEGER PRIMARY KEY AUTOINCREMENT,
    codigo_cliente INTEGER NOT NULL,
    codigo_servicio INTEGER NOT NULL,
    estado TEXT NOT NULL,
    fecha_generacion TEXT NOT NULL,
    fecha_atencion_cancelacion TEXT,
    FOREIGN KEY (codigo_cliente) REFERENCES clientes(codigo),
    FOREIGN KEY (codigo_servicio) REFERENCES servicios(codigo)
);

-- Registros Iniciales Comunes - Clientes
INSERT INTO clientes (codigo, nombre, telefono, estado) VALUES (1001, 'Ana López', '55501001', 'Activo');
INSERT INTO clientes (codigo, nombre, telefono, estado) VALUES (1002, 'Carlos Pérez', '55501002', 'Activo');
INSERT INTO clientes (codigo, nombre, telefono, estado) VALUES (1003, 'María García', '55501003', 'Activo');
INSERT INTO clientes (codigo, nombre, telefono, estado) VALUES (1004, 'José Hernández', '55501004', 'Activo');
INSERT INTO clientes (codigo, nombre, telefono, estado) VALUES (1005, 'Luisa Morales', '55501005', 'Activo');

-- Registros Iniciales Comunes - Servicios
INSERT INTO servicios (codigo, nombre, estado) VALUES (1, 'Información', 'Activo');
INSERT INTO servicios (codigo, nombre, estado) VALUES (2, 'Entrega de documentos', 'Activo');
INSERT INTO servicios (codigo, nombre, estado) VALUES (3, 'Actualización de datos', 'Activo');