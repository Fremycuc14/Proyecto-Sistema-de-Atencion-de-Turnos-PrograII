package dao;

import modelo.Cliente;
import excepciones.PersistenciaException;
import java.util.List;

public interface IClienteDAO {
    void insertar(Cliente cliente) throws PersistenciaException;
    Cliente buscarPorCodigo(int codigo) throws PersistenciaException;
    List<Cliente> obtenerTodos() throws PersistenciaException;
    void actualizar(Cliente cliente) throws PersistenciaException;
}
