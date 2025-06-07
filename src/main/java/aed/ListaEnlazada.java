package aed;

public class ListaEnlazada<T> {
    private Nodo cabeza;
    private Nodo cola;

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
    }

    public int longitud() {
        int contador = 0;
        for (Nodo actual = cabeza; actual != null; actual = actual.siguiente) {
            contador++;
        }
        return contador;
    }

    public void agregarAdelante(T elem) {
        Nodo otro = new Nodo(elem);
        otro.siguiente = cabeza;
        if (cabeza != null) {
            cabeza.anterior = otro;
        }
        cabeza = otro;
        if (cola == null) {
            cola = otro;
        }
    }

    public void agregarAtras(T elem) {
        Nodo otro = new Nodo(elem);
        if (cabeza == null) {
            cabeza = otro;
            cola = otro;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            otro.anterior = actual;
            actual.siguiente = otro;
            cola = otro;
        }
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

    public void eliminar(int i) {
        if (cabeza == null) {
            return;
        }
        Nodo actual = cabeza;
        for (int j = 0; actual != null && j < i; j++) {
            actual = actual.siguiente;
        }
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
    }

    public void modificarPosicion(int indice, T elem) {
        if (indice < 0 || indice >= longitud()) {
            throw new IndexOutOfBoundsException("El índice se fue de rango");
        }
        Nodo actual = cabeza;
        for (int i = 0; actual != null && i < indice; i++) {
            actual = actual.siguiente;
        }
        actual.dato = elem;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        this();
        Nodo actual = lista.cabeza;
        while (actual != null) {
            agregarAtras(actual.dato);
            actual = actual.siguiente;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Nodo actual = cabeza;
        while (actual != null) {
            sb.append(actual.dato);
            actual = actual.siguiente;
            if (actual != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public Iterador<T> iterador() {
    return new ListaIterador();
}

private class ListaIterador implements Iterador<T> {
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