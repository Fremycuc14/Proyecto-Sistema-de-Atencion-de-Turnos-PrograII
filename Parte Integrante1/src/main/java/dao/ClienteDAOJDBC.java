package dao;

import excepciones.PersistenciaException;
import modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOJDBC implements ClienteDAO {

    @Override
    public void insertar(Cliente c) {
        String sql = "INSERT INTO clientes(codigo, nombre, telefono, activo) VALUES (?,?,?,?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getCodigo());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getTelefono());
            ps.setInt(4, c.isActivo() ? 1 : 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo insertar el cliente.", e);
        }
    }

    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT codigo, nombre, telefono, activo FROM clientes ORDER BY codigo";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Cliente(rs.getInt("codigo"), rs.getString("nombre"),
                        rs.getString("telefono"), rs.getInt("activo") == 1));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudieron cargar los clientes.", e);
        }
        return lista;
    }

    @Override
    public void actualizar(Cliente c) {
        String sql = "UPDATE clientes SET nombre = ?, telefono = ? WHERE codigo = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setInt(3, c.getCodigo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo actualizar el cliente.", e);
        }
    }

    @Override
    public void cambiarEstado(int codigo, boolean activo) {
        String sql = "UPDATE clientes SET activo = ? WHERE codigo = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, activo ? 1 : 0);
            ps.setInt(2, codigo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo cambiar el estado del cliente.", e);
        }
    }

    @Override
    public boolean tieneTurnoPendiente(int codigo) {
        String sql = "SELECT COUNT(*) FROM turnos WHERE cliente_codigo = ? AND estado = 'PENDIENTE'";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudieron consultar los turnos del cliente.", e);
        }
    }
}
