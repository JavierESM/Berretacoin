package aed;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
    private Nodo raiz;
    private int tamaño;
    private ArrayList<Nodo> nodosEnNivel;

    private class Nodo {
        T dato;
        Nodo padre, hijoIzquierdo, hijoDerecho;
        HandleHeap aSiMismo;

        Nodo(T dato) {
            this.dato = dato;
        }
    }

    public class HandleHeap {
        private Nodo nodoApuntado;

        private HandleHeap(Nodo nodo) {
            this.nodoApuntado = nodo;
        }

        public T obtener() {
            return nodoApuntado.dato;
        }
    }

    public Heap() {
        this.nodosEnNivel = new ArrayList<>();
    }

    public HandleHeap insertar(T nuevoValor) {
        Nodo nuevoNodo = new Nodo(nuevoValor);
        HandleHeap nuevoHandle = new HandleHeap(nuevoNodo);
        nuevoNodo.aSiMismo = nuevoHandle;

        if (raiz == null) {
            raiz = nuevoNodo;
        } else {
            Nodo padre = nodosEnNivel.get((nodosEnNivel.size() - 1) / 2);
            nuevoNodo.padre = padre;
            if (padre.hijoIzquierdo == null) {
                padre.hijoIzquierdo = nuevoNodo;
            } else {
                padre.hijoDerecho = nuevoNodo;
            }
        }

        nodosEnNivel.add(nuevoNodo);
        tamaño++;
        return nuevoHandle;
    }

    public void heapifyLloyd() {
        for (int i = nodosEnNivel.size() / 2 - 1; i >= 0; i--) {
            siftDown(nodosEnNivel.get(i));
        }
    }

    public void editar(HandleHeap handle) {
        siftUp(handle.nodoApuntado);
        siftDown(handle.nodoApuntado);
    }

    public T mostrarMaximo() {
        if (raiz == null) {
            return null;
        }
        return raiz.dato;
    }

    public HandleHeap extraerMaximo() {
        if (raiz == null) return null;

        Nodo nodoMaximo = raiz;
        HandleHeap handleMaximo = nodoMaximo.aSiMismo;

        Nodo ultimo = nodosEnNivel.get(nodosEnNivel.size() - 1);
        intercambiar(nodoMaximo, ultimo);

        if (ultimo.padre != null) {
            if (ultimo.padre.hijoIzquierdo == ultimo) {
                ultimo.padre.hijoIzquierdo = null;
            } else {
                ultimo.padre.hijoDerecho = null;
            }
        } else {
            raiz = null;
        }

        nodosEnNivel.remove(nodosEnNivel.size() - 1);
        tamaño--;

        if (raiz != null) {
            siftDown(raiz);
        }

        return handleMaximo;
    }

    private void intercambiar(Nodo a, Nodo b) {
        T guardarDato = a.dato;
        a.dato = b.dato;
        b.dato = guardarDato;

        HandleHeap handleA = a.aSiMismo;
        HandleHeap handleB = b.aSiMismo;

        a.aSiMismo = handleB;
        b.aSiMismo = handleA;
        if (a.aSiMismo != null) a.aSiMismo.nodoApuntado = a;
        if (b.aSiMismo != null) b.aSiMismo.nodoApuntado = b;
    }

    private void siftUp(Nodo nodo) {
        while (nodo.padre != null && nodo.dato.compareTo(nodo.padre.dato) < 0) {
            intercambiar(nodo, nodo.padre);
            nodo = nodo.padre;
        }
    }

    private void siftDown(Nodo nodo) {
        while (true) {
            Nodo mayor = nodo;

            if (nodo.hijoIzquierdo != null && nodo.hijoIzquierdo.dato.compareTo(mayor.dato) < 0) {
                mayor = nodo.hijoIzquierdo;
            }

            if (nodo.hijoDerecho != null && nodo.hijoDerecho.dato.compareTo(mayor.dato) < 0) {
                mayor = nodo.hijoDerecho;
            }

            if (nodo != mayor) {
                intercambiar(nodo, mayor);
                nodo = mayor;
            } else {
                break;
            }
        }
    }

    public int tamaño() {
        return this.tamaño;
    }
}
