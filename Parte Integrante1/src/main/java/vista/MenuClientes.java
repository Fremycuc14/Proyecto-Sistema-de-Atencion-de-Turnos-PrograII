package vista;

import servicio.ClienteService;
import java.util.Scanner;

public class MenuClientes {
    private final ClienteService servicio;
    private final Scanner sc;

    public MenuClientes(ClienteService servicio, Scanner sc) {
        this.servicio = servicio;
        this.sc = sc;
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE CLIENTES ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar todos (inorden)");
            System.out.println("3. Buscar por código");
            System.out.println("4. Actualizar nombre y teléfono");
            System.out.println("5. Activar / desactivar");
            System.out.println("0. Volver");
            opcion = leerEntero("Opción: ");
            try {
                switch (opcion) {
                    case 1 -> servicio.registrar(leerEntero("Código: "),
                            leerTexto("Nombre completo: "), leerTexto("Teléfono (8 dígitos): "));
                    case 2 -> servicio.listar().forEach(System.out::println);
                    case 3 -> System.out.println(servicio.buscar(leerEntero("Código: ")));
                    case 4 -> servicio.actualizar(leerEntero("Código: "),
                            leerTexto("Nuevo nombre: "), leerTexto("Nuevo teléfono: "));
                    case 5 -> {
                        int codigo = leerEntero("Código: ");
                        String r = leerTexto("¿Activar (A) o desactivar (D)? ");
                        if (r.equalsIgnoreCase("A")) servicio.cambiarEstado(codigo, true);
                        else if (r.equalsIgnoreCase("D")) servicio.cambiarEstado(codigo, false);
                        else System.out.println("Opción inexistente.");
                    }
                    case 0 -> { }
                    default -> System.out.println("Opción inexistente.");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }
}
