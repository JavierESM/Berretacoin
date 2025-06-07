package aed;

public class Usuario implements Comparable<Usuario>, metodosHeap{
    private int id;
    private int monto;
    private Heap<Usuario>.Handle handleDelheap;
    private ListaEnlazada<Usuario>.Handle handleDeLista;

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

     @Override
    public void setearIndiceHeap(int i) {
        this.indiceHeap = i;
    }

    @Override
    public int obtenerIndiceHeap() {
        return this.indiceHeap;
    }

    @Override
    public int compareTo(Usuario otro) {
        return Integer.compare(this.id, otro.id);  
    }
}
