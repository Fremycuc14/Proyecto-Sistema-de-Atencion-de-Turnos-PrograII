package dao;

import config.ConexionDB;
import excepciones.PersistenciaException;
import modelo.Servicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAOJDBC implements IServicioDAO {

    @Override
    public void insertar(Servicio servicio) throws PersistenciaException {
        String sql = "INSERT INTO servicios (codigo, nombre, estado) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, servicio.getCodigo());
            stmt.setString(2, servicio.getNombre());
            stmt.setString(3, servicio.getEstado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al insertar servicio: " + e.getMessage(), e);
        }
    }

    @Override
    public Servicio buscarPorCodigo(int codigo) throws PersistenciaException {
        String sql = "SELECT codigo, nombre, estado FROM servicios WHERE codigo = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Servicio(
                        rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getString("estado")
                    );
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al buscar servicio: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Servicio> obtenerTodos() throws PersistenciaException {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT codigo, nombre, estado FROM servicios ORDER BY codigo";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                servicios.add(new Servicio(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar servicios: " + e.getMessage(), e);
        }
        return servicios;
    }

    @Override
    public void actualizar(Servicio servicio) throws PersistenciaException {
        String sql = "UPDATE servicios SET nombre = ?, estado = ? WHERE codigo = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, servicio.getNombre());
            stmt.setString(2, servicio.getEstado());
            stmt.setInt(3, servicio.getCodigo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar servicio: " + e.getMessage(), e);
        }
    }
}
