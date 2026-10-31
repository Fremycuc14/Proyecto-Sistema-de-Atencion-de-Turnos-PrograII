package modelo;

import java.time.LocalDateTime;

public class Turno {
    private int numero;
    private int codigoCliente;
    private int codigoServicio;
    private LocalDateTime fechaHoraGeneracion;
    private String estado; // PENDIENTE, ATENDIDO, CANCELADO
    private LocalDateTime fechaHoraAtencionCancelacion;

    public Turno(int numero, int codigoCliente, int codigoServicio, LocalDateTime fechaHoraGeneracion, String estado) {
        this.numero = numero;
        this.codigoCliente = codigoCliente;
        this.codigoServicio = codigoServicio;
        this.fechaHoraGeneracion = fechaHoraGeneracion;
        this.estado = estado;
    }

    // Getters y Setters
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public int getCodigoCliente() { return codigoCliente; }
    public void setCodigoCliente(int codigoCliente) { this.codigoCliente = codigoCliente; }

    public int getCodigoServicio() { return codigoServicio; }
    public void setCodigoServicio(int codigoServicio) { this.codigoServicio = codigoServicio; }

    public LocalDateTime getFechaHoraGeneracion() { return fechaHoraGeneracion; }
    public void setFechaHoraGeneracion(LocalDateTime fechaHoraGeneracion) { this.fechaHoraGeneracion = fechaHoraGeneracion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaHoraAtencionCancelacion() { return fechaHoraAtencionCancelacion; }
    public void setFechaHoraAtencionCancelacion(LocalDateTime fechaHoraAtencionCancelacion) { 
        this.fechaHoraAtencionCancelacion = fechaHoraAtencionCancelacion; 
    }

    @Override
    public String toString() {
        return "Turno #" + numero + " [Cliente: " + codigoCliente + ", Servicio: " + codigoServicio + 
               ", Estado: " + estado + ", Generado: " + fechaHoraGeneracion + "]";
    }
}