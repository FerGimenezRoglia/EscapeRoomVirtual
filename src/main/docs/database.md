# DatabaseConnection 

## 1. Descripción del documento
- Actualmente, el sistema permite la conexión con MySQL, el manejo de excepciones personalizadas y la gestión de logs mediante Log4J. Se sigue una estructura organizada donde cada componente cumple una responsabilidad específica.

---

## 2. Configuración de la Conexión a la Base de Datos

- Para garantizar que la aplicación pueda interactuar correctamente con MySQL, se desarrolló la clase **`DatabaseConnection`**, que se encarga de gestionar la conexión a la base de datos. Se implementó el **patrón Singleton** para asegurar que solo haya una única instancia de conexión en toda la aplicación.
- Dentro de **`DatabaseConnection`**, se definieron los atributos necesarios para conectarse a MySQL, como la URL de la base de datos, el usuario y la contraseña. Se utilizó **JDBC (Java Database Connectivity)** para establecer la conexión, y se incorporaron mecanismos de manejo de excepciones para capturar errores en caso de fallos en la conexión.
  - Para probar la funcionalidad de esta conexión, se ejecutó la aplicación y se identificaron varios errores, como la ausencia del controlador JDBC (`ClassNotFoundException`), problemas de autenticación en MySQL (`Access denied for user 'root'@'localhost'`) y la inexistencia de la base de datos (`Unknown database 'escape_room_db'`). Estos problemas fueron resueltos ajustando la configuración, asegurando que la base de datos estuviera correctamente creada y verificando las credenciales de acceso.

---

## 3. Manejo de Excepciones

- Para mantener un código más limpio y facilitar la identificación de errores, se creó la clase **`DatabaseException`**, que extiende `RuntimeException`. Su propósito es capturar errores relacionados con la conexión a MySQL y lanzar excepciones personalizadas que permitan identificar más fácilmente la causa del problema.
- Este enfoque evita el uso de múltiples `try-catch` en el código, centralizando la gestión de errores en una única clase. Gracias a esto, cuando ocurre un fallo en la conexión o en la ejecución de una consulta SQL, se captura en `DatabaseConnection` y se lanza una `DatabaseException` con un mensaje claro sobre lo sucedido.

---

## 4. Implementación de `Test.App` y `Main`

- Para estructurar mejor el flujo de ejecución, se desarrollaron dos clases principales: **`Test.App`** y **`Main`**.
- La clase **`Test.App`** es la encargada de orquestar el funcionamiento de la aplicación. Su principal responsabilidad es ejecutar la conexión a la base de datos y verificar que esta se establezca correctamente. Se diseñó un método llamado `testConexion()` que permite realizar una prueba rápida de la conexión a MySQL, asegurando que se pueda interactuar con la base de datos antes de continuar con el desarrollo del sistema.
- Por otro lado, la clase **`Main`** es el punto de entrada del programa. Su única función es instanciar `Test.App` y ejecutar `run()`, manteniendo el principio de separación de responsabilidades y evitando agregar lógica en el método `main()`. Esto asegura que `Main` solo actúe como iniciador de la aplicación, mientras que `Test.App` maneja el flujo real de ejecución.

---

## 5. Implementación de Logs con Log4J

- En lugar de utilizar `System.out.println()` para imprimir mensajes en la consola, se implementó **Log4J 2** como sistema de gestión de logs. Este cambio permite registrar eventos importantes de la aplicación con diferentes niveles de severidad (`INFO`, `ERROR`, `DEBUG`), mejorando la depuración y facilitando el monitoreo del sistema.
- Se configuró un archivo **`log4j2.xml`** dentro de la carpeta `resources/`, donde se define cómo se manejan los logs. En esta configuración, los mensajes se imprimen en la consola con un formato claro, mostrando la fecha, el nivel del mensaje y la clase que lo generó. Gracias a esta implementación, cuando ocurre un error en la conexión con MySQL, Log4J lo captura y lo registra en lugar de depender de `System.out.println()`.
- Esto mejora la legibilidad de los mensajes de error y permite una mejor trazabilidad del sistema, especialmente cuando se ejecuta en entornos de producción.

---

## 6. Estado Final del Proyecto

Después de la implementación de estas mejoras, se logró:

1. **Conexión estable con MySQL**, garantizada mediante el patrón Singleton.
2. **Manejo centralizado de excepciones** para identificar problemas en la base de datos.
3. **Estructura clara de ejecución**, separando la lógica en `Test.App` y manteniendo `Main` solo como punto de entrada.
4. **Registro de eventos con Log4J 2**, eliminando la dependencia de `System.out.println()`.
5. **Verificación manual de la base de datos**, asegurando que `escape_room_db` estuviera correctamente creada y accesible desde Java.

---

## 7. Próximos Pasos

- El siguiente objetivo es implementar la **ejecución automática de `schema.sql` desde Java**, permitiendo que el esquema de la base de datos se cree automáticamente al iniciar la aplicación.
- Actualmente, `schema.sql` está en desarrollo por otro miembro del equipo. Cuando esté listo, se procederá a integrar su ejecución en la aplicación, asegurando que las tablas necesarias sean creadas correctamente sin necesidad de intervención manual.

---
