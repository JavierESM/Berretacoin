package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


public class BerretacoinTests {
    private Berretacoin berretacoin;
    private Transaccion[] transacciones;
    private Transaccion[] transacciones2;
    private Transaccion[] transacciones3;

    // Helper class para trackear saldos de usuarios
    private class SaldoTracker {
        private Map<Integer, Integer> saldos;
        
        public SaldoTracker(int usuarios) {
            saldos = new HashMap<>();
            for (int i = 1; i <= usuarios; i++) {
                saldos.put(i, 0);
            }
        }
        
        public void aplicarTransaccion(Transaccion tx) {
            if (tx.id_comprador() == 0) {
                saldos.put(tx.id_vendedor(), saldos.get(tx.id_vendedor()) + tx.monto());
            } else {
                saldos.put(tx.id_comprador(), saldos.get(tx.id_comprador()) - tx.monto());
                saldos.put(tx.id_vendedor(), saldos.get(tx.id_vendedor()) + tx.monto());
            }
        }
        
        public int getSaldo(int usuario) {
            return saldos.get(usuario);
        }
        
        public boolean puedeGastar(int usuario, int monto) {
            return usuario == 0 || getSaldo(usuario) >= monto;
        }
        
        public int getMaximoTenedor() {
            int maxSaldo = -1;
            int maxUsuario = Integer.MAX_VALUE;
            
            for (Map.Entry<Integer, Integer> entry : saldos.entrySet()) {
                int usuario = entry.getKey();
                int saldo = entry.getValue();
                
                if (saldo > maxSaldo || (saldo == maxSaldo && usuario < maxUsuario)) {
                    maxSaldo = saldo;
                    maxUsuario = usuario;
                }
            }
            return maxUsuario;
        }
        
        public void revertirTransaccion(Transaccion tx) {
            if (tx.id_comprador() == 0) {
                saldos.put(tx.id_vendedor(), saldos.get(tx.id_vendedor()) - tx.monto());
            } else {
                saldos.put(tx.id_comprador(), saldos.get(tx.id_comprador()) + tx.monto());
                saldos.put(tx.id_vendedor(), saldos.get(tx.id_vendedor()) - tx.monto());
            }
        }
    }

    @BeforeEach
    void setUp() {
        berretacoin = new Berretacoin(10);

        transacciones = new Transaccion[] {
            new Transaccion(0, 0, 2, 1), // 2 -> $1
            new Transaccion(1, 2, 3, 1), // 3 -> $1
            new Transaccion(2, 3, 4, 1) // 4 -> $1
        };

        transacciones2 = new Transaccion[] {
            new Transaccion(0, 0, 4, 1), // 4 -> $2
            new Transaccion(1, 4, 1, 2), // 1 -> $2
            new Transaccion(2, 1, 2, 1)  // 1 -> $1 , 2 -> $1
        };

        transacciones3 = new Transaccion[] {
            new Transaccion(0, 0, 1, 1), // 1 -> $2, 2 -> $1
            new Transaccion(1, 1, 2, 2), // 2 -> $3
            new Transaccion(2, 2, 3, 3), // 3 -> $3
            new Transaccion(3, 3, 1, 2), // 1 -> $2, 3 -> $1
            new Transaccion(4, 1, 2, 1), // 1 -> $1, 2 -> $1, 3 -> $1
            new Transaccion(5, 2, 3, 1)  // 1 -> $1, 3 -> $2
        };
    }
     @Test
    public void testAgregarBloqueVacioYHackearVariasVeces() {
        // 
        Berretacoin bc = new Berretacoin(5);
        
        // Agregamos 3 bloques sin transacciones
        bc.agregarBloque(Collections.emptyList()); // bloque 1
        bc.agregarBloque(Collections.emptyList()); // bloque 2
        bc.agregarBloque(Collections.emptyList()); // bloque 3

        // Hackeamos varias veces el último bloque
        bc.hackearTx();
        bc.hackearTx();

        List<Transaccion> ultimoBloque = bc.txUltimoBloque();

        // Primero, el bloque no estaba vacío, así que el hackear varias veces 
        // no debería duplicar o dejar más de 1 transacción.
        assertEquals(1, ultimoBloque.size(), "El bloque hackeado tiene que tener 1 transacción.");
    }
 
    @Test
    public void testAgregarTransaccionesYLuegoBloqueVacioYHackear() {
        // Primero bloque con transacciones
        Berretacoin bc = new Berretacoin(4);
        List<Transaccion> txs = List.of(
                new Transaccion(1, 2, 50),
                new Transaccion(2, 3, 100),
                new Transaccion(3, 4, 200)
        );
        bc.agregarBloque(txs);
        
        // Después bloque vacío
        bc.agregarBloque(Collections.emptyList());

        // Hackear bloque vacío
        bc.hackearTx();

        List<Transaccion> ultimo = bc.txUltimoBloque();

        assertEquals(1, ultimo.size(), "Tras hackear, el bloque vacío tiene que tener 1 transacción.");
        assertEquals(0, ultimo.get(0).getValor(), "Valor de la transacción hackeada tiene que ser 0.");
    }
 
    @Test
    public void testAgregarVariosBloquesYSoloHackearElUltimoVacio() {
        // 5 usuarios
        Berretacoin bc = new Berretacoin(5);
        
        // Bloque 1
        bc.agregarBloque(List.of(
                new Transaccion(1, 2, 100),
                new Transaccion(2, 3, 200)
       ));

        // Bloque 2 
        bc.agregarBloque(Collections.emptyList());

        bc.hackearTx();

        List<Transaccion> ultimo = bc.txUltimoBloque();

        assertEquals(1, ultimo.size(), "Bloque vacío tiene que tener 1 transacción luego de hackear.");
        assertEquals(0, ultimo.get(0).getValor(), "Valor de transacción hackeada tiene que ser 0.");
    }
 
    @Test
    public void testAgregarVariosBloquesVaciosSeguidosYHackear() {
        // 2 usuarios
        Berretacoin bc = new Berretacoin(2);
        
        bc.agregarBloque(Collections.emptyList()); // bloque 1
        bc.agregarBloque(Collections.emptyList()); // bloque 2
        bc.agregarBloque(Collections.emptyList()); // bloque 3

        bc.hackearTx();

        List<Transaccion> ultimo = bc.txUltimoBloque();

        assertEquals(1, ultimo.size(), "Bloque 3 tiene que tener 1 transacción.");
        assertEquals(0, ultimo.get(0).getValor(), "Valor tiene que ser 0.");
    }
 
    @Test
    public void testAgregarTransaccionAgregarVacioYHackearDosVecesSeguidas() {
        // 3 usuarios
        Berretacoin bc = new Berretacoin(3);
        
        bc.agregarBloque(List.of(new Transaccion(1, 2, 500)));
        bc.agregarBloque(Collections.emptyList());

        bc.hackearTx();
        bc.hackearTx();

        List<Transaccion> ultimo = bc.txUltimoBloque();

        assertEquals(1, ultimo.size(), "Debería tener solo 1 transacción.");
        assertEquals(0, ultimo.get(0).getValor(), "Valor tiene que quedar en 0.");
    }
     @Test
    public void testAgregarBloqueYVerificarTxMayorValor() {
        // Creamos el Berretacoin con 3 usuarios
        Berretacoin bc = new Berretacoin(3);
        
        List<Transaccion> txs = List.of(
                new Transaccion(1, 2, 50),
                new Transaccion(2, 3, 500),
                new Transaccion(3, 1, 250)
        );
        bc.agregarBloque(txs);
        
        Transaccion max = bc.txMayorValorUltimoBloque();

        assertEquals(500, max.getValor(), "Deberia encontrar la transaccion de 500 como la de mayor valor.");
    }
 
    @Test
    public void testAgregarBloqueYCalcularMontoMedio() {
        // Creamos el Berretacoin con 4 usuarios
        Berretacoin bc = new Berretacoin(4);
        
        List<Transaccion> txs = List.of(
                new Transaccion(1, 2, 100),
                new Transaccion(2, 3, 200),
                new Transaccion(3, 4, 300),
                new Transaccion(4, 1, 400)
        );
        bc.agregarBloque(txs);
        
        int promedio = bc.montoMedioUltimoBloque();

        assertEquals(250, promedio, "Valor medio de 100, 200, 300, 400 es 250.");
    }
 
    @Test
    public void testAgregarBloquesYVerificarMaximoTenedor() {
        // 5 usuarios
        Berretacoin bc = new Berretacoin(5);
        
        bc.agregarBloque(List.of(
                new Transaccion(1, 5, 500),
                new Transaccion(2, 5, 500),
                new Transaccion(3, 5, 500),
                new Transaccion(4, 5, 500)
        ));

        int max = bc.maximoTenedor();

        assertEquals(5, max, "Usuario 5 tiene más Berretacoin que el resto.");
    }
 
    @Test
    public void testAgregarBloqueVacioYHackearPeroSeguirConsultandoOtrosMetodos() {
        // 2 usuarios
        Berretacoin bc = new Berretacoin(2);
        
        bc.agregarBloque(Collections.emptyList()); // bloque vacío
        bc.hackearTx();

        // Aun así podemos preguntar maximoTenedor o monto medio
        int max = bc.maximoTenedor();
        int medio = bc.montoMedioUltimoBloque();

        assertEquals(1, bc.txUltimoBloque().size());
        assertEquals(0, bc.txUltimoBloque().get(0).getValor());
        assertTrue(max >= 1 && max <= 2);
        assertEquals(0, medio);
    }
 
    @Test
    public void testAgregarVariasOperacionesCombinadas() {
        // 4 usuarios
        Berretacoin bc = new Berretacoin(4);
        
        bc.agregarBloque(List.of(
                new Transaccion(1, 2, 100),
                new Transaccion(2, 3, 200),
                new Transaccion(3, 4, 500)
        ));

        int max = bc.maximoTenedor();
        Transaccion maxTx = bc.txMayorValorUltimoBloque();
        int medio = bc.montoMedioUltimoBloque();

        bc.hackearTx();

        List<Transaccion> ultimo = bc.txUltimoBloque();

        assertEquals(500, maxTx.getValor(), "Valor más alto antes de hackear.");
        assertEquals(266, medio, "Valor medio (100 + 200 + 500) / 3 ≅ 266.");

        assertEquals(1, ultimo.size(), "Tras hackear tiene que quedar 1.");
        assertEquals(0, ultimo.get(0).getValor(), "Valor tiene que quedar en 0.");
        assertTrue(max >= 1 && max <= 4, "Máximo tenedor tiene que estar en el rango de usuarios.");
    }
}
    
    
    

