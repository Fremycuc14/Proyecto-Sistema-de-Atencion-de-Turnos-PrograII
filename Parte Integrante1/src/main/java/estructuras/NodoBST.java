package estructuras;

import modelo.Cliente;

public class NodoBST {
    Cliente cliente;
    NodoBST izquierdo;
    NodoBST derecho;

    NodoBST(Cliente cliente) {
        this.cliente = cliente;
    }
}
