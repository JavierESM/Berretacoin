package aed;

public class Bloque {
    public Heap<Transaccion> transaccionesHeap;
    public ListaEnlazada<Transaccion> transaccionesLista;

    public Bloque() { /*Complejidad O(1) */
        transaccionesHeap = new Heap<>(); /*Operación básica */
        transaccionesLista = new ListaEnlazada<>(); /*Operación básica */
    }

    public Bloque(Heap<Transaccion> heap, ListaEnlazada<Transaccion> lista) {
        this.transaccionesHeap = heap; /*Operación básica */
        this.transaccionesLista = lista; /*Operación básica */
    }
}
