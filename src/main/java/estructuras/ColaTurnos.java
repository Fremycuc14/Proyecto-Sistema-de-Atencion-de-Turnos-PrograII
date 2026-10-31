package estructuras;

import modelo.Turno;

public class ColaTurnos {
    private NodoTurno frente;
    private NodoTurno fin;
    private int tamanio;

    public ColaTurnos() {
        this.frente = null;
        this.fin = null;
        this.tamanio = 0;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    // 1. Encolar (Agregar al final de la cola)
    public void encolar(Turno turno) {
        NodoTurno nuevo = new NodoTurno(turno);
        if (estaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        tamanio++;
    }

    // 2. Desencolar / Atender (FIFO - Primero en entrar, primero en salir)
    public Turno desencolar() {
        if (estaVacia()) {
            return null;
        }
        Turno turnoAtendido = frente.getTurno();
        frente = frente.getSiguiente();
        if (frente == null) {
            fin = null; // La cola quedó completamente vacía
        }
        tamanio--;
        return turnoAtendido;
    }

    // 3. Consultar el primer turno sin retirarlo (Peek)
    public Turno consultarPrimero() {
        if (estaVacia()) {
            return null;
        }
        return frente.getTurno();
    }

    // 4. Cancelar un turno por su número exacto manteniendo el orden
    public boolean cancelarTurno(int numeroTurno) {
        if (estaVacia()) {
            return false;
        }

        // Si el turno a cancelar está exactamente en el frente
        if (frente.getTurno().getNumero() == numeroTurno) {
            frente.getTurno().setEstado("CANCELADO");
            frente = frente.getSiguiente();
            if (frente == null) {
                fin = null;
            }
            tamanio--;
            return true;
        }

        // Buscar en el resto de la cola
        NodoTurno actual = frente;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getTurno().getNumero() == numeroTurno) {
                NodoTurno nodoACancelar = actual.getSiguiente();
                nodoACancelar.getTurno().setEstado("CANCELADO");
                actual.setSiguiente(nodoACancelar.getSiguiente());
                if (nodoACancelar == fin) {
                    fin = actual; // Actualizar el puntero fin si era el último
                }
                tamanio--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false; // No se encontró el turno pendiente
    }

    // 5. Recorrer y mostrar la cola pendiente
    public void mostrarCola() {
        if (estaVacia()) {
            System.out.println("No hay turnos pendientes en la cola.");
            return;
        }
        NodoTurno actual = frente;
        int posicion = 1;
        System.out.println("--- COLA DE TURNOS PENDIENTES ---");
        while (actual != null) {
            System.out.println(posicion + ". " + actual.getTurno());
            actual = actual.getSiguiente();
            posicion++;
        }
    }

    public int getTamanio() {
        return tamanio;
    }
}