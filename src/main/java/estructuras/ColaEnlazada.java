package estructuras;

import modelo.Turno;
import java.util.ArrayList;
import java.util.List;

public class ColaEnlazada {
    private NodoCola frente;
    private NodoCola fin;

    public ColaEnlazada() {
        this.frente = null;
        this.fin = null;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public void encolar(Turno turno) {
        NodoCola nuevo = new NodoCola(turno);
        if (estaVacia()) {
            frente = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
    }

    public Turno desencolar() {
        if (estaVacia()) return null;
        Turno turno = frente.getTurno();
        frente = frente.getSiguiente();
        if (frente == null) {
            fin = null;
        }
        return turno;
    }

    public Turno verFrente() {
        return estaVacia() ? null : frente.getTurno();
    }

    public boolean cancelarPorNumero(int numeroTurno) {
        if (estaVacia()) return false;

        // Si es el primero de la cola
        if (frente.getTurno().getNumero() == numeroTurno) {
            desencolar();
            return true;
        }

        NodoCola actual = frente;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getTurno().getNumero() == numeroTurno) {
                NodoCola aEliminar = actual.getSiguiente();
                actual.setSiguiente(aEliminar.getSiguiente());
                if (aEliminar == fin) {
                    fin = actual;
                }
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public List<Turno> recorrer() {
        List<Turno> lista = new ArrayList<>();
        NodoCola actual = frente;
        while (actual != null) {
            lista.add(actual.getTurno());
            actual = actual.getSiguiente();
        }
        return lista;
    }

    public void vaciar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'vaciar'");
    }

    public List<Turno> obtenerTodos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerTodos'");
    }
}
