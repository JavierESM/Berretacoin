package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BerrecaCoinTestsPropios {

    // Helper class para trackear saldos de usuarios
    private class SaldoTracker {
        private Map<Integer, Integer> saldos;
        
        public SaldoTracker(int usuarios) {
            saldos = new HashMap<>();
            for (int i = 1; i <= usuarios; i++) {
                saldos.put(i, 0);
            }
        }
    
        
        public int getSaldo(int usuario) {
            return saldos.get(usuario);
        }
        
        public boolean puedeGastar(int usuario, int monto) {
            return usuario == 0 || getSaldo(usuario) >= monto;
        }
        
    }

    @Test
    public void testHackearTxEnBloqueVacio() {
        Transaccion[] transaccionesVacias = new Transaccion[]{
            new Transaccion(0, 0, 1, 1)
        };
        Transaccion[] transaccionesVacias2 = new Transaccion[]{
            new Transaccion(0, 0, 2, 1)
        };
        Berretacoin bc = new Berretacoin(2);
        // Agregamos 2 bloques pero sin transacciones, exceptuando la de creacion.
        bc.agregarBloque(transaccionesVacias); // bloque 1 Debe darle 1 berretacoin a alguno
        bc.agregarBloque(transaccionesVacias2); // bloque 2 el otro debe tener 1 también
        bc.hackearTx();
        
        //  
        assertEquals(0, bc.txMayorValorUltimoBloque());
        assertEquals(1, bc.maximoTenedor());
    }

@Test
    public void VariasOperaciones(){
        // 4 usuarios
        Berretacoin bc = new Berretacoin(4);
        SaldoTracker tracker = new SaldoTracker(4);
        Transaccion[] transaccion = new Transaccion[]{
            new Transaccion(0, 0 , 1 ,1),
            new Transaccion(1,1,2,1),
            new Transaccion(2,2,3,1),
            new Transaccion(3,3,4,1),
            new Transaccion(4,4,1,1)
        };
    
        bc.agregarBloque(transaccion);

        int max = bc.maximoTenedor();
        Transaccion maxTx = bc.txMayorValorUltimoBloque();
        int medio = bc.montoMedioUltimoBloque();
        assertEquals(1, max);
        assertEquals(1, medio);
        bc.hackearTx();
        int mediosinuno = bc.montoMedioUltimoBloque(); 
        assertEquals(0, max);
        assertEquals(0, tracker.getSaldo(max));
        assertFalse(tracker.puedeGastar(1,1));
        assertEquals(1, maxTx);
        assertEquals(3/4, mediosinuno);
    }

    @Test 
    public void Cambiandotxmayor(){
         Transaccion[] transacciones = new Transaccion[] {
            new Transaccion(0, 0, 1, 1), // 1 -> $2, 2 -> $1
            new Transaccion(1, 1, 2, 2), // 2 -> $3
            new Transaccion(2, 2, 3, 3), // 3 -> $3
            new Transaccion(3, 3, 1, 2), // 1 -> $2, 3 -> $1
            new Transaccion(4, 1, 2, 1), // 1 -> $1, 2 -> $1, 3 -> $1
            new Transaccion(5, 2, 3, 1)  // 1 -> $1, 3 -> $2
        };
        Berretacoin bc = new Berretacoin(3);

        bc.agregarBloque(transacciones);
        assertEquals(3, bc.txMayorValorUltimoBloque());

        bc.hackearTx();
        assertEquals(2, bc.txMayorValorUltimoBloque());

        bc.hackearTx();
        bc.hackearTx();
        bc.hackearTx();
        bc.hackearTx();
        assertEquals(0, bc.txMayorValorUltimoBloque());
        assertEquals(1, bc.maximoTenedor());
        bc.hackearTx();
        assertEquals(0, bc.maximoTenedor());

    }
    






}