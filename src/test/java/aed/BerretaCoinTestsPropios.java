package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class BerretaCoinTestsPropios {
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
        
        assertEquals(null, bc.txMayorValorUltimoBloque());
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
            new Transaccion(4,4,2,1)
        };
    
        bc.agregarBloque(transaccion);

        int max = bc.maximoTenedor();
        Transaccion maxTx = bc.txMayorValorUltimoBloque();
        int medio = bc.montoMedioUltimoBloque();
        assertEquals(2, max); //Máximo tenedor el usuario de id 1.
        assertEquals(1, medio); //Promedio de monto de transacciones 1
        bc.hackearTx(); // Eliminamos la transaccion 4.
        assertEquals(1, medio); //Desaparece la transacción 4. El monto medio sigue siendo el mismo.
        assertEquals(bc.maximoTenedor(), 4); //Cómo desapareció esa ultima transacción, ahora el maximo tenedor es 4.
        // Ya que no le mandó la berretacoin al usuario 2.
        assertEquals(4, bc.maximoTenedor()); // 4 se queda cómo máximo tenedor. 
        assertFalse(tracker.puedeGastar(2,1)); // 2 no puede gastar nada. Ya que nunca recibió nada.
        assertFalse(bc.txMayorValorUltimoBloque() == maxTx); //El maxTx que contenía la 
        // transaación mayor antes del hackeo. 
    }

    @Test 
    public void Cambiandotxmayor(){
        Transaccion[] transacciones1 = new Transaccion[] {
            new Transaccion(0, 0, 1, 1), 
            new Transaccion(1, 1, 2, 1), 
            new Transaccion(2, 2, 3, 1), 
            new Transaccion(3, 3, 1, 1), 
            new Transaccion(4, 1, 2, 1), 
            new Transaccion(5, 2, 3, 1)  
        };

        Transaccion[] transacciones2 = new Transaccion[] {
            new Transaccion(0, 0, 2, 1), 
            new Transaccion(1, 2, 1, 1), 
            new Transaccion(2, 1, 3, 1), 
            new Transaccion(3, 3, 1, 2), 
            new Transaccion(4, 1, 2, 1), 
            new Transaccion(5, 2, 3, 1)  
        };

        Transaccion[] transacciones3 = new Transaccion[] {
            new Transaccion(0, 0, 3, 1), 
            new Transaccion(1, 1, 2, 1), 
            new Transaccion(2, 2, 3, 1), 
            new Transaccion(3, 3, 1, 3), 
            new Transaccion(4, 1, 2, 1), 
            new Transaccion(5, 1, 3, 1)  
        };
        Berretacoin bc = new Berretacoin(3);

        bc.agregarBloque(transacciones1);
        bc.agregarBloque(transacciones2);
        bc.agregarBloque(transacciones3);

        assertEquals(transacciones3[3], bc.txMayorValorUltimoBloque());

        bc.hackearTx();
        assertEquals(transacciones3[5], bc.txMayorValorUltimoBloque());
        // Cómo todas las transacciones tenian 1 monto de transacción, desempta el de id mayor
        // en este caso la de id = 5

        bc.hackearTx();
        assertEquals(transacciones3[4], bc.txMayorValorUltimoBloque());
        // Va bajando el id de transacción, en este caso sería la transaccion 4
    }
    
}





}
