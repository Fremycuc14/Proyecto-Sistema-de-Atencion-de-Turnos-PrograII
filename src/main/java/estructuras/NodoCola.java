package estructuras;

import modelo.Turno;

public class NodoCola {
    private Turno turno;
    private NodoCola siguiente;

    public NodoCola(Turno turno) {
        this.turno = turno;
        this.siguiente = null;
    }

    public Turno getTurno() { return turno; }
    public NodoCola getSiguiente() { return siguiente; }
    public void setSiguiente(NodoCola siguiente) { this.siguiente = siguiente; }
}
