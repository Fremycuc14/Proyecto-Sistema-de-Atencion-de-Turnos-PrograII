package servicio;

import dao.ClienteDAO;
import estructuras.ArbolBST;
import excepciones.ReglaNegocioException;
import modelo.Cliente;
import java.util.List;

/** Validaciones y coordinación entre la base de datos (fuente de verdad) y el BST. */
public class ClienteService {
    private final ClienteDAO dao;
    private final ArbolBST arbol = new ArbolBST();

    public ClienteService(ClienteDAO dao) {
        this.dao = dao;
        for (Cliente c : dao.listarTodos()) {   // reconstrucción del BST al iniciar
            arbol.insertar(c);
        }
    }

    public void registrar(int codigo, String nombre, String telefono) {
        if (codigo <= 0) throw new ReglaNegocioException("El código debe ser un entero positivo.");
        if (arbol.buscar(codigo) != null) throw new ReglaNegocioException("Ya existe un cliente con ese código.");
        validarNombre(nombre);
        validarTelefono(telefono);
        Cliente nuevo = new Cliente(codigo, nombre.trim(), telefono, true);
        dao.insertar(nuevo);   // 1) base de datos
        arbol.insertar(nuevo); // 2) árbol (solo si lo anterior no falló)
    }

    public Cliente buscar(int codigo) {
        Cliente c = arbol.buscar(codigo);
        if (c == null) throw new ReglaNegocioException("No existe un cliente con el código " + codigo + ".");
        return c;
    }

    public List<Cliente> listar() {
        return arbol.inorden();
    }

    public void actualizar(int codigo, String nombre, String telefono) {
        Cliente actual = buscar(codigo);
        validarNombre(nombre);
        validarTelefono(telefono);
        Cliente copia = new Cliente(codigo, nombre.trim(), telefono, actual.isActivo());
        dao.actualizar(copia); // si falla, el árbol queda intacto
        actual.setNombre(copia.getNombre());
        actual.setTelefono(copia.getTelefono());
    }

    public void cambiarEstado(int codigo, boolean activo) {
        Cliente c = buscar(codigo);
        if (!activo && dao.tieneTurnoPendiente(codigo)) {
            throw new ReglaNegocioException("No se puede desactivar: el cliente tiene un turno pendiente.");
        }
        dao.cambiarEstado(codigo, activo);
        c.setActivo(activo);
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new ReglaNegocioException("El nombre es obligatorio.");
        }
    }

    private void validarTelefono(String telefono) {
        if (telefono == null || !telefono.matches("\\d{8}")) {
            throw new ReglaNegocioException("El teléfono debe tener exactamente 8 dígitos.");
        }
    }
}
