package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class Berretacoin {
    
    private AVL<Usuario> usuariosPorId;
    private Heap<Usuario> usuariosPorMonto;
    
    private ArrayList<Heap> bloques;

     public Berretacoin(int usuariosAGenerar) {
        this.usuariosPorId = new AVL<>();
        this.usuariosPorMonto = new Heap<>(Comparator.comparingInt(Usuario::monto).reversed().thenComparingInt(Usuario::id));
        this.bloques = new ArrayList<>();

        for (int i = 0; i < usuariosAGenerar; i++) {
            Usuario usuario = new Usuario(i, 0);
            usuariosPorId.insertar(usuario);
            usuariosPorMonto.insertar(usuario);
        }
    }

    public void agregarBloque(Transaccion[] transacciones){
        Heap<Transaccion> heapTransacciones = new Heap<>(Comparator.comparingInt(Transaccion::monto).reversed());
        
        
        for (Transaccion t : transacciones) {
            Usuario comprador = usuariosPorId.buscar(new Usuario(t.id_comprador(), 0));
            Usuario vendedor = usuariosPorId.buscar(new Usuario(t.id_comprador(), 0));
        
            if (comprador == null || vendedor == null) continue;

            if (comprador.monto() < t.monto()) continue;

            if (comprador.id() == 0) {
                vendedor.setearMonto(vendedor.monto() + t.monto());
            } else {
                comprador.setearMonto(comprador.monto() - t.monto());
                vendedor.setearMonto(vendedor.monto() + t.monto()); 
            }
            
            usuariosPorMonto.actualizar(comprador);
            usuariosPorMonto.actualizar(vendedor);
            
            heapTransacciones.insertar(t);
        }

        this.bloques.add(heapTransacciones);
        
    }

    public Transaccion txMayorValorUltimoBloque(){
        Heap<Transaccion> ultimoBloque = bloques.get(bloques.size() - 1);
        Transaccion txMayorValor = ultimoBloque.mostrarMaximo();
        return txMayorValor;
    }

    public Transaccion[] txUltimoBloque(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public int maximoTenedor(){
        Usuario maximoTenedor = usuariosPorMonto.mostrarMaximo();
        return maximoTenedor.id();
    }

    public int montoMedioUltimoBloque(){
        Heap<Transaccion> ultimoBloque = bloques.get(bloques.size() - 1);
        int cantidadTransacciones = ultimoBloque.numeroNodos();
        int sumaTransacciones = 0;
        for (int i = 0; i < cantidadTransacciones; i++){
            sumaTransacciones = sumaTransacciones + ultimoBloque.devolverPorIndice(i).monto();
        }
        return sumaTransacciones;
    }

    public void hackearTx(){
        throw new UnsupportedOperationException("Implementar!");
    }
}
