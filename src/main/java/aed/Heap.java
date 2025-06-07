package aed;

public class Heap<T extends Comparable<T>> {
    private Nodo raiz;
    private int tamaño;

    private class Nodo {
        T dato;
        Nodo padre, hijoIzquierdo, hijoDerecho;
        int tamañoSubarbol;

        Nodo(T dato) {
            this.dato = dato;
            this.tamañoSubarbol = 1;
        }
    }

    public class Handle {
        private Nodo nodoApuntado;

        private Handle(Nodo nodo) {
            this.nodoApuntado = nodo;
        }

        public T obtener() {
            return nodoApuntado.dato;
        }

        public void editar(T nuevoDato) {
            T anterior = nodoApuntado.dato;
            nodoApuntado.dato = nuevoDato;
            if (nuevoDato.compareTo(anterior) > 0) {
                siftUp(nodoApuntado);
            } else {
                siftDown(nodoApuntado);
            }
        }

        public void eliminar() {
            nodoApuntado = null;
        }
    }

    public Handle insertar(T nuevoValor) {
        Nodo nuevoNodo = new Nodo(nuevoValor);
        Handle nuevoHandle = new Handle(nuevoNodo);

        if (raiz == null) {
            raiz = nuevoNodo;
        } else {
            Nodo padre = dondeInsertar(raiz);
            nuevoNodo.padre = padre;
            if (padre.hijoIzquierdo == null) {
                padre.hijoIzquierdo = nuevoNodo;
            } else {
                padre.hijoDerecho = nuevoNodo;
            }
        }

        tamaño++;
        actualizarTamaños(raiz);
        siftUp(nuevoNodo);
        return nuevoHandle;
    }

    public T mostrarMax() {
        if (raiz == null) {
            return null;
        }
        return raiz.dato;
    }

    public T extraerMax() {
        if (raiz == null) {
            return null;
        }

        T maximo = raiz.dato;

        if (tamaño == 1) {
            raiz = null;
            tamaño--;
            return maximo;
        }

        Nodo ultimo = obtenerUltimoNodo(raiz);
        raiz.dato = ultimo.dato;

        if (ultimo.padre.hijoIzquierdo == ultimo) {
            ultimo.padre.hijoIzquierdo = null;
        } else {
            ultimo.padre.hijoDerecho = null;
        }

        tamaño--;
        actualizarTamaños(raiz);
        siftDown(raiz);

        return maximo;
    }

    private void intercambiar(Nodo a, Nodo b) {
        T guardarDato = a.dato;
        a.dato = b.dato;
        b.dato = guardarDato;
    }

    private void siftUp(Nodo nodo) {
        while (nodo.padre != null && nodo.dato.compareTo(nodo.padre.dato) > 0) {
            intercambiar(nodo, nodo.padre);
            nodo = nodo.padre;
        }
    }

    private void siftDown(Nodo nodo) {
        while (true) {
            Nodo mayor = nodo;

            if (nodo.hijoIzquierdo != null && nodo.hijoIzquierdo.dato.compareTo(mayor.dato) > 0) {
                mayor = nodo.hijoIzquierdo;
            }

            if (nodo.hijoDerecho != null && nodo.hijoDerecho.dato.compareTo(mayor.dato) > 0) {
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

    private Nodo dondeInsertar(Nodo actual) {
        actual.tamañoSubarbol++;

        if (actual.hijoIzquierdo == null || actual.hijoDerecho == null) {
            return actual;
        }

        if (actual.hijoIzquierdo.tamañoSubarbol <= actual.hijoDerecho.tamañoSubarbol) {
            return dondeInsertar(actual.hijoIzquierdo);
        } else {
            return dondeInsertar(actual.hijoDerecho);
        }
    }

    private int actualizarTamaños(Nodo nodo) {
        if (nodo == null) return 0;
        int izq = actualizarTamaños(nodo.hijoIzquierdo);
        int der = actualizarTamaños(nodo.hijoDerecho);
        nodo.tamañoSubarbol = 1 + izq + der;
        return nodo.tamañoSubarbol;
    }

    private Nodo obtenerUltimoNodo(Nodo actual) {
        if (actual.hijoIzquierdo == null && actual.hijoDerecho == null) {
            return actual;
        }

        if (actual.hijoDerecho == null) {
            return obtenerUltimoNodo(actual.hijoIzquierdo);
        }

        if (actual.hijoIzquierdo.tamañoSubarbol >= actual.hijoDerecho.tamañoSubarbol) {
            return obtenerUltimoNodo(actual.hijoDerecho);
        } else {
            return obtenerUltimoNodo(actual.hijoIzquierdo);
        }
    }
}
