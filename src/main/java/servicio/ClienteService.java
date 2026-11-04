package servicio;

import dao.IClienteDAO;
import estructuras.ArbolBST;
import excepciones.PersistenciaException;
import excepciones.ReglaNegocioException;
import modelo.Cliente;

import java.util.List;

public class ClienteService {
    private final IClienteDAO clienteDAO;
    private final ArbolBST arbolBST;

    public ClienteService(IClienteDAO clienteDAO, ArbolBST arbolBST) {
        this.clienteDAO = clienteDAO;
        this.arbolBST = arbolBST;
    }

    public void registrarCliente(int codigo, String nombre, String telefono) throws ReglaNegocioException, PersistenciaException {
        if (codigo <= 0) {
            throw new ReglaNegocioException("El código del cliente debe ser un número positivo.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ReglaNegocioException("El nombre del cliente es obligatorio.");
        }
        if (telefono == null || !telefono.trim().matches("\\d{8}")) {
            throw new ReglaNegocioException("El teléfono debe contener exactamente 8 dígitos numéricos.");
        }
        if (clienteDAO.buscarPorCodigo(codigo) != null) {
            throw new ReglaNegocioException("Ya existe un cliente registrado con el código: " + codigo);
        }

        Cliente cliente = new Cliente(codigo, nombre.trim(), telefono.trim(), "Activo");
        clienteDAO.insertar(cliente);
        arbolBST.insertar(cliente);
    }

    public List<Cliente> obtenerClientesOrdenadosBST() {
        return arbolBST.obtenerEnOrden();
    }

    public Cliente buscarClienteBST(int codigo) {
        return arbolBST.buscar(codigo);
    }

    public void actualizarCliente(int codigo, String nuevoNombre, String nuevoTelefono) throws ReglaNegocioException, PersistenciaException {
        Cliente cliente = clienteDAO.buscarPorCodigo(codigo);
        if (cliente == null) {
            throw new ReglaNegocioException("No existe el cliente con código: " + codigo);
        }
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new ReglaNegocioException("El nombre del cliente es obligatorio.");
        }
        if (nuevoTelefono == null || !nuevoTelefono.trim().matches("\\d{8}")) {
            throw new ReglaNegocioException("El teléfono debe contener exactamente 8 dígitos.");
        }

        cliente.setNombre(nuevoNombre.trim());
        cliente.setTelefono(nuevoTelefono.trim());
        clienteDAO.actualizar(cliente);

        Cliente clienteEnBST = arbolBST.buscar(codigo);
        if (clienteEnBST != null) {
            clienteEnBST.setNombre(nuevoNombre.trim());
            clienteEnBST.setTelefono(nuevoTelefono.trim());
        }
    }

    public void cambiarEstadoCliente(int codigo, boolean activar) throws ReglaNegocioException, PersistenciaException {
        Cliente cliente = clienteDAO.buscarPorCodigo(codigo);
        if (cliente == null) {
            throw new ReglaNegocioException("No existe el cliente con código: " + codigo);
        }

        cliente.setEstado(activar ? "Activo" : "Inactivo");
        clienteDAO.actualizar(cliente);

        Cliente clienteEnBST = arbolBST.buscar(codigo);
        if (clienteEnBST != null) {
            clienteEnBST.setEstado(activar ? "Activo" : "Inactivo");
        }
    }
}