package estructuras;

import modelo.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ArbolBST {
    private NodoBST raiz;

    public ArbolBST() {
        this.raiz = null;
    }

    public void insertar(Cliente cliente) {
        raiz = insertarRec(raiz, cliente);
    }

    private NodoBST insertarRec(NodoBST nodo, Cliente cliente) {
        if (nodo == null) {
            return new NodoBST(cliente);
        }
        if (cliente.getCodigo() < nodo.getCliente().getCodigo()) {
            nodo.setIzquierdo(insertarRec(nodo.getIzquierdo(), cliente));
        } else if (cliente.getCodigo() > nodo.getCliente().getCodigo()) {
            nodo.setDerecho(insertarRec(nodo.getDerecho(), cliente));
        } else {
            nodo.setCliente(cliente); // Actualizar si el código ya existe
        }
        return nodo;
    }

    public Cliente buscar(int codigo) {
        return buscarRec(raiz, codigo);
    }

    private Cliente buscarRec(NodoBST nodo, int codigo) {
        if (nodo == null) {
            return null;
        }
        if (codigo == nodo.getCliente().getCodigo()) {
            return nodo.getCliente();
        }
        return codigo < nodo.getCliente().getCodigo()
                ? buscarRec(nodo.getIzquierdo(), codigo)
                : buscarRec(nodo.getDerecho(), codigo);
    }

    public List<Cliente> obtenerEnOrden() {
        List<Cliente> lista = new ArrayList<>();
        inOrdenRec(raiz, lista);
        return lista;
    }

    private void inOrdenRec(NodoBST nodo, List<Cliente> lista) {
        if (nodo != null) {
            inOrdenRec(nodo.getIzquierdo(), lista);
            lista.add(nodo.getCliente());
            inOrdenRec(nodo.getDerecho(), lista);
        }
    }

    public boolean estaVacio() {
        return raiz == null;
    }
}