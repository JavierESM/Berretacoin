package aed;

import java.util.ArrayList;

public class Berretacoin {
    private Array<Usuario> usuariosLista;
    private Heap<Usuario> usuariosPorMonto;
    private ArrayList<Bloque> cadena;
    private int montoMedioUltimoBloque;
    
    public Berretacoin(int usuariosAOperar) {
    int totalUsuarios = usuariosAOperar + 1; // +1 por el sistema
    this.usuariosLista = new Array<>(totalUsuarios + 1);
    this.usuariosPorMonto = new Heap<>();
    this.cadena = new ArrayList<>();
    this.montoMedioUltimoBloque = 0;

    Usuario sistema = new Usuario(0, 0);
    Array<Usuario>.Handle handleSistemaArray = usuariosLista.agregar(sistema);
    sistema.setearHandleArray(handleSistemaArray);
    Heap<Usuario>.HandleHeap handleSistemaHeap = usuariosPorMonto.insertar(sistema);
    sistema.setearHandleHeapU(handleSistemaHeap);

    for (int i = 1; i <= usuariosAOperar; i++) {
        System.out.println("Creando usuario " + i);
        Usuario usuario = new Usuario(i, 0);
        Array<Usuario>.Handle handleArray = usuariosLista.agregar(usuario);
        usuario.setearHandleArray(handleArray);

        Heap<Usuario>.HandleHeap handleHeap = usuariosPorMonto.insertar(usuario);
        usuario.setearHandleHeapU(handleHeap);
    }
}

    public void agregarBloque(Transaccion[] transacciones) {
    Heap<Transaccion> heapTransacciones = new Heap<>();
    ListaEnlazada<Transaccion> listaTransacciones = new ListaEnlazada<>();

    for (Transaccion t : transacciones) {
        int idComprador = t.id_comprador();
        int idVendedor = t.id_vendedor();
        

        if (idComprador < 0 || idComprador >= usuariosLista.longitud()) continue;
        if (idVendedor < 0 || idVendedor >= usuariosLista.longitud()) continue;

        Usuario comprador = usuariosLista.obtener(idComprador);
        Usuario vendedor = usuariosLista.obtener(idVendedor);

        if (comprador == null || vendedor == null) continue;
        if (comprador.id() != 0 && comprador.monto() < t.monto()) continue;

        System.out.println("Antes de la transacción:");
        System.out.println("Comprador: " + comprador);
        System.out.println("Vendedor: " + vendedor);

        if (comprador.id() == 0) {
          
            vendedor.setearMonto(vendedor.monto() + t.monto());
            System.out.println("Actualizando vendedor " + vendedor.id() + " con nuevo monto " + vendedor.monto());
            usuariosPorMonto.editar(vendedor.obtenerHandleHeapU());
        } else {
        
            comprador.setearMonto(comprador.monto() - t.monto());
            System.out.println("Actualizando comprador " + comprador.id() + " con nuevo monto " + comprador.monto());
            usuariosPorMonto.editar(comprador.obtenerHandleHeapU());
            
          
            vendedor.setearMonto(vendedor.monto() + t.monto());
            System.out.println("Actualizando vendedor " + vendedor.id() + " con nuevo monto " + vendedor.monto());
            usuariosPorMonto.editar(vendedor.obtenerHandleHeapU());
        }

        System.out.println("Después de la transacción:");
        System.out.println("Comprador: " + comprador);
        System.out.println("Vendedor: " + vendedor);
        Heap<Transaccion>.HandleHeap handleHeap = heapTransacciones.insertar(t);
        ListaEnlazada<Transaccion>.Handle handleLista = listaTransacciones.agregarAtras(t);
        t.setearHandleHeapT(handleHeap);
        t.setearHandleLL(handleLista);
    }

    Bloque nuevoBloque = new Bloque(heapTransacciones, listaTransacciones);
    System.out.println("Transacciones agregadas al bloque:");
Iterador<Transaccion> debugIt = listaTransacciones.iterador();
while (debugIt.haySiguiente()) {
    Transaccion tx = debugIt.siguiente();
    System.out.println("Tx: comprador=" + tx.id_comprador() + ", vendedor=" + tx.id_vendedor() + ", monto=" + tx.monto() + ", id de transaccion= " + tx.id());
}
    this.cadena.add(nuevoBloque);
    
}
    public Transaccion txMayorValorUltimoBloque(){
        Bloque ultimoBloque = cadena.get(cadena.size() - 1);
        Transaccion txMayorValor = ultimoBloque.transaccionesHeap.mostrarMaximo();
        return txMayorValor;
    }

    public Transaccion[] txUltimoBloque() {
        if (cadena.isEmpty()) {
            return new Transaccion[0];
        }
        Bloque ultimoBloque = cadena.get(cadena.size() - 1);
        int n = ultimoBloque.transaccionesLista.longitud();
        Transaccion[] transacciones = new Transaccion[n];

        Iterador<Transaccion> it = ultimoBloque.transaccionesLista.iterador();
        int i = 0;
        while (it.haySiguiente()) {
            transacciones[i++] = it.siguiente();
        }

        return transacciones;
    }

    public int maximoTenedor(){
        Usuario maximoTenedor = usuariosPorMonto.mostrarMaximo();
        System.out.println("Máximo del heap: " + maximoTenedor);

        /*Usuario u2999 = usuariosLista.obtener(2999);
        Usuario u3000 = usuariosLista.obtener(3000);

        System.out.println("Usuario 2999: id=" + u2999.id() + ", monto=" + u2999.monto());
        System.out.println("Usuario 3000: id=" + u3000.id() + ", monto=" + u3000.monto());
        System.out.println("Comparación: 3000.compareTo(2999) = " + u3000.compareTo(u2999));
        */

        return maximoTenedor.id();
    }

    public int montoMedioUltimoBloque() {
        if (cadena.isEmpty()) return 0;
        Bloque ultimoBloque = cadena.get(cadena.size() - 1);
        int sumaTransacciones = 0;
        int cantidadTransacciones = 0;
        Iterador<Transaccion> it = ultimoBloque.transaccionesLista.iterador();
        
        while (it.haySiguiente()) {
            Transaccion tx = it.siguiente();
            if (tx.id_comprador() != 0) {
                sumaTransacciones += tx.monto();
                cantidadTransacciones++;
            }
        }

        int promedio = (cantidadTransacciones == 0) ? 0 : sumaTransacciones / cantidadTransacciones;
        this.montoMedioUltimoBloque = promedio;
        return promedio;
    }


    public void hackearTx(){
    Bloque ultimoBloque = cadena.get(cadena.size() - 1);

    if (ultimoBloque.transaccionesHeap.tamaño() == 0) {
        return;
    }

    Heap<Transaccion>.HandleHeap h = ultimoBloque.transaccionesHeap.extraerMaximo();
    Transaccion tx = h.obtener();
    ultimoBloque.transaccionesLista.eliminar(tx.obtenerHandleLL());
    
    this.montoMedioUltimoBloque = montoMedioUltimoBloque();

    if (ultimoBloque.transaccionesHeap.tamaño() == 0) {
        cadena.removeLast();
    }
     
    // ——— Aquí agregamos el print de todas las transacciones que quedan ———
    System.out.print("Transacciones restantes en el último bloque:");
    Iterador<Transaccion> it = ultimoBloque.transaccionesLista.iterador();
    while (it.haySiguiente()) {
        Transaccion t = it.siguiente();
        System.out.print(String.format(
            " [id=%d, compr=%d, vend=%d, m=%d]",
            t.id(), t.id_comprador(), t.id_vendedor(), t.monto()
        ));
    }
    System.out.println();
}

}


