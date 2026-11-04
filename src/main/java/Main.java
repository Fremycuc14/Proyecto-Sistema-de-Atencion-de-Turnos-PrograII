import config.ConexionDB;
import dao.*;
import estructuras.ArbolBST;
import estructuras.ColaEnlazada;
import modelo.Cliente;
import modelo.Turno;
import servicio.ClienteService;
import servicio.ServicioService;
import servicio.TurnoService;
import vista.MenuConsola;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando Sistema de Atención de Turnos...");

            // 0. Crear tablas e insertar datos iniciales exactos de la guía
            inicializarBaseDeDatos();

            // 1. Instanciar DAOs
            IClienteDAO clienteDAO = new ClienteDAOJDBC();
            IServicioDAO servicioDAO = new ServicioDAOJDBC();
            ITurnoDAO turnoDAO = new TurnoDAOJDBC();

            // 2. Instanciar Estructuras de Datos Propias
            ArbolBST arbolBST = new ArbolBST();
            ColaEnlazada colaPendientes = new ColaEnlazada();

            // 3. Cargar datos en memoria (BST)
            System.out.println("Cargando clientes en el BST...");
            List<Cliente> todosClientes = clienteDAO.obtenerTodos();
            for (Cliente c : todosClientes) {
                arbolBST.insertar(c);
            }
            System.out.println("✅ " + todosClientes.size() + " clientes cargados en el BST.");

            // 4. Reconstruir Cola FIFO de turnos pendientes desde BD
            System.out.println("Reconstruyendo la Cola Enlazada con turnos PENDIENTES...");
            List<Turno> pendientesBD = turnoDAO.obtenerPendientesOrdenados();
            for (Turno t : pendientesBD) {
                colaPendientes.encolar(t);
            }

            // 5. Instanciar Capa de Servicios
            ClienteService clienteService = new ClienteService(clienteDAO, arbolBST);
            ServicioService servicioService = new ServicioService(servicioDAO, turnoDAO);
            TurnoService turnoService = new TurnoService(turnoDAO, clienteDAO, servicioDAO, colaPendientes);

            // 6. Iniciar Interfaz de Consola
            MenuConsola menu = new MenuConsola(clienteService, servicioService, turnoService);
            menu.iniciar();

        } catch (Exception e) {
            System.err.println("❌ Error crítico al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void inicializarBaseDeDatos() {
        try (Connection conn = ConexionDB.getConexion(); Statement stmt = conn.createStatement()) {
            String scriptSQL = """
                CREATE TABLE IF NOT EXISTS clientes (
                    codigo INTEGER PRIMARY KEY,
                    nombre TEXT NOT NULL,
                    telefono TEXT NOT NULL,
                    estado TEXT NOT NULL
                );

                CREATE TABLE IF NOT EXISTS servicios (
                    codigo INTEGER PRIMARY KEY,
                    nombre TEXT NOT NULL,
                    estado TEXT NOT NULL
                );

                CREATE TABLE IF NOT EXISTS turnos (
                    numero INTEGER PRIMARY KEY AUTOINCREMENT,
                    codigo_cliente INTEGER NOT NULL,
                    codigo_servicio INTEGER NOT NULL,
                    estado TEXT NOT NULL,
                    fecha_generacion TEXT NOT NULL,
                    fecha_atencion_cancelacion TEXT,
                    FOREIGN KEY (codigo_cliente) REFERENCES clientes(codigo),
                    FOREIGN KEY (codigo_servicio) REFERENCES servicios(codigo)
                );
            """;

            for (String sql : scriptSQL.split(";")) {
                if (!sql.trim().isEmpty()) {
                    stmt.execute(sql);
                }
            }

            // Insertar datos de prueba OFICIALES si la tabla clientes está vacía
            var rs = stmt.executeQuery("SELECT COUNT(*) FROM clientes");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO clientes VALUES (1001, 'Ana López', '55501001', 'Activo');");
                stmt.execute("INSERT INTO clientes VALUES (1002, 'Carlos Pérez', '55501002', 'Activo');");
                stmt.execute("INSERT INTO clientes VALUES (1003, 'María García', '55501003', 'Activo');");
                stmt.execute("INSERT INTO clientes VALUES (1004, 'José Hernández', '55501004', 'Activo');");
                stmt.execute("INSERT INTO clientes VALUES (1005, 'Luisa Morales', '55501005', 'Activo');");

                stmt.execute("INSERT INTO servicios VALUES (1, 'Información', 'Activo');");
                stmt.execute("INSERT INTO servicios VALUES (2, 'Entrega de documentos', 'Activo');");
                stmt.execute("INSERT INTO servicios VALUES (3, 'Actualización de datos', 'Activo');");
                System.out.println("✅ Base de datos cargada con los datos iniciales comunes del proyecto.");
            }

        } catch (Exception e) {
            System.err.println("⚠️ Error al inicializar tablas: " + e.getMessage());
        }
    }
}