# Sistema de Atención de Turnos en Oficina de Servicio

Este proyecto es una aplicación de consola en **Java 21** desarrollada bajo el patrón de arquitectura **MVC (Modelo-Vista-Controlador) y DAO (Data Access Object)**. El sistema administra el flujo de clientes, servicios y la asignación/atención de turnos utilizando estructuras de datos lineales y no lineales propias (**Árbol Binario de Búsqueda - BST** y **Cola Enlazada FIFO**), con persistencia de datos en **SQLite**.

---

## 👥 Integrantes del Grupo

| Nombre Completo | Carné |
| :--- | :--- |
| **Fremy Gadiel Cuc Canel** | 7590-22-13210 |
| **Sinthia Johana Morales Pineda** | 6590-24-13297 |
| **Carlos Alessandro Mendoza Hernández** | 6691-23-17008 |

---

## ⚙️ Requisitos para Ejecutar el Proyecto

1. **Java Development Kit (JDK):** Versión 21 o superior.
2. **Visual Studio Code:** Con las siguientes extensiones recomendadas:
   - *Extension Pack for Java* (Microsoft).
   - *PowerShell* (para la ejecución de comandos).
3. **Bibliotecas JAR (incluidas en la carpeta `lib/`):**
   - `sqlite-jdbc.jar` (Conector JDBC para SQLite).
   - `slf4j-api.jar` (Biblioteca de logging).

---

## 🚀 Instrucciones para Abrir y Ejecutar en VS Code

### 1. Abrir el proyecto
1. Abre **Visual Studio Code**.
2. Ve a `Archivo -> Abrir carpeta...` (o `File -> Open Folder...`) y selecciona la carpeta raíz del proyecto (`Proyecto-Sistema-Atencion-de-Turnos`).

### 2. Compilar y ejecutar desde la Terminal (PowerShell)
Abre la terminal integrada en VS Code (`Ctrl + ~`) y ejecuta el siguiente comando unificado para compilar todas las clases y desplegar el sistema:

```powershell
javac -encoding UTF-8 -cp "lib/sqlite-jdbc.jar;lib/slf4j-api.jar" -d bin (Get-ChildItem -Recurse -Filter *.java src).FullName; java -cp "bin;lib/sqlite-jdbc.jar;lib/slf4j-api.jar" Main


🗄️ Instrucciones para Crear la Base de Datos desde el Script
El sistema cuenta con una rutina automática que crea el archivo data/turnos.db y las tablas necesarias al iniciar por primera vez. No obstante, si deseas recrear la base de datos manualmente o verificar la estructura, se incluye el script SQL en la carpeta sql/crear_y_cargar.sql.

Crear/Resetear la Base de Datos manualmente:
Para limpiar la base de datos actual, ejecuta en la terminal de PowerShell:

PowerShell
Remove-Item -Force data/turnos.db -ErrorAction SilentlyContinue
Al volver a ejecutar la aplicación con el comando principal de Java, el sistema leerá el esquema e insertará automáticamente los datos iniciales comunes de prueba:

Clientes Iniciales:

1001 - Ana López (55501001)

1002 - Carlos Pérez (55501002)

1003 - María García (55501003)

1004 - José Hernández (55501004)

1005 - Luisa Morales (55501005)

Servicios Iniciales:

1 - Información (Activo)

2 - Entrega de documentos (Activo)

3 - Actualización de datos (Activo)

📊 Distribución del Trabajo
El desarrollo se organizó por capas arquitectónicas asignadas a cada integrante de la siguiente manera:

👤 Carlos (Estructuras de Datos y Persistencia Base)
Configuración de la conexión a la base de datos SQLite (ConexionDB.java).

Implementación del script de base de datos SQL (sql/crear_y_cargar.sql).

Estructuras de datos lineales y no lineales propias de memoria:

Árbol Binario de Búsqueda (ArbolBST.java, NodoBST.java).

Cola Enlazada FIFO (ColaEnlazada.java, NodoCola.java).

👤 Cynthia (Modelo de Dominio y Capa DAO)
Clases del modelo del sistema (Cliente.java, Servicio.java, Turno.java, EstadoTurno.java).

Definición de excepciones personalizadas (PersistenciaException.java, ReglaNegocioException.java).

Interfaces y clases de la capa de acceso a datos (DAO):

IClienteDAO.java / ClienteDAOJDBC.java

IServicioDAO.java / ServicioDAOJDBC.java

ITurnoDAO.java / TurnoDAOJDBC.java

👤 [Fremy] (Capa de Servicios, Interfaz de Consola e Integración)
Capa de lógica de negocio y servicios (ClienteService.java, ServicioService.java, TurnoService.java).

Desarrollo de la interfaz de consola interactiva con navegación de submenús (MenuConsola.java).

Coordinación del punto de entrada (Main.java), auto-inicialización de base de datos e integración final del sistema al 100%.

Configuración del archivo .gitignore y documentación (README.md).

🔗 Enlace del Repositorio Git
Repositorio en GitHub: https://github.com/Fremycuc14/Proyecto-Sistema-de-Atencion-de-Turnos-PrograII.git
