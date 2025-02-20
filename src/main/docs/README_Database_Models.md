# Creación de Modelos y Clases en Java

## 1. Descripción del documento
- Este documento detalla la implementación de los modelos en Java basados en la estructura de la base de datos `escape_room_db`.
- Cada modelo se diseñó como una clase Java dentro del paquete `models`, reflejando las entidades y relaciones de la base de datos.

---

## 2. Modelos Implementados

Se han creado las siguientes clases dentro del paquete `models`, cada una correspondiendo a una tabla en la base de datos:

### **1. EscapeRoom**
- Representa una experiencia general de escape room.
- Atributos principales:
  - `id`
  - `name`
  - `createdAt`
  - `updatedAt`

### **2. Room**
- Define las salas individuales dentro de un escape room.
- Relación con `EscapeRoom` mediante `escapeRoomId`.
- Atributos principales:
  - `id`
  - `escapeRoomId`
  - `name`
  - `difficultyLevel`
  - `price`
  - `createdAt`
  - `updatedAt`

### **3. Hint**
- Representa las pistas disponibles en cada sala.
- Relación con `Room` mediante `roomId`.
- Atributos principales:
  - `id`
  - `roomId`
  - `description`
  - `price`
  - `createdAt`

### **4. Decoration**
- Representa los objetos decorativos en cada sala.
- Relación con `Room` mediante `roomId`.
- Atributos principales:
  - `id`
  - `roomId`
  - `name`
  - `material`
  - `price`
  - `createdAt`

### **5. Client**
- Representa a los usuarios registrados en el sistema.
- Atributos principales:
  - `id`
  - `name`
  - `email`
  - `isSubscribed`
  - `createdAt`
  - `updatedAt`

### **6. RoomClient**
- Representa la relación de muchos a muchos entre `Room` y `Client`.
- Atributos principales:
  - `clientId`
  - `roomId`
  - `startTime`
  - `endTime`
  - `completed`

### **7. Ticket**
- Registra la compra de entradas por parte de los clientes.
- Relación con `Client` y `Room` mediante `clientId` y `roomId`.
- Atributos principales:
  - `id`
  - `clientId`
  - `roomId`
  - `totalPrice`
  - `purchaseDate`

---

## 3. Instalación y Uso

### **Estructura del Proyecto**
Cada modelo está ubicado en el paquete `models`. Se recomienda la siguiente organización del proyecto:

```
src/
 ├── main/
 │   ├── java/
 │   │   ├── models/
 │   │   │   ├── EscapeRoom.java
 │   │   │   ├── Room.java
 │   │   │   ├── Hint.java
 │   │   │   ├── Decoration.java
 │   │   │   ├── Client.java
 │   │   │   ├── RoomClient.java
 │   │   │   ├── Ticket.java
```

### **Configuración y Compilación**
Para compilar el proyecto, usar Maven o Gradle según la configuración del entorno. En una configuración básica con Maven:

1. Asegurarse de que las dependencias necesarias están en `pom.xml` (por ejemplo, MySQL Connector).
2. Compilar el proyecto con:
   ```sh
   mvn clean install
   ```
3. Ejecutar la aplicación asegurándose de que las clases estén correctamente enlazadas.

---

## 4. Próximos Pasos

- Implementar **repositorios y servicios** para la interacción con la base de datos.
- Definir **DTOs (Data Transfer Objects)** para estructurar mejor la comunicación entre capas.
- Integrar el manejo de **excepciones personalizadas** para validaciones y errores de negocio.

---
