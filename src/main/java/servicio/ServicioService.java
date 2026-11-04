package servicio;

import dao.IServicioDAO;
import dao.ITurnoDAO;
import excepciones.PersistenciaException;
import excepciones.ReglaNegocioException;
import modelo.EstadoTurno;
import modelo.Servicio;
import modelo.Turno;

import java.util.*;

public class ServicioService {
    private final IServicioDAO servicioDAO;
    private final ITurnoDAO turnoDAO;

    public ServicioService(IServicioDAO servicioDAO, ITurnoDAO turnoDAO) {
        this.servicioDAO = servicioDAO;
        this.turnoDAO = turnoDAO;
    }

    public void registrarServicio(int codigo, String nombre) throws ReglaNegocioException, PersistenciaException {
        if (codigo <= 0) {
            throw new ReglaNegocioException("El código del servicio debe ser un número positivo.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ReglaNegocioException("El nombre del servicio es obligatorio.");
        }
        if (servicioDAO.buscarPorCodigo(codigo) != null) {
            throw new ReglaNegocioException("Ya existe un servicio con el código: " + codigo);
        }

        Servicio servicio = new Servicio(codigo, nombre.trim(), "Activo");
        servicioDAO.insertar(servicio);
    }

    public List<Servicio> obtenerTodosServicios() throws PersistenciaException {
        return servicioDAO.obtenerTodos();
    }

    public void actualizarServicio(int codigo, String nuevoNombre) throws ReglaNegocioException, PersistenciaException {
        Servicio servicio = servicioDAO.buscarPorCodigo(codigo);
        if (servicio == null) {
            throw new ReglaNegocioException("No existe el servicio con código: " + codigo);
        }
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new ReglaNegocioException("El nombre es obligatorio.");
        }

        servicio.setNombre(nuevoNombre.trim());
        servicioDAO.actualizar(servicio);
    }

    public void cambiarEstadoServicio(int codigo, boolean activar) throws ReglaNegocioException, PersistenciaException {
        Servicio servicio = servicioDAO.buscarPorCodigo(codigo);
        if (servicio == null) {
            throw new ReglaNegocioException("No existe el servicio con código: " + codigo);
        }

        if (!activar && turnoDAO.servicioTieneTurnosPendientes(codigo)) {
            throw new ReglaNegocioException("No se puede desactivar un servicio con turnos pendientes.");
        }

        servicio.setEstado(activar ? "Activo" : "Inactivo");
        servicioDAO.actualizar(servicio);
    }

    // Uso obligatorio de Colecciones de Java (Map / List) para generar el Resumen
    public List<Map<String, Object>> generarResumenPorServicio() throws PersistenciaException {
        List<Servicio> servicios = servicioDAO.obtenerTodos();
        List<Map<String, Object>> resumenList = new ArrayList<>();

        for (Servicio s : servicios) {
            Map<String, Object> fila = new LinkedHashMap<>();
            fila.put("nombre", s.getNombre());

            int pendientes = 0;
            int atendidos = 0;
            int cancelados = 0;

            // Obtener todos los turnos asignados a este servicio
            List<Turno> todosTurnosPendientes = turnoDAO.obtenerPorEstado(EstadoTurno.PENDIENTE);
            List<Turno> todosTurnosAtendidos = turnoDAO.obtenerPorEstado(EstadoTurno.ATENDIDO);
            List<Turno> todosTurnosCancelados = turnoDAO.obtenerPorEstado(EstadoTurno.CANCELADO);

            for (Turno t : todosTurnosPendientes) {
                if (t.getCodigoServicio() == s.getCodigo()) pendientes++;
            }
            for (Turno t : todosTurnosAtendidos) {
                if (t.getCodigoServicio() == s.getCodigo()) atendidos++;
            }
            for (Turno t : todosTurnosCancelados) {
                if (t.getCodigoServicio() == s.getCodigo()) cancelados++;
            }

            fila.put("pendientes", pendientes);
            fila.put("atendidos", atendidos);
            fila.put("cancelados", cancelados);
            fila.put("total", pendientes + atendidos + cancelados);

            resumenList.add(fila);
        }
        return resumenList;
    }
}
