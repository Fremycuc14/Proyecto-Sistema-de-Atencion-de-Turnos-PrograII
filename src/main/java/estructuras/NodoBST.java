package estructuras;

import modelo.Cliente;

public class NodoBST {
    private Cliente cliente;
    private NodoBST izquierdo;
    private NodoBST derecho;

    public NodoBST(Cliente cliente) {
        this.cliente = cliente;
        this.izquierdo = null;
        this.derecho = null;
    }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public NodoBST getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoBST izquierdo) { this.izquierdo = izquierdo; }
    public NodoBST getDerecho() { return derecho; }
    public void setDerecho(NodoBST derecho) { this.derecho = derecho; }
}
