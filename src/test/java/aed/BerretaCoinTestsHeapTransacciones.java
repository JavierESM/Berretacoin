package aed;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BerretaCoinTestsHeapTransacciones {
    @Test
    public void HeapMaximaTransaccion(){
    Berretacoin testcoin = new Berretacoin(4);

    Transaccion[] transacciones = new Transaccion[]{
            new Transaccion(0, 0, 1, 10),
            new Transaccion(1, 1, 2, 2),
            new Transaccion(2, 2, 3, 2),
            new Transaccion(3, 1, 4, 3),
            new Transaccion(4, 4, 1, 1),
            new Transaccion(5, 1, 2, 5)};

        testcoin.agregarBloque(transacciones);

        Heap<Transaccion> heapTransacciones = new Heap<>();
        List<Transaccion> listatransacciones = new ArrayList<>();
        listatransacciones.add(new Transaccion(0, 0, 1, 10));
        listatransacciones.add(new Transaccion(1, 1, 2, 2));
        listatransacciones.add(new Transaccion(2, 2, 3, 2));
        listatransacciones.add(new Transaccion(3, 1, 4, 3));
        listatransacciones.add(new Transaccion(4, 4, 1, 1));
        listatransacciones.add(new Transaccion(5, 1, 2, 5));
        heapTransacciones.insertarDesdeLista(listatransacciones);
        Transaccion maximo = heapTransacciones.mostrarMaximo();
        

        assertEquals(testcoin.txMayorValorUltimoBloque(), new Transaccion(maximo.id(),maximo.id_comprador(),maximo.id_vendedor(),maximo.monto()));
}
@Test
    public void HeapHackearMaximaTransaccion(){
 Berretacoin testcoin = new Berretacoin(4);

    Transaccion[] transacciones = new Transaccion[]{
            new Transaccion(0, 0, 1, 1),
            new Transaccion(1, 1, 2, 1),
            new Transaccion(2, 2, 3, 1),
            new Transaccion(3, 3, 4, 1),
            new Transaccion(4, 4, 2, 1)};
    testcoin.agregarBloque(transacciones);

    Heap<Transaccion> heapTransacciones = new Heap<>();
    List<Transaccion> listaTransacciones = new ArrayList<>(Arrays.asList(transacciones));
    
    heapTransacciones.insertarDesdeLista(listaTransacciones);
    Transaccion maximo1 = heapTransacciones.mostrarMaximo();

    assertEquals(testcoin.txMayorValorUltimoBloque(), new Transaccion(maximo1.id(),maximo1.id_comprador(),maximo1.id_vendedor(),maximo1.monto()));
    
    testcoin.hackearTx();
    heapTransacciones.extraerMaximo();
    Transaccion maximo2 = heapTransacciones.mostrarMaximo();
    assertEquals(testcoin.txMayorValorUltimoBloque(), new Transaccion(maximo2.id(),maximo2.id_comprador(),maximo2.id_vendedor(),maximo2.monto()));
    
    testcoin.hackearTx();
    heapTransacciones.extraerMaximo();
    Transaccion maximo3 = heapTransacciones.mostrarMaximo();
    assertEquals(testcoin.txMayorValorUltimoBloque(), new Transaccion(maximo3.id(),maximo3.id_comprador(),maximo3.id_vendedor(),maximo3.monto()));
    
    testcoin.hackearTx();
    heapTransacciones.extraerMaximo();
    Transaccion maximo4 = heapTransacciones.mostrarMaximo();
    assertEquals(testcoin.txMayorValorUltimoBloque(), new Transaccion(maximo4.id(),maximo4.id_comprador(),maximo4.id_vendedor(),maximo4.monto()));

}
}