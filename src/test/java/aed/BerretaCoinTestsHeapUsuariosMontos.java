package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BerretaCoinTestsHeapUsuariosMontos {

     @Test
    public void HeapMaximoTenedor(){
    Berretacoin testcoin = new Berretacoin(4);

    Transaccion[] transacciones = new Transaccion[]{
            new Transaccion(0, 0, 1, 10),
            new Transaccion(1, 1, 2, 2),
            new Transaccion(2, 2, 3, 2),
            new Transaccion(3, 1, 4, 3),
            new Transaccion(4, 4, 1, 1),
            new Transaccion(5, 1, 2, 5)};

        testcoin.agregarBloque(transacciones);

        Heap<Usuario> usuariosPorMonto = new Heap<>();
        List<Usuario> usuariosCargados = new ArrayList<>(); 

        for (int i = 1; i <= 4; i++) { 
                Usuario usuario = new Usuario(i, 0); 
                usuariosCargados.add(usuario); 
            }
        List<Heap<Usuario>.HandleHeap> handles = usuariosPorMonto.insertarDesdeLista(usuariosCargados);
        Heap<Usuario>.HandleHeap uno = handles.get(0);
        Heap<Usuario>.HandleHeap dos = handles.get(1);
        Heap<Usuario>.HandleHeap tres = handles.get(2);
        Heap<Usuario>.HandleHeap cuatro = handles.get(3);   
        usuariosCargados.get(0).setearMonto(1);
        usuariosCargados.get(1).setearMonto(5);
        usuariosCargados.get(2).setearMonto(2);
        usuariosCargados.get(3).setearMonto(2);
        usuariosPorMonto.editar(uno);
        usuariosPorMonto.editar(dos);
        usuariosPorMonto.editar(tres);   
        usuariosPorMonto.editar(cuatro);    
        assertEquals(testcoin.maximoTenedor(), usuariosPorMonto.mostrarMaximo().id());
    }

    @Test
        public void maximovariante(){
            Berretacoin testcoin = new Berretacoin(4);

        Transaccion[] transacciones = new Transaccion[]{
        new Transaccion(0, 0, 1, 1),
        new Transaccion(1, 1, 2, 1),
        new Transaccion(2, 2, 3, 1),
        new Transaccion(3, 3, 4, 1),
        new Transaccion(4, 4, 1, 1),
        new Transaccion(5, 1, 2, 1)};
        testcoin.agregarBloque(transacciones);
        Heap<Usuario> usuariosPorMonto = new Heap<>();
        List<Usuario> usuariosCargados = new ArrayList<>(); 

        for (int i = 1; i <= 4; i++) { 
                Usuario usuario = new Usuario(i, 0); 
                usuariosCargados.add(usuario); 
            }
        List<Heap<Usuario>.HandleHeap> handles = usuariosPorMonto.insertarDesdeLista(usuariosCargados);
        Heap<Usuario>.HandleHeap uno = handles.get(0);
        Heap<Usuario>.HandleHeap dos = handles.get(1);
        Heap<Usuario>.HandleHeap tres = handles.get(2);
        Heap<Usuario>.HandleHeap cuatro = handles.get(3);
        usuariosCargados.get(1).setearMonto(1);
        usuariosPorMonto.editar(dos);
        assertEquals(testcoin.maximoTenedor(), usuariosPorMonto.mostrarMaximo().id());

        testcoin.hackearTx();
        usuariosCargados.get(1).setearMonto(0);
        usuariosCargados.get(0).setearMonto(1);
        usuariosPorMonto.editar(uno);   
        assertEquals(testcoin.maximoTenedor(), usuariosPorMonto.mostrarMaximo().id());

        testcoin.hackearTx();
        usuariosCargados.get(0).setearMonto(0);
        usuariosCargados.get(3).setearMonto(1);
        usuariosPorMonto.editar(dos);  
        usuariosPorMonto.editar(cuatro); 
        assertEquals(testcoin.maximoTenedor(), usuariosPorMonto.mostrarMaximo().id());

        testcoin.hackearTx();
        usuariosCargados.get(3).setearMonto(0);
        usuariosCargados.get(2).setearMonto(1);
        usuariosPorMonto.editar(tres);
        assertEquals(testcoin.maximoTenedor(), usuariosPorMonto.mostrarMaximo().id());
        }

}