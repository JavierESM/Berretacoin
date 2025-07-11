package aed;

public class Bloque {
    public Heap<Transaccion> transaccionesHeap;
    public ListaEnlazada<Transaccion> transaccionesLista;

    public Bloque() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
        transaccionesHeap = new Heap<>(); /*Operación básica, O(1) */
        transaccionesLista = new ListaEnlazada<>(); /*Operación básica, O(1) */
    }

    public Bloque(Heap<Transaccion> heap, ListaEnlazada<Transaccion> lista) { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
        this.transaccionesHeap = heap; /*Operación básica, O(1) */
        this.transaccionesLista = lista; /*Operación básica, O(1) */
    }
}
