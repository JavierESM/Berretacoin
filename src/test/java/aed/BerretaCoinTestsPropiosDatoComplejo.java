package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;


public class BerretaCoinTestsPropios {
    
    @Test
    public void empateMontosTx(){
       /* Se mide que sucede cuando empatan todas las transacciones en el bloque. 
       Si bien las transacciones se deben agregar en orden y de manera incremental acá estamos probando
        qué pasa si se agregan transacciones de montos distintos para probar el heapify*/

        Berretacoin testcoin = new Berretacoin(10);
    
        Transaccion[] creacion = new Transaccion[]{
            new Transaccion(0, 0, 1, 1),
            new Transaccion(25, 0, 2, 1),
            new Transaccion(45, 0, 3, 1),
            new Transaccion(5000, 0, 4, 1),
            new Transaccion(1, 0, 5, 1),
            new Transaccion(46, 0, 6, 1)
        };


        testcoin.agregarBloque(creacion);

        assertEquals(new Transaccion(5000, 0, 4, 1), testcoin.txMayorValorUltimoBloque());
    }
    @Test
    public void transaccionesDesorden(){
        /*Se prueba que sucede cuando se agregan transacciones en montos que no siguen un orden*/
 
         Berretacoin testcoin = new Berretacoin(10);
         Transaccion[] creacion = new Transaccion[]{
             new Transaccion(0, 0, 1, 5),
             new Transaccion(1, 0, 2, 8),
             new Transaccion(2, 0, 3, 3),
             new Transaccion(3, 0, 4, 9),
             new Transaccion(4, 0, 5, 6),
             new Transaccion(5, 0, 6, 6)
         };
 
 
         testcoin.agregarBloque(creacion);
 
         assertEquals(new Transaccion(3, 0, 4, 9), testcoin.txMayorValorUltimoBloque());
     }
    @Test 
    public void empateMontosU (){
        /*Se prueba que sucede en el empate de montos de usuarios */
        Berretacoin testcoin = new Berretacoin(10);

        assertEquals(new Usuario(1, 0).id(), testcoin.maximoTenedor());
    } 

    @Test
    public void eliminarTransacciones(){
        Berretacoin testcoin = new Berretacoin(10);

        Transaccion[] transacciones = new Transaccion[]{
            new Transaccion(0, 0, 1, 10),
            new Transaccion(1, 1, 2, 2),
            new Transaccion(2, 2, 3, 2),
            new Transaccion(3, 1, 4, 3),
            new Transaccion(4, 4, 1, 1),
            new Transaccion(5, 1, 2, 5)
        };

        Transaccion[] transaccionesCopia1 = new Transaccion[]{
            
            new Transaccion(1, 1, 2, 2),
            new Transaccion(2, 2, 3, 2),
            new Transaccion(3, 1, 4, 3),
            new Transaccion(4, 4, 1, 1),
            new Transaccion(5, 1, 2, 5)
        };

        Transaccion[] transaccionesCopia2 = new Transaccion[]{
      
            new Transaccion(1, 1, 2, 2),
            new Transaccion(2, 2, 3, 2),
            new Transaccion(3, 1, 4, 3),
            new Transaccion(4, 4, 1, 1),

        };

        Transaccion[] transaccionesCopia3 = new Transaccion[]{
            new Transaccion(1, 1, 2, 2),
            new Transaccion(2, 2, 3, 2),
            new Transaccion(4, 4, 1, 1),
        };
        testcoin.agregarBloque(transacciones);
        ListaEnlazada<Transaccion> listaTx = new ListaEnlazada<>(); 
        Iterador<Transaccion> it = listaTx.iterador(); 
         

        for (Transaccion t : transacciones) { 
            ListaEnlazada<Transaccion>.Handle handleLista = listaTx.agregarAtras(t);
            t.setearHandleLL(handleLista); 
        }

        ListaEnlazada<Transaccion>.Handle hCabeza = testcoin.txMayorValorUltimoBloque().obtenerHandleLL();
        listaTx.eliminar(hCabeza);
        Transaccion array1[] = new Transaccion[transaccionesCopia1.length];
        int i = 0;
        Iterador<Transaccion> itLista = listaTx.iterador();
        while (itLista.haySiguiente()) {
            array1[i] = itLista.siguiente();
            i++;       
        }
        for (int j = 0; j < transaccionesCopia1.length - 1; j++){
            assertEquals(transaccionesCopia1[j], array1[j]);
        }
        


        ListaEnlazada<Transaccion>.Handle hCola = testcoin.txMayorValorUltimoBloque().obtenerHandleLL();
        listaTx.eliminar(hCola);
        ArrayList<Transaccion> array2 = new ArrayList<>(); 
        while (it.haySiguiente()) {
            array2.add(it.siguiente());
        }
        for (int j = 0; j < transaccionesCopia1.length - 1; j++){
            assertEquals(transaccionesCopia1[j], array1[j]);
        }
        ListaEnlazada<Transaccion>.Handle hMedio = testcoin.txMayorValorUltimoBloque().obtenerHandleLL();
        listaTx.eliminar(hMedio);
        ArrayList<Transaccion> array3 = new ArrayList<>(); 
        while (it.haySiguiente()) {
            array3.add(it.siguiente());
        }
        for (int j = 0; j < transaccionesCopia1.length - 1; j++){
            assertEquals(transaccionesCopia1[j], array1[j]);
        }
    }   
    @Test
        public void transaccionSaldoInsuficciente() {
            Berretacoin sistema = new Berretacoin(3);

            Transaccion[] bloque = {
                new Transaccion(0, 1, 2, 50) 
            };

            sistema.agregarBloque(bloque);

            assertEquals(1, sistema.maximoTenedor()); 
            assertEquals(0, sistema.txUltimoBloque().length); 
        }



}
