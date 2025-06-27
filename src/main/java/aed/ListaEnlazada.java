package aed;

public class ListaEnlazada<T> {
    private Nodo cabeza;
    private Nodo cola;
    private int longitud;

    private class Nodo {
        T dato;
        Nodo anterior;
        Nodo siguiente;

        Nodo(T dato) { /*Complejidad básica O(1) */
            this.dato = dato; /*Operación básica, O(1) */
            siguiente = anterior = null; /*Operación básica, O(1) */
        }
    }

    public class Handle { 
        private Nodo nodoApuntado;

        private Handle(Nodo nodo) { /*Complejidad básica O(1) */
            this.nodoApuntado = nodo; /*Operación básica, O(1) */
        }

        public T obtener() { /*Complejidad básica O(1) */
            return nodoApuntado.dato; /*Operación básica, O(1) */
        }
    }


    public ListaEnlazada() { /*Complejidad básica O(1) */
        cabeza = null; /*Operación básica, O(1) */
        cola = null; /*Operación básica, O(1) */
        longitud = 0; /*Operación básica, O(1) */
    }

    public int longitud() { /*Complejidad básica O(1) */
        return longitud; /*Operación básica, O(1) */
    }

    public Handle agregarAtras(T elem) { /*Complejidad O(1) gracias a que se utiliza el atributo cola de la clase y no se recorre toda la lista para agregar el nodo*/
    Nodo nuevo = new Nodo(elem); /*Operación básica, O(1) */
    Handle handle = new Handle(nuevo); /*Operación básica, O(1) */
    if (cabeza == null) { /*Operación básica, O(1) */
        cabeza = cola = nuevo; /*Operación básica, O(1) */
    } else {
        nuevo.anterior = cola; /*Operación básica, O(1) */
        cola.siguiente = nuevo; /*Operación básica, O(1) */
        cola = nuevo; /*Operación básica, O(1) */
    }
    longitud++; /*Operación básica, O(1) */
    return handle; /*Operación básica, O(1) */
}

   

    public void eliminar(Handle h) { /*Complejidad O(1), gracias al handle se ubica el nodo rápidamente */
        Nodo actual = h.nodoApuntado; /*Operación básica, O(1) */
        if (actual == null) { /*Operación básica, O(1) */
            return; /*Operación básica, O(1) */
            }
        if (actual.anterior != null) { /*Operación básica, O(1) */
            actual.anterior.siguiente = actual.siguiente; /*Operación básica, O(1) */
            }
        if (actual.siguiente != null) { /*Operación básica, O(1) */
            actual.siguiente.anterior = actual.anterior; /*Operación básica, O(1) */
            }
        if (cabeza == actual) { /*Operación básica, O(1) */
            cabeza = actual.siguiente; /*Operación básica, O(1) */
            }
        if (cola == actual) { /*Operación básica, O(1) */
            cola = actual.anterior; /*Operación básica, O(1) */
            }
        h.nodoApuntado = null; /*Operación básica, O(1) */
        longitud--; /*Operación básica, O(1) */
        }



    public Iterador<T> iterador() { /*Complejidad O(1) */
    return new ListaIterador(); /*Operación básica, O(1) */
}

public class ListaIterador implements Iterador<T> {
    private Nodo actual; 
    private Nodo ultimoReturn;

    public ListaIterador() { /*Complejidad O(1) */
        this.actual = cabeza; /*Operación básica, O(1) */
        this.ultimoReturn = null; /*Operación básica, O(1) */
    }

    public boolean haySiguiente() { /*Complejidad O(1) */
        return actual != null; /*Operación básica, O(1) */
    }

    public boolean hayAnterior() { /*Complejidad O(1) */
        return ultimoReturn != null; /*Operación básica, O(1) */
    }

    public T siguiente() {
        if (!haySiguiente()) { /*Operación básica, O(1) */
            throw new IllegalStateException("Este es el head"); /*Operación básica, O(1) */
        }
        ultimoReturn = actual; /*Operación básica, O(1) */
        T dato = actual.dato; /*Operación básica, O(1) */
        actual = actual.siguiente; /*Operación básica, O(1) */
        return dato; /*Operación básica, O(1) */
    }

    public T anterior() { /*Complejidad O(1)*/
        if (!hayAnterior()) { /*Operación básica, O(1) */
            throw new IllegalStateException("Este es la cola"); /*Operación básica, O(1) */
        }
        actual = ultimoReturn; /*Operación básica, O(1) */
        T dato = actual.dato; /*Operación básica, O(1) */
        ultimoReturn = actual.anterior; /*Operación básica, O(1) */
        return dato; /*Operación básica, O(1) */
        }
    } 
}