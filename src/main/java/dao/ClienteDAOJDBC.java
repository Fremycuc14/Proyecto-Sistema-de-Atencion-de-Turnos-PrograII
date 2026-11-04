package dao;

import config.ConexionDB;
import excepciones.PersistenciaException;
import modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOJDBC implements IClienteDAO {

    @Override
    public void insertar(Cliente cliente) throws PersistenciaException {
        String sql = "INSERT INTO clientes (codigo, nombre, telefono, estado) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getCodigo());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEstado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al insertar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public Cliente buscarPorCodigo(int codigo) throws PersistenciaException {
        String sql = "SELECT codigo, nombre, telefono, estado FROM clientes WHERE codigo = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                        rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("estado")
                    );
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al buscar cliente: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Cliente> obtenerTodos() throws PersistenciaException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT codigo, nombre, telefono, estado FROM clientes ORDER BY codigo";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clientes.add(new Cliente(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar clientes: " + e.getMessage(), e);
        }
        return clientes;
    }

    @Override
    public void actualizar(Cliente cliente) throws PersistenciaException {
        String sql = "UPDATE clientes SET nombre = ?, telefono = ?, estado = ? WHERE codigo = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getTelefono());
            stmt.setString(3, cliente.getEstado());
            stmt.setInt(4, cliente.getCodigo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar cliente: " + e.getMessage(), e);
        }
    }
}
