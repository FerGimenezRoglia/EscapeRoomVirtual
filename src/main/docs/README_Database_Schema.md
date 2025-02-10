# Creación del Esquema de Base de Datos

## 1. Descripción del documento
- Este documento detalla el proceso de creación del esquema de la base de datos **`escape_room_db`**. Contiene la estructura de las tablas principales y sus relaciones.
- El esquema está definido en el archivo **`schema.sql`**, que permite la creación automática de la base de datos y sus tablas en MySQL.

---

## 2. Creación del Esquema en MySQL

- Para estructurar la base de datos, se definieron las siguientes tablas principales:
  - **`escape_room`**: Representa una experiencia general de escape room.
  - **`room`**: Define las salas individuales dentro de un escape room.
  - **`hint`**: Contiene las pistas disponibles para cada sala.
  - **`decoration`**: Almacena objetos decorativos presentes en una sala.
  - **`client`**: Registra a los usuarios que participan en el escape room.
  - **`room_client`**: Define la relación de muchos a muchos entre `room` y `client`.
  - **`ticket`**: Representa la compra de entradas para participar en una sala.

### **Estructura de la Base de Datos**

- Se implementaron **claves primarias y foráneas** para establecer las relaciones entre tablas.
- Se definieron **restricciones** como `NOT NULL`, `AUTO_INCREMENT` y `DEFAULT` para garantizar la integridad de los datos.

---

## 3. Instalación y Uso

### **Requisitos Previos**
- Tener instalado **MySQL** en el sistema.
- Contar con acceso a una base de datos MySQL y permisos para ejecutar scripts SQL.

### **Ejecución del Esquema**
Para crear la base de datos y sus tablas, seguir estos pasos:

1. **Abrir MySQL desde la terminal**:
   ```sh
   mysql -u tu_usuario -p
   ```
   *(Reemplaza `tu_usuario` con el nombre de usuario de MySQL. Luego, ingresa tu contraseña cuando lo solicite.)*

2. **Ejecutar el script de creación de base de datos y tablas**:
   ```sql
   SOURCE /ruta/al/schema.sql;
   ```
   *(Reemplazar `/ruta/al/schema.sql` con la ubicación real del archivo `schema.sql`.)*

3. **Verificar que la base de datos se creó correctamente**:
   ```sql
   SHOW DATABASES;
   USE escape_room_db;
   SHOW TABLES;
   ```

### **Prueba de la Base de Datos**
Para asegurarse de que la base de datos y sus tablas funcionan correctamente:

1. **Verificar la estructura de una tabla específica**, por ejemplo `room`:
   ```sql
   DESCRIBE room;
   ```
2. **Insertar un dato de prueba en `client`**:
   ```sql
   INSERT INTO client (name, email, is_subscribed) VALUES ('John Doe', 'john@example.com', true);
   ```
3. **Consultar los datos insertados**:
   ```sql
   SELECT * FROM client;
   ```

---

## 4. Validación y Solución de Errores

- **Error: "Unknown database 'escape_room_db'"** → Asegurarse de ejecutar `CREATE DATABASE escape_room_db;` antes de importar el esquema.
- **Error de permisos** → Verificar que el usuario de MySQL tenga privilegios suficientes con `GRANT ALL PRIVILEGES`.
- **Errores en claves foráneas** → Comprobar que las tablas padre se crean antes de las tablas dependientes.

---

## 5. Estado Final del Proyecto

Tras la ejecución del esquema SQL, se garantiza que:
1. La base de datos **`escape_room_db`** está correctamente creada.
2. Todas las tablas y relaciones están estructuradas conforme al modelo relacional.
3. Se pueden insertar registros sin conflictos de integridad.
4. La base de datos está lista para su integración con la aplicación en Java.

---

## 6. Próximos Pasos

- **Implementar la carga inicial de datos** con valores por defecto en algunas tablas.
- **Automatizar la ejecución de `schema.sql` desde Java**, evitando la necesidad de ejecución manual.
- **Optimizar las relaciones y restricciones** según los requisitos del sistema final.

---
