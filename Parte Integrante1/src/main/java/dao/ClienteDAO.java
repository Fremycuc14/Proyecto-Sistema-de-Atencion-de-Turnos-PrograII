package dao;

import modelo.Cliente;
import java.util.List;

public interface ClienteDAO {
    void insertar(Cliente cliente);
    List<Cliente> listarTodos();
    void actualizar(Cliente cliente);               // nombre y teléfono
    void cambiarEstado(int codigo, boolean activo);
    boolean tieneTurnoPendiente(int codigo);
}
