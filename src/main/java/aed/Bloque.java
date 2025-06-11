package aed;

public class Bloque {
    public Heap<Transaccion> transaccionesHeap;
    public ListaEnlazada<Transaccion> transaccionesLista;

    public Bloque() {
        transaccionesHeap = new Heap<>();
        transaccionesLista = new ListaEnlazada<>();
    }

    public Bloque(Heap<Transaccion> heap, ListaEnlazada<Transaccion> lista) {
        this.transaccionesHeap = heap;
        this.transaccionesLista = lista;
    }
}
