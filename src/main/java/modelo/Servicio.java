package modelo;

public class Servicio {
    private int codigo;
    private String nombre;
    private String estado; // "Activo" o "Inactivo"

    public Servicio(int codigo, String nombre, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public boolean isActivo() { return "Activo".equalsIgnoreCase(this.estado); }

    @Override
    public String toString() {
        return String.format("Servicio [Código: %d, Nombre: %s, Estado: %s]", codigo, nombre, estado);
    }
}
