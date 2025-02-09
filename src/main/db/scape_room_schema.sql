CREATE DATABASE IF NOT EXISTS escape_room_db;
USE escape_room_db;

-- Tabla EscapeRoom (experiencia general)
CREATE TABLE IF NOT EXISTS escape_room (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla Room (Salas dentro del Escape Room)
CREATE TABLE IF NOT EXISTS room (
    id INT AUTO_INCREMENT PRIMARY KEY,
    escape_room_id INT,
    name VARCHAR(255) NOT NULL,
    difficulty_level ENUM('EASY', 'MEDIUM', 'HARD') NOT NULL,
    price DOUBLE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_room_escape_room_id FOREIGN KEY (escape_room_id) REFERENCES escape_room(id) ON DELETE SET NULL
);

-- Tabla Hint (Pistas dentro de una Sala)
CREATE TABLE IF NOT EXISTS hint (
    id INT AUTO_INCREMENT PRIMARY KEY,
    room_id INT NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

-- Tabla Decoration (Objetos decorativos en una Sala)
CREATE TABLE IF NOT EXISTS decoration (
    id INT AUTO_INCREMENT PRIMARY KEY,
    room_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    material VARCHAR(255),
    price DOUBLE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

-- Tabla Client (Usuarios que participan en EscapeRoom)
CREATE TABLE IF NOT EXISTS client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    is_subscribed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla Room_Client (Relación Muchos a Muchos entre Room y Client)
CREATE TABLE IF NOT EXISTS room_client (
    client_id INT NOT NULL,
    room_id INT NOT NULL,
    start_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    end_time DATETIME NULL,
    completed BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (client_id, room_id),
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

-- Tabla Ticket (Registro de Compra de Entradas)
CREATE TABLE IF NOT EXISTS ticket (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT NOT NULL,
    room_id INT NOT NULL,
    total_price DOUBLE NOT NULL,
    purchase_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);