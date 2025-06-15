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
            if (this.monto != otro.monto) {
                return Integer.compare(otro.monto, this.monto); 
            }
            return Integer.compare(otro.id, this.id); 
        }

    @Override
        public boolean equals(Object objeto) {
            if (this == objeto) {
                return true;
            }

            Transaccion otra = (Transaccion) objeto;

            boolean mismoId = this.id == otra.id;
            boolean mismoComprador = this.id_comprador == otra.id_comprador;
            boolean mismoVendedor = this.id_vendedor == otra.id_vendedor;
            boolean mismoMonto = this.monto == otra.monto;

            if (mismoId && mismoComprador && mismoVendedor && mismoMonto) {
                return true;
            } else {
                return false;
            }
        }
}


