package dao;

import modelo.Servicio;
import excepciones.PersistenciaException;
import java.util.List;

public interface IServicioDAO {
    void insertar(Servicio servicio) throws PersistenciaException;
    Servicio buscarPorCodigo(int codigo) throws PersistenciaException;
    List<Servicio> obtenerTodos() throws PersistenciaException;
    void actualizar(Servicio servicio) throws PersistenciaException;
}
