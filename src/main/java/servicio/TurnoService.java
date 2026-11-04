package servicio;

import dao.IClienteDAO;
import dao.IServicioDAO;
import dao.ITurnoDAO;
import estructuras.ColaEnlazada;
import excepciones.PersistenciaException;
import excepciones.ReglaNegocioException;
import modelo.EstadoTurno;
import modelo.Turno;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TurnoService {

    private final ITurnoDAO turnoDAO;
    private final IClienteDAO clienteDAO;
    private final IServicioDAO servicioDAO;
    private final ColaEnlazada colaPendientes;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TurnoService(ITurnoDAO turnoDAO, IClienteDAO clienteDAO, IServicioDAO servicioDAO, ColaEnlazada colaPendientes) {
        this.turnoDAO = turnoDAO;
        this.clienteDAO = clienteDAO;
        this.servicioDAO = servicioDAO;
        this.colaPendientes = colaPendientes;
    }

    public Turno generarTurno(int codigoCliente, int codigoServicio) throws ReglaNegocioException, PersistenciaException {
        if (clienteDAO.buscarPorCodigo(codigoCliente) == null) {
            throw new ReglaNegocioException("El cliente no se encuentra registrado.");
        }
        if (servicioDAO.buscarPorCodigo(codigoServicio) == null) {
            throw new ReglaNegocioException("El servicio no existe o no se encuentra activo.");
        }
        if (turnoDAO.clienteTieneTurnoPendiente(codigoCliente)) {
            throw new ReglaNegocioException("El cliente ya posee un turno PENDIENTE activo.");
        }

        String fechaActual = LocalDateTime.now().format(FORMATO_FECHA);
        int numeroTurno = turnoDAO.generarTurno(codigoCliente, codigoServicio, fechaActual);

        Turno nuevoTurno = new Turno(numeroTurno, codigoCliente, codigoServicio, EstadoTurno.PENDIENTE, fechaActual, null);
        colaPendientes.encolar(nuevoTurno);
        return nuevoTurno;
    }

    public Turno atenderSiguienteTurno() throws ReglaNegocioException, PersistenciaException {
        if (colaPendientes.estaVacia()) {
            return null;
        }

        Turno turno = colaPendientes.desencolar();
        String fechaAtencion = LocalDateTime.now().format(FORMATO_FECHA);

        turno.setEstado(EstadoTurno.ATENDIDO);
        turno.setFechaAtencionCancelacion(fechaAtencion);

        turnoDAO.actualizarEstado(turno.getNumero(), EstadoTurno.ATENDIDO, fechaAtencion);
        return turno;
    }

    public void cancelarTurno(int numeroTurno) throws ReglaNegocioException, PersistenciaException {
        Turno turno = turnoDAO.buscarPorNumero(numeroTurno);
        if (turno == null) {
            throw new ReglaNegocioException("No existe el turno con número: " + numeroTurno);
        }
        if (turno.getEstado() != EstadoTurno.PENDIENTE) {
            throw new ReglaNegocioException("Solo se pueden cancelar turnos en estado PENDIENTE.");
        }

        String fechaCancelacion = LocalDateTime.now().format(FORMATO_FECHA);
        turnoDAO.actualizarEstado(numeroTurno, EstadoTurno.CANCELADO, fechaCancelacion);

        // Reconstruir la cola en memoria sin el turno cancelado
        List<Turno> pendientes = turnoDAO.obtenerPendientesOrdenados();
        colaPendientes.vaciar();
        for (Turno t : pendientes) {
            colaPendientes.encolar(t);
        }
    }

    public List<Turno> obtenerPendientesMemoria() {
        return colaPendientes.obtenerTodos();
    }

    public List<Turno> obtenerTurnosPorEstado(EstadoTurno estado) throws PersistenciaException {
        return turnoDAO.obtenerPorEstado(estado);
    }

    public List<Turno> obtenerHistorialCliente(int codigoCliente) throws PersistenciaException {
        return turnoDAO.obtenerPorCliente(codigoCliente);
    }

    public void mostrarResumenPorServicio() throws PersistenciaException {
        List<Turno> todos = turnoDAO.obtenerTodos();
        System.out.println("\n--- RESUMEN DE TURNOS POR SERVICIO ---");
        var servicios = servicioDAO.obtenerTodos();
        for (var s : servicios) {
            long total = todos.stream().filter(t -> t.getCodigoServicio() == s.getCodigo()).count();
            long atendidos = todos.stream().filter(t -> t.getCodigoServicio() == s.getCodigo() && t.getEstado() == EstadoTurno.ATENDIDO).count();
            long pendientes = todos.stream().filter(t -> t.getCodigoServicio() == s.getCodigo() && t.getEstado() == EstadoTurno.PENDIENTE).count();
            long cancelados = todos.stream().filter(t -> t.getCodigoServicio() == s.getCodigo() && t.getEstado() == EstadoTurno.CANCELADO).count();

            System.out.printf("Servicio [%d] %s: Total = %d | Atendidos = %d | Pendientes = %d | Cancelados = %d\n",
                    s.getCodigo(), s.getNombre(), total, atendidos, pendientes, cancelados);
        }
    }
}