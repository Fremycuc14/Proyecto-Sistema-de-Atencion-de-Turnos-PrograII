package vista;

import modelo.Cliente;
import modelo.EstadoTurno;
import modelo.Servicio;
import modelo.Turno;
import servicio.ClienteService;
import servicio.ServicioService;
import servicio.TurnoService;

import java.util.List;
import java.util.Scanner;

public class MenuConsola {

    private final ClienteService clienteService;
    private final ServicioService servicioService;
    private final TurnoService turnoService;
    private final Scanner scanner;

    public MenuConsola(ClienteService clienteService, ServicioService servicioService, TurnoService turnoService) {
        this.clienteService = clienteService;
        this.servicioService = servicioService;
        this.turnoService = turnoService;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Seleccione una opción: ");
            System.out.println();

            switch (opcion) {
                case 1 -> menuGestionClientes();
                case 2 -> menuGestionServicios();
                case 3 -> generarTurno();
                case 4 -> atenderTurno();
                case 5 -> cancelarTurno();
                case 6 -> consultarPendientes();
                case 7 -> consultarAtendidos();
                case 8 -> consultarCancelados();
                case 9 -> historialCliente();
                case 10 -> resumenPorServicio();
                case 11 -> {
                    System.out.println(" Saliendo del sistema... ¡Hasta luego!");
                    salir = true;
                }
                default -> System.out.println(" Opción no válida. Intente de nuevo.");
            }
            System.out.println();
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("==================================================");
        System.out.println("   SISTEMA DE ATENCIÓN DE TURNOS - MENÚ PRINCIPAL");
        System.out.println("==================================================");
        System.out.println("1.  Gestión de clientes.");
        System.out.println("2.  Gestión de servicios.");
        System.out.println("3.  Generación de turnos.");
        System.out.println("4.  Atención del siguiente turno.");
        System.out.println("5.  Cancelación de turnos.");
        System.out.println("6.  Consulta de turnos pendientes.");
        System.out.println("7.  Consulta de turnos atendidos.");
        System.out.println("8.  Consulta de turnos cancelados.");
        System.out.println("9.  Historial de un cliente.");
        System.out.println("10. Resumen por servicio.");
        System.out.println("11. Salir.");
        System.out.println("==================================================");
    }

    // -------------------------------------------------------------
    // SUBMENÚ COMPLETO DE CLIENTES
    // -------------------------------------------------------------
    private void menuGestionClientes() {
        System.out.println("\n--- GESTIÓN DE CLIENTES ---");
        System.out.println("1. Registrar nuevo cliente");
        System.out.println("2. Listar clientes (Ordenados por BST)");
        System.out.println("3. Buscar cliente por código");
        System.out.println("4. Actualizar datos de cliente");
        System.out.println("5. Cambiar estado de cliente (Alta/Baja)");
        System.out.println("0.  Volver al Menú Principal");
        
        int op = leerEntero("Seleccione sub-opción: ");
        System.out.println();

        switch (op) {
            case 1 -> registrarCliente();
            case 2 -> listarClientes();
            case 3 -> buscarCliente();
            case 4 -> actualizarCliente();
            case 5 -> cambiarEstadoCliente();
            case 0 -> System.out.println("Regresando al menú principal...");
            default -> System.out.println(" Opción no válida.");
        }
    }

    // -------------------------------------------------------------
    // SUBMENÚ COMPLETO DE SERVICIOS
    // -------------------------------------------------------------
    private void menuGestionServicios() {
        System.out.println("\n--- GESTIÓN DE SERVICIOS ---");
        System.out.println("1. Registrar nuevo servicio");
        System.out.println("2. Listar todos los servicios");
        System.out.println("3. Cambiar estado de servicio (Activo/Inactivo)");
        System.out.println("0.  Volver al Menú Principal");

        int op = leerEntero("Seleccione sub-opción: ");
        System.out.println();

        switch (op) {
            case 1 -> registrarServicio();
            case 2 -> listarServicios();
            case 3 -> cambiarEstadoServicio();
            case 0 -> System.out.println("Regresando al menú principal...");
            default -> System.out.println(" Opción no válida.");
        }
    }

    // -------------------------------------------------------------
    // OPERACIONES DE CLIENTES
    // -------------------------------------------------------------
    private void registrarCliente() {
        try {
            int cod = leerEntero("Código del cliente: ");
            System.out.print("Nombre completo: ");
            String nom = scanner.nextLine();
            System.out.print("Teléfono (8 dígitos): ");
            String tel = scanner.nextLine();
            clienteService.registrarCliente(cod, nom, tel);
            System.out.println("✅ Cliente registrado con éxito.");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteService.obtenerClientesOrdenadosBST();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("--- LISTA DE CLIENTES (BST) ---");
            clientes.forEach(System.out::println);
        }
    }

    private void buscarCliente() {
        int cod = leerEntero("Código del cliente a buscar: ");
        Cliente c = clienteService.buscarClienteBST(cod);
        if (c != null) {
            System.out.println("🔍 Cliente encontrado: " + c);
        } else {
            System.out.println(" No se encontró ningún cliente con el código: " + cod);
        }
    }

    private void actualizarCliente() {
        try {
            int cod = leerEntero("Código del cliente a actualizar: ");
            System.out.print("Nuevo nombre: ");
            String nom = scanner.nextLine();
            System.out.print("Nuevo teléfono (8 dígitos): ");
            String tel = scanner.nextLine();

            clienteService.actualizarCliente(cod, nom, tel);
            System.out.println(" Datos del cliente actualizados con éxito.");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void cambiarEstadoCliente() {
        try {
            int cod = leerEntero("Código del cliente: ");
            System.out.print("¿Desea activarlo? (S/N): ");
            String resp = scanner.nextLine().trim();
            boolean activar = resp.equalsIgnoreCase("S");

            clienteService.cambiarEstadoCliente(cod, activar);
            System.out.println(" Estado del cliente modificado con éxito.");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------
    // OPERACIONES DE SERVICIOS
    // -------------------------------------------------------------
    private void registrarServicio() {
        try {
            int cod = leerEntero("Código del servicio: ");
            System.out.print("Nombre del servicio: ");
            String nom = scanner.nextLine();

            servicioService.registrarServicio(cod, nom);
            System.out.println(" Servicio registrado con éxito.");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void listarServicios() {
        try {
            List<Servicio> servicios = servicioService.obtenerTodosServicios();
            if (servicios.isEmpty()) {
                System.out.println("No hay servicios registrados.");
            } else {
                System.out.println("--- LISTA DE SERVICIOS ---");
                servicios.forEach(servicio -> System.out.println(servicio));
            }
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void cambiarEstadoServicio() {
        try {
            int cod = leerEntero("Código del servicio: ");
            System.out.print("¿Desea activarlo? (S/N): ");
            String resp = scanner.nextLine().trim();
            boolean activar = resp.equalsIgnoreCase("S");

            servicioService.cambiarEstadoServicio(cod, activar);
            System.out.println(" Estado del servicio modificado.");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------
    // OPERACIONES DE TURNOS
    // -------------------------------------------------------------
    private void generarTurno() {
        try {
            int codC = leerEntero("Código del cliente: ");
            int codS = leerEntero("Código del servicio: ");
            Turno t = turnoService.generarTurno(codC, codS);
            System.out.println(" Turno generado exitosamente: #" + t.getNumero());
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void atenderTurno() {
        try {
            Turno t = turnoService.atenderSiguienteTurno();
            if (t != null) {
                System.out.println(" Atendiendo Turno #" + t.getNumero() + " (Cliente: " + t.getCodigoCliente() + ")");
            } else {
                System.out.println(" No hay turnos pendientes en la cola.");
            }
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void cancelarTurno() {
        try {
            int num = leerEntero("Número de turno a cancelar: ");
            turnoService.cancelarTurno(num);
            System.out.println(" Turno cancelado correctamente.");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void consultarPendientes() {
        List<Turno> p = turnoService.obtenerPendientesMemoria();
        if (p.isEmpty()) {
            System.out.println("No hay turnos pendientes en este momento.");
        } else {
            System.out.println("--- TURNOS PENDIENTES ---");
            p.forEach(turno -> System.out.println(turno));
        }
    }

    private void consultarAtendidos() {
        try {
            List<Turno> a = turnoService.obtenerTurnosPorEstado(EstadoTurno.ATENDIDO);
            if (a.isEmpty()) {
                System.out.println("No hay turnos atendidos registrados.");
            } else {
                System.out.println("--- TURNOS ATENDIDOS ---");
                a.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void consultarCancelados() {
        try {
            List<Turno> c = turnoService.obtenerTurnosPorEstado(EstadoTurno.CANCELADO);
            if (c.isEmpty()) {
                System.out.println("No hay turnos cancelados registrados.");
            } else {
                System.out.println("--- TURNOS CANCELADOS ---");
                c.forEach(turno -> System.out.println(turno));
            }
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void historialCliente() {
        try {
            int cod = leerEntero("Código del cliente: ");
            List<Turno> h = turnoService.obtenerHistorialCliente(cod);
            if (h.isEmpty()) {
                System.out.println("El cliente no tiene historial de turnos.");
            } else {
                System.out.println("--- HISTORIAL DE TURNOS DEL CLIENTE " + cod + " ---");
                h.forEach(turno -> System.out.println(turno));
            }
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void resumenPorServicio() {
        try {
            turnoService.mostrarResumenPorServicio();
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(" Por favor, ingrese un número entero válido.");
            }
        }
    }
}