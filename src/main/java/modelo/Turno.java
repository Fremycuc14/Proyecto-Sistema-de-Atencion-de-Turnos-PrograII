package modelo;

public class Turno {
    private int numero;
    private int codigoCliente;
    private int codigoServicio;
    private EstadoTurno estado;
    private String fechaGeneracion;
    private String fechaAtencionCancelacion;

    public Turno() {
    }

    public Turno(int numero, int codigoCliente, int codigoServicio, EstadoTurno estado, String fechaGeneracion, String fechaAtencionCancelacion) {
        this.numero = numero;
        this.codigoCliente = codigoCliente;
        this.codigoServicio = codigoServicio;
        this.estado = estado;
        this.fechaGeneracion = fechaGeneracion;
        this.fechaAtencionCancelacion = fechaAtencionCancelacion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }

    public String getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getFechaAtencionCancelacion() {
        return fechaAtencionCancelacion;
    }

    public void setFechaAtencionCancelacion(String fechaAtencionCancelacion) {
        this.fechaAtencionCancelacion = fechaAtencionCancelacion;
    }
}