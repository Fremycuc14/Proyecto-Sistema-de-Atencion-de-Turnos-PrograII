package dao;

import modelo.Turno;
import modelo.EstadoTurno;
import excepciones.PersistenciaException;
import java.util.List;

public interface ITurnoDAO {
    int generarTurno(int codigoCliente, int codigoServicio, String fechaGeneracion) throws PersistenciaException;
    Turno buscarPorNumero(int numero) throws PersistenciaException;
    List<Turno> obtenerPendientesOrdenados() throws PersistenciaException;
    List<Turno> obtenerPorEstado(EstadoTurno estado) throws PersistenciaException;
    List<Turno> obtenerPorCliente(int codigoCliente) throws PersistenciaException;
    boolean clienteTieneTurnoPendiente(int codigoCliente) throws PersistenciaException;
    boolean servicioTieneTurnosPendientes(int codigoServicio) throws PersistenciaException;
    void actualizarEstado(int numeroTurno, EstadoTurno nuevoEstado, String fechaAtencionCancelacion) throws PersistenciaException;
    List<Turno> obtenerTodos();
}
