package aed;

public class ListaEnlazada<T> {
    private Nodo cabeza;
    private Nodo cola;
    private int longitud;

    private class Nodo {
        T dato;
        Nodo anterior;
        Nodo siguiente;

        Nodo(T dato) {
            this.dato = dato;
            siguiente = anterior = null;
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
    }


    public ListaEnlazada() {
        cabeza = null;
        cola = null;
        longitud = 0;
    }
    
    public Nodo cabeza(){
        return this.cabeza;
    }

    public Nodo cola(){
        return this.cola;
    }

    public int longitud() {
        return longitud;
    }

    public Handle agregarAtras(T elem) {
    Nodo nuevo = new Nodo(elem);
    Handle handle = new Handle(nuevo);
    if (cabeza == null) {
        cabeza = cola = nuevo;
    } else {
        nuevo.anterior = cola;
        cola.siguiente = nuevo;
        cola = nuevo;
    }
    longitud++;
    return handle;
}

   
    public T obtener(int i) {
        if (i < 0 || i >= longitud()) {
            throw new IndexOutOfBoundsException("El índice se fué de rango");
        }
        Nodo actual = cabeza;
        for (int j = 0; j < i; j++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    public void eliminar(Handle h) {
        Nodo actual = h.nodoApuntado;
        if (actual == null) {
            return;
            }
        if (actual.anterior != null) {
            actual.anterior.siguiente = actual.siguiente;
            }
        if (actual.siguiente != null) {
            actual.siguiente.anterior = actual.anterior;
            }
        if (cabeza == actual) {
            cabeza = actual.siguiente;
            }
        if (cola == actual) {
            cola = actual.anterior;
            }
        h.nodoApuntado = null;
        longitud--;
        }



    public ListaEnlazada(ListaEnlazada<T> lista) {
        this();
        Nodo actual = lista.cabeza;
        while (actual != null) {
            agregarAtras(actual.dato);
            actual = actual.siguiente;
        }
    }

    public Iterador<T> iterador() {
    return new ListaIterador();
}

public class ListaIterador implements Iterador<T> {
    private Nodo actual;
    private Nodo ultimoReturn;

    public ListaIterador() {
        this.actual = cabeza;
        this.ultimoReturn = null;
    }

    public boolean haySiguiente() {
        return actual != null;
    }

    public boolean hayAnterior() {
        return ultimoReturn != null;
    }

    public T siguiente() {
        if (!haySiguiente()) {
            throw new IllegalStateException("Este es el head");
        }
        ultimoReturn = actual;
        T dato = actual.dato;
        actual = actual.siguiente;
        return dato;
    }

    public T anterior() {
        if (!hayAnterior()) {
            throw new IllegalStateException("Este es la cola");
        }
        actual = ultimoReturn;
        T dato = actual.dato;
        ultimoReturn = actual.anterior;
        return dato;
        }
    } 
}