📌 Resumen de Mejoras en la Estructura del Proyecto

1️⃣ Objetivo Principal
✔ Modularizar Main.java para que solo ejecute la aplicación, delegando la inicialización a una nueva clase AppRunner.
✔ Mantener la funcionalidad intacta sin romper dependencias ni afectar el flujo actual.

2️⃣ Cambios Realizados
✅ 1. Creación de AppRunner
Se creó la clase AppRunner, que inicializa la aplicación y ejecuta el menú.
Ahora Main.java solo llama a AppRunner.start(), evitando que maneje lógica innecesaria.

✅ 2. Eliminación de AppInitializer en MenuManagement
Se intentó eliminar AppInitializer de MenuManagement, pero vimos que RoomManagement, DecorationManagement y HintManagement lo necesitaban.
Decisión final: Se mantuvo AppInitializer en esos módulos para no afectar la funcionalidad.

✅ 3. Validación de Conexión y Base de Datos
Probamos la aplicación y confirmamos que:
runScheme() se ejecuta correctamente.
La base de datos se conecta sin errores.
Se pueden agregar y eliminar salas, pistas, decoraciones y tickets.
Las notificaciones (Observer) funcionan cuando un cliente se suscribe o desuscribe.

3️⃣ Conclusión
✔ Main.java ahora solo ejecuta AppRunner, mejorando la modularidad.
✔ AppInitializer sigue en RoomManagement, DecorationManagement y HintManagement para evitar errores.
✔ Se hicieron pruebas y todo funciona correctamente.