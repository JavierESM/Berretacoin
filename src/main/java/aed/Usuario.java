package aed;

import aed.Heap.HandleHeap;
import aed.Array.Handle;

public class Usuario implements Comparable<Usuario>{
    private int id;
    private int monto;
    private Heap<Usuario>.HandleHeap handleDelheap;
    private Array<Usuario>.Handle handleDelArray;
    

    public Usuario (int id, int monto) {
        this.id = id;
        this.monto = monto;
    }

    public int id() {
        return id;
    }

    public int monto(){
        return monto;
    }
    
    public void setearMonto(int nuevoMonto) {

        this.monto = nuevoMonto;
    }

    public void setearHandleHeapU(Heap<Usuario>.HandleHeap handle) {
        this.handleDelheap = handle;
    }

    public void setearHandleArray(Array<Usuario>.Handle handle) {
        this.handleDelArray = handle;
    }

    public HandleHeap obtenerHandleHeapU() {
        return this.handleDelheap;
    }

    public Handle obtenerHandleArray() {
        return this.handleDelArray;
    }

    @Override
        public int compareTo(Usuario otro) {
            if (this.monto != otro.monto) {
                return Integer.compare(otro.monto, this.monto); 
            }
            return Integer.compare(this.id, otro.id); 
        }
}
