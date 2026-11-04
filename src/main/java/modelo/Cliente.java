package modelo;

public class Cliente {
    private int codigo;
    private String nombre;
    private String telefono;
    private String estado; // "Activo" o "Inactivo"

    public Cliente(int codigo, String nombre, String telefono, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.estado = estado;
    }

    public int getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public boolean isActivo() { return "Activo".equalsIgnoreCase(this.estado); }

    @Override
    public String toString() {
        return String.format("Cliente [Código: %d, Nombre: %s, Teléfono: %s, Estado: %s]",
                codigo, nombre, telefono, estado);
    }
}
