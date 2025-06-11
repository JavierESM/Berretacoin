package aed;

import aed.ListaEnlazada.Handle;
import aed.Heap.HandleHeap;

public class Transaccion implements Comparable<Transaccion> {
    private int id;
    private int id_comprador;
    private int id_vendedor;
    private int monto;
    private Heap<Transaccion>.HandleHeap handleDelheap;
    private ListaEnlazada<Transaccion>.Handle handleDeLL;



    public Transaccion(int id, int id_comprador, int id_vendedor, int monto) {
        this.id = id;
        this.id_comprador = id_comprador;
        this.id_vendedor = id_vendedor;
        this.monto = monto;
    }

    public int monto() {
        return monto;
    }

    public int id() {
        return id;
    }

    public int id_comprador() {
        return id_comprador;
    }

    public int id_vendedor() {
        return id_vendedor;
    }

    public void setearMonto(int nuevoMonto) {
        this.monto = nuevoMonto;
    }

    public void setearHandleHeapT(Heap<Transaccion>.HandleHeap handle) {
        this.handleDelheap = handle;
    }

    public void setearHandleLL(ListaEnlazada<Transaccion>.Handle handle) {
        this.handleDeLL = handle;
    }

    public HandleHeap obtenerHandleHeapU() {
        return this.handleDelheap;
    }

    public Handle obtenerHandleLL() {
        return this.handleDeLL;
    }

    @Override
    public int compareTo(Transaccion otro) {
        return Integer.compare(this.monto, otro.monto);  
    }

    @Override
    public boolean equals(Object transaccion) {
        if (this == transaccion) return true;
        if (!(transaccion instanceof Transaccion)) return false;
        Transaccion t = (Transaccion) transaccion;
        return this.id == t.id;
    }
}
