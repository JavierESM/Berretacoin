package aed;

public class Transaccion implements Comparable<Transaccion>, metodosHeap {
    private int id;
    private int id_comprador;
    private int id_vendedor;
    private int monto;
    private int indiceHeap;

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

    @Override
    public void setearIndiceHeap(int i) {
        this.indiceHeap = i;
    }

    @Override
    public int obtenerIndiceHeap() {
        return this.indiceHeap;
    }

    @Override
    public int compareTo(Transaccion otro) {
        return Integer.compare(this.id, otro.id);  
    }
}
