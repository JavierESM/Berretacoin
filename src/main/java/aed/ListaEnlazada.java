package aed;

public class ListaEnlazada<T> {
    private Nodo cabeza;
    private Nodo cola;
    private int longitud;

    private class Nodo {
        T dato;
        Nodo anterior;
        Nodo siguiente;

        Nodo(T dato) { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
            this.dato = dato; /*Operación básica, O(1) */
            siguiente = anterior = null; /*Operación básica, O(1) */
        }
    }

    public class Handle {
        private Nodo nodoApuntado;

        private Handle(Nodo nodo) { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
            this.nodoApuntado = nodo; /*Operación básica, O(1) */
        }

        public T obtener() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
            return nodoApuntado.dato; /*Operación básica, O(1) */
        }
    }

    public ListaEnlazada() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
        cabeza = null; /*Operación básica, O(1) */
        cola = null; /*Operación básica, O(1) */
        longitud = 0; /*Operación básica, O(1) */
    }

    public Nodo cabeza() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
        return this.cabeza; /*Operación básica, O(1) */
    }

    public Nodo cola() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
        return this.cola; /*Operación básica, O(1) */
    }

    public int longitud() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
        return longitud; /*Operación básica, O(1) */
    }

    public Handle agregarAtras(T elem) { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
        Nodo nuevo = new Nodo(elem); /*Operación básica, O(1) */
        Handle handle = new Handle(nuevo); /*Operación básica, O(1) */
        if (cabeza == null) {
            cabeza = cola = nuevo; /*Operación básica, O(1) */
        } else {
            nuevo.anterior = cola; /*Operación básica, O(1) */
            cola.siguiente = nuevo; /*Operación básica, O(1) */
            cola = nuevo; /*Operación básica, O(1) */
        }
        longitud++; /*Operación básica, O(1) */
        return handle; /*Operación básica, O(1) */
    }

    public T obtener(int i) { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(n), n siendo la posición accedida */
        if (i < 0 || i >= longitud()) { /*Operación básica, O(1) */
            throw new IndexOutOfBoundsException("El índice se fué de rango"); /*Operación básica, O(1) */
        }
        Nodo actual = cabeza; /*Operación básica, O(1) */
        for (int j = 0; j < i; j++) { /*Recorre n eleementos, O(n) */
            actual = actual.siguiente; /*Operación básica, O(1) */
        }
        return actual.dato; /*Operación básica, O(1) */
    }

    public void eliminar(Handle h) { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
        Nodo actual = h.nodoApuntado; /*Operación básica, O(1) */
        if (actual == null) {
            return; /*Operación básica, O(1) */
        }
        if (actual.anterior != null) {
            actual.anterior.siguiente = actual.siguiente; /*Operación básica, O(1) */
        }
        if (actual.siguiente != null) {
            actual.siguiente.anterior = actual.anterior; /*Operación básica, O(1) */
        }
        if (cabeza == actual) {
            cabeza = actual.siguiente; /*Operación básica, O(1) */
        }
        if (cola == actual) {
            cola = actual.anterior; /*Operación básica, O(1) */
        }
        h.nodoApuntado = null; /*Operación básica, O(1) */
        longitud--; /*Operación básica, O(1) */
    }


    public Iterador<T> iterador() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
        return new ListaIterador(); /*Operación básica, O(1) */
    }

    public class ListaIterador implements Iterador<T> {
        private Nodo actual;
        private Nodo ultimoReturn;

        public ListaIterador() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
            this.actual = cabeza; /*Operación básica, O(1) */
            this.ultimoReturn = null; /*Operación básica, O(1) */
        }

        public boolean haySiguiente() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
            return actual != null; /*Operación básica, O(1) */
        }

        public boolean hayAnterior() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
            return ultimoReturn != null; /*Operación básica, O(1) */
        }

        public T siguiente() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
            if (!haySiguiente()) {
                throw new IllegalStateException("Este es el head"); /*Operación básica, O(1) */
            }
            ultimoReturn = actual; /*Operación básica, O(1) */
            T dato = actual.dato; /*Operación básica, O(1) */
            actual = actual.siguiente; /*Operación básica, O(1) */
            return dato; /*Operación básica, O(1) */
        }

        public T anterior() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
            if (!hayAnterior()) {
                throw new IllegalStateException("Este es la cola"); /*Operación básica, O(1) */
            }
            actual = ultimoReturn; /*Operación básica, O(1) */
            T dato = actual.dato; /*Operación básica, O(1) */
            ultimoReturn = actual.anterior; /*Operación básica, O(1) */
            return dato; /*Operación básica, O(1) */
        }
    }
}
