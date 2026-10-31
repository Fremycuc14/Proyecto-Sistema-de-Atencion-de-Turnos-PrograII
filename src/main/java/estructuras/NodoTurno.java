package estructuras;

import modelo.Turno;

public class NodoTurno {
    private Turno turno;
    private NodoTurno siguiente;

    public NodoTurno(Turno turno) {
        this.turno = turno;
        this.siguiente = null;
    }

    public Turno getTurno() { return turno; }
    public void setTurno(Turno turno) { this.turno = turno; }

    public NodoTurno getSiguiente() { return siguiente; }
    public void setSiguiente(NodoTurno siguiente) { this.siguiente = siguiente; }
}