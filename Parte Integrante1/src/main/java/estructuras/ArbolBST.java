package estructuras;

import modelo.Cliente;
import java.util.ArrayList;
import java.util.List;

/** Árbol binario de búsqueda propio. Clave: código del cliente. */
public class ArbolBST {
    private NodoBST raiz;

    public boolean insertar(Cliente cliente) {
        if (buscar(cliente.getCodigo()) != null) return false; // código duplicado
        raiz = insertarRec(raiz, cliente);
        return true;
    }

    private NodoBST insertarRec(NodoBST nodo, Cliente cliente) {
        if (nodo == null) return new NodoBST(cliente);
        if (cliente.getCodigo() < nodo.cliente.getCodigo()) {
            nodo.izquierdo = insertarRec(nodo.izquierdo, cliente);
        } else {
            nodo.derecho = insertarRec(nodo.derecho, cliente);
        }
        return nodo;
    }

    public Cliente buscar(int codigo) {
        return buscarRec(raiz, codigo);
    }

    private Cliente buscarRec(NodoBST nodo, int codigo) {
        if (nodo == null) return null;                               // caso base: no existe
        if (codigo == nodo.cliente.getCodigo()) return nodo.cliente; // caso base: encontrado
        return codigo < nodo.cliente.getCodigo()
                ? buscarRec(nodo.izquierdo, codigo)
                : buscarRec(nodo.derecho, codigo);
    }

    public List<Cliente> inorden() {
        List<Cliente> resultado = new ArrayList<>();
        inordenRec(raiz, resultado);
        return resultado;
    }

    private void inordenRec(NodoBST nodo, List<Cliente> acumulado) {
        if (nodo == null) return;
        inordenRec(nodo.izquierdo, acumulado);
        acumulado.add(nodo.cliente);
        inordenRec(nodo.derecho, acumulado);
    }

    public boolean estaVacio() {
        return raiz == null;
    }
}
