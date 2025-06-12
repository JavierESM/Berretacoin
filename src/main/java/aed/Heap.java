package aed;

public class Heap<T extends Comparable<T>> {
    private Nodo raiz;
    private int tamaño;

    private class Nodo {
        T dato;
        Nodo padre, hijoIzquierdo, hijoDerecho;
        int tamañoSubarbol;
        HandleHeap aSiMismo;

        Nodo(T dato) {
            this.dato = dato;
            this.tamañoSubarbol = 1;

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

    public HandleHeap insertar(T nuevoValor) {
        Nodo nuevoNodo = new Nodo(nuevoValor);
        HandleHeap nuevoHandle = new HandleHeap(nuevoNodo);
        nuevoNodo.aSiMismo = nuevoHandle;

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

    public void editar(HandleHeap handle) {
    
            System.out.println("Llamando siftUp y siftDown (igualdad)...");
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
        if (raiz == null) {
            return null;
        }
        System.out.println("=== extraerMaximo() → root.id=" + raiz.dato
                       + " monto=" + raiz.dato + " ===");

        HandleHeap handleMaximo = raiz.aSiMismo;

        if (tamaño == 1) {
            raiz = null;
            tamaño--;
            return handleMaximo;
        }

        Nodo ultimo = obtenerUltimoNodo(raiz);
        raiz.dato = ultimo.dato;
        raiz.aSiMismo = ultimo.aSiMismo;
        if (raiz.aSiMismo != null) {
        raiz.aSiMismo.nodoApuntado = raiz;
        }


        if (ultimo.padre.hijoIzquierdo == ultimo) {
            ultimo.padre.hijoIzquierdo = null;
        } else {
            ultimo.padre.hijoDerecho = null;
        }

        tamaño--;
        actualizarTamaños(raiz);
        siftDown(raiz);

        return handleMaximo;
    }

    private void intercambiar(Nodo a, Nodo b) {
    System.out.println("Intercambiando nodo " + a.dato + " con nodo " + b.dato);
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
        if (nodo == null) {
            return 0;
        }
        int subIzquierdo = actualizarTamaños(nodo.hijoIzquierdo);
        int subDerecho = actualizarTamaños(nodo.hijoDerecho);
        nodo.tamañoSubarbol = 1 + subIzquierdo + subDerecho;
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

    public int tamaño(){
        return this.tamaño;
    }


    public void imprimirHeap() {
        imprimirDesde(raiz, "");
    }

    private void imprimirDesde(Nodo nodo, String indent) {
        if (nodo == null) return;
        imprimirDesde(nodo.hijoDerecho, indent + "   ");
        System.out.println(indent + nodo.dato.toString());
        imprimirDesde(nodo.hijoIzquierdo, indent + "   ");
    }
}
