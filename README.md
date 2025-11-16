M6 Evaluación Final - Gestión de Productos

Este proyecto es una aplicación Android nativa desarrollada como parte de la Evaluación Final del Módulo 6. La aplicación permite gestionar un inventario de productos, interactuando tanto con una API remota para las operaciones en tiempo real como con una base de datos local (Room) para la persistencia de datos y el funcionamiento offline.

Repositorio: https://github.com/Halbarad/M6_Evaluacion_Final


Información Básica

La aplicación implementa un CRUD (Crear, Leer, Actualizar, Borrar) completo para la entidad "Producto". Los usuarios pueden ver una lista de productos, crear nuevos, editar los existentes y eliminarlos. La arquitectura está diseñada para ser modular y escalable, siguiendo las guías recomendadas por Google para el desarrollo de Android moderno.

Tecnologías Principales:

•Lenguaje: Kotlin
•Arquitectura: MVVM (Model-View-ViewModel)
•UI: Android Views con ViewBinding
•Navegación: Navigation Component para gestionar el flujo entre pantallas.
•Networking: Retrofit para las llamadas a la API REST.
•Persistencia Local: Room para la base de datos local.
•Asincronía: Coroutines de Kotlin para manejar operaciones en segundo plano.
•Inyección de Dependencias (conceptual): AndroidViewModel para gestionar el ciclo de vida y las dependencias de forma limpia.


Autores

Este proyecto fue desarrollado por:

•Antonio Badilla
•Alejandra Flaño
•Sebastián Ramírez


Instrucciones de Ejecución

Sigue estas instrucciones para ejecutar la aplicación.

En la primera vista se mostrarán la lista de productos creados. El botón de la esquina inferior izquierda permite cambiar entre API REST y Base de datos local.

1. Crear un producto
Para crear un producto se debe hacer click en el botón flotante de la esquina inferior derecha "+", navegará hasta a la vista de creación del producto. En esta ventana se deben rellenar los campos y hacer click en el botón "Crear"

2. Actualizar un producto
Para actualizar un producto se debe hacer click en el botón con el icono de un lápiz, navegará hasta la vista actualizar en donde se podrá editar los campos del producto. Para finalizar la edición se debe hacer click en "Actualizar".

3. Eliminar un producto
Para eliminar un producto se debe hacer click en el botón con el icono de un basurero que eliminará el producto.


2. Probar la Aplicación

El proyecto está configurado para soportar tanto tests unitarios como tests de instrumentación.

•Tests Unitarios: Se encuentran en el directorio app/src/test/java. Estos tests se ejecutan en la JVM local y son ideales para probar la lógica del ViewModel y el Repository sin necesidad de un dispositivo Android.
•Tests de Instrumentación: Se encuentran en el directorio app/src/androidTest/java. Estos tests se ejecutan en un emulador o dispositivo físico y son para probar la UI (interacciones con Fragments) y la base de datos Room.

Para ejecutar los tests:

1.En la vista de proyecto de Android Studio, haz clic derecho sobre la carpeta de tests que desees ejecutar (ej: test o androidTest).
2.Selecciona la opción "Run 'Tests in '...'".
3.Los resultados se mostrarán en la ventana de "Run".


3. Generar el AAB para Producción

Un Android App Bundle (.aab) es el formato estándar para publicar aplicaciones en Google Play. Este paquete incluye todo el código y los recursos de tu app, pero delega la generación del AAB final a Google Play, optimizándolo para cada dispositivo.

1.Abrir el Asistente: En el menú de Android Studio, ve a Build > Generate Signed Bundle / APK....

2.Seleccionar el Formato:
◦Elige la opción "Android App Bundle".
◦Haz clic en "Next".

3.Configurar la Clave de Firma (Keystore): Google Play requiere que todas las apps estén firmadas digitalmente con una clave privada.
◦Si ya tienes una clave: Selecciona "Choose existing..." y navega hasta tu archivo de keystore (.jks o .keystore), introduce las contraseñas correspondientes.
◦Si no tienes una clave (primera vez):
a.Selecciona "Create new...".
b.Key store path: Elige una ubicación en tu computadora para guardar este archivo. ¡Guárdalo en un lugar seguro y no lo pierdas!
c.Password: Crea una contraseña segura para el keystore.
d.Key alias: Dale un nombre a tu clave (ej: upload-key).
e.Password: Crea una contraseña para la clave (puede ser la misma que la del keystore).
f.Rellena el resto de la información del certificado (nombre, organización, etc.).
g.Haz clic en "OK".

4.Generar el Bundle:
◦Una vez seleccionada la clave, haz clic en "Next".
◦En la siguiente pantalla, elige la variante de compilación release.
◦Haz clic en el botón "Finish".

Gradle comenzará el proceso de compilación. Una vez terminado, aparecerá una notificación en Android Studio con un enlace para "locate" (localizar) el archivo generado. El archivo .aab se encontrará en el directorio app/release/.
