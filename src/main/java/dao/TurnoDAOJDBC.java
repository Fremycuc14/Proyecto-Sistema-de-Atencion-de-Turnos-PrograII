package dao;

import config.ConexionDB;
import excepciones.PersistenciaException;
import modelo.Turno;
import modelo.EstadoTurno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAOJDBC implements ITurnoDAO {

    @Override
    public int generarTurno(int codigoCliente, int codigoServicio, String fechaGeneracion) throws PersistenciaException {
        String sql = "INSERT INTO turnos (codigo_cliente, codigo_servicio, estado, fecha_generacion) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, codigoCliente);
            stmt.setInt(2, codigoServicio);
            stmt.setString(3, EstadoTurno.PENDIENTE.name());
            stmt.setString(4, fechaGeneracion);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            throw new PersistenciaException("No se pudo obtener el ID del turno generado.");
        } catch (SQLException e) {
            throw new PersistenciaException("Error al generar turno en la base de datos", e);
        }
    }

    @Override
    public Turno buscarPorNumero(int numero) throws PersistenciaException {
        String sql = "SELECT * FROM turnos WHERE numero = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTurno(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al buscar turno por número: " + numero, e);
        }
    }

    @Override
    public List<Turno> obtenerPendientesOrdenados() throws PersistenciaException {
        return obtenerPorEstado(EstadoTurno.PENDIENTE);
    }

    @Override
    public List<Turno> obtenerPorEstado(EstadoTurno estado) throws PersistenciaException {
        List<Turno> lista = new ArrayList<>();
        String sql = "SELECT * FROM turnos WHERE estado = ? ORDER BY numero ASC";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearTurno(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar turnos por estado", e);
        }
    }

    @Override
    public List<Turno> obtenerPorCliente(int codigoCliente) throws PersistenciaException {
        List<Turno> lista = new ArrayList<>();
        String sql = "SELECT * FROM turnos WHERE codigo_cliente = ? ORDER BY numero ASC";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigoCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearTurno(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar historial por cliente", e);
        }
    }

    @Override
    public void actualizarEstado(int numeroTurno, EstadoTurno nuevoEstado, String fechaHora) throws PersistenciaException {
        String sql = "UPDATE turnos SET estado = ?, fecha_atencion_cancelacion = ? WHERE numero = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado.name());
            stmt.setString(2, fechaHora);
            stmt.setInt(3, numeroTurno);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar el estado del turno", e);
        }
    }

    @Override
    public boolean clienteTieneTurnoPendiente(int codigoCliente) throws PersistenciaException {
        String sql = "SELECT COUNT(*) FROM turnos WHERE codigo_cliente = ? AND estado = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigoCliente);
            stmt.setString(2, EstadoTurno.PENDIENTE.name());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al verificar turnos pendientes del cliente", e);
        }
    }

    @Override
    public boolean servicioTieneTurnosPendientes(int codigoServicio) throws PersistenciaException {
        String sql = "SELECT COUNT(*) FROM turnos WHERE codigo_servicio = ? AND estado = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigoServicio);
            stmt.setString(2, EstadoTurno.PENDIENTE.name());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al verificar turnos pendientes del servicio", e);
        }
    }

    private Turno mapearTurno(ResultSet rs) throws SQLException {
        int numero = rs.getInt("numero");
        int codigoCliente = rs.getInt("codigo_cliente");
        int codigoServicio = rs.getInt("codigo_servicio");
        String estadoStr = rs.getString("estado");
        String fechaGeneracion = rs.getString("fecha_generacion");
        String fechaAtencionCancelacion = rs.getString("fecha_atencion_cancelacion");

        EstadoTurno estado = EstadoTurno.valueOf(estadoStr.toUpperCase());

        return new Turno(numero, codigoCliente, codigoServicio, estado, fechaGeneracion, fechaAtencionCancelacion);
    }

    @Override
    public List<Turno> obtenerTodos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerTodos'");
    }
}