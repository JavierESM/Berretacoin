package aed;

import java.util.ArrayList;

public class Berretacoin {
    private Array<Usuario> usuariosLista;
    private Heap<Usuario> usuariosPorMonto;
    private ArrayList<Bloque> cadena;
    private int montoMedioUltimoBloque;
    private int montoUltimoBloque;
    private int cantidadTransacciones;
    public Berretacoin(int usuariosAOperar) {

        /*Inicializo los atributos relevantes en el sistema, esto es de complejidad O(1) */
        int totalUsuarios = usuariosAOperar + 1;
        this.usuariosLista = new Array<>(totalUsuarios + 1);
        this.usuariosPorMonto = new Heap<>();
        this.cadena = new ArrayList<>();
        this.montoMedioUltimoBloque = 0;
        /*Creo e inserto el sistema en la lista enlazada, no en el heap para no tener problemas en el maximoTenedor.
         * Esto es de complejidad O(P * log p)???????
         */
        Usuario sistema = new Usuario(0, 0);
        Array<Usuario>.Handle handleSistemaArray = usuariosLista.agregar(sistema);
        sistema.setearHandleArray(handleSistemaArray);


        for (int i = 1; i <= usuariosAOperar; i++) {
            Usuario usuario = new Usuario(i, 0);
            Array<Usuario>.Handle handleArray = usuariosLista.agregar(usuario);
            usuario.setearHandleArray(handleArray);
            Heap<Usuario>.HandleHeap handleHeap = usuariosPorMonto.insertar(usuario);
            usuario.setearHandleHeapU(handleHeap);
        }

        usuariosPorMonto.heapifyLloyd();
    }

    public void agregarBloque(Transaccion[] transacciones) {
    Heap<Transaccion> heapTransacciones = new Heap<>();
    ListaEnlazada<Transaccion> listaTransacciones = new ListaEnlazada<>();

    montoUltimoBloque = 0;
    cantidadTransacciones = 0;

    for (Transaccion t : transacciones) {
        int idComprador = t.id_comprador();
        int idVendedor = t.id_vendedor();

        if (idComprador < 0 || idComprador >= usuariosLista.longitud()) continue;
        if (idVendedor < 0 || idVendedor >= usuariosLista.longitud()) continue;

        Usuario comprador = usuariosLista.obtener(idComprador);
        Usuario vendedor = usuariosLista.obtener(idVendedor);

        if (comprador == null || vendedor == null) continue;
        if (comprador.id() != 0 && comprador.monto() < t.monto()) continue;

      
        Heap<Transaccion>.HandleHeap handleHeap = heapTransacciones.insertar(t);
        ListaEnlazada<Transaccion>.Handle handleLista = listaTransacciones.agregarAtras(t);
        t.setearHandleLL(handleLista);
        t.setearHandleHeapT(handleHeap);
       

        if (comprador.id() == 0) {
            vendedor.setearMonto(vendedor.monto() + t.monto());
            usuariosPorMonto.editar(vendedor.obtenerHandleHeapU());
        } else {
            comprador.setearMonto(comprador.monto() - t.monto());
            usuariosPorMonto.editar(comprador.obtenerHandleHeapU());

            vendedor.setearMonto(vendedor.monto() + t.monto());
            usuariosPorMonto.editar(vendedor.obtenerHandleHeapU());

            montoUltimoBloque += t.monto();
            cantidadTransacciones++;
        }
    }

    heapTransacciones.heapifyLloyd(); 
    Bloque nuevoBloque = new Bloque(heapTransacciones, listaTransacciones);
    this.cadena.add(nuevoBloque);
}


    public Transaccion txMayorValorUltimoBloque() {
        /*Busco mandandole un indice, es complejidad O(1) */
        Bloque ultimoBloque = cadena.get(cadena.size() - 1);
        return ultimoBloque.transaccionesHeap.mostrarMaximo();
    }

    public Transaccion[] txUltimoBloque() {

        if (cadena.isEmpty()) return new Transaccion[0];
        /*Asignaciones, O(1) */
        Bloque ultimoBloque = cadena.get(cadena.size() - 1);
        ListaEnlazada<Transaccion> lista = ultimoBloque.transaccionesLista;
        /*Itero sobre las  transacciones con el iterador y devuelvo la lista, complejidad O(n_b)*/
        ArrayList<Transaccion> resultado = new ArrayList<>();
        Iterador<Transaccion> it = lista.iterador();
        while (it.haySiguiente()) {
            resultado.add(it.siguiente());
        }

        return resultado.toArray(new Transaccion[0]);
    }

    public int maximoTenedor() {
        /*Como tengo un max-heap, le estoy pidiendo que me devuelva la raiz lo que me resulta en 
         * complejidad O(1)
         */
        Usuario maximo = usuariosPorMonto.mostrarMaximo();
        if(usuariosPorMonto.tamaño() == 0){
            return (0);
        } else {
            return maximo.id();
        }
    }

    public int montoMedioUltimoBloque() {
        /*Creo que esto tambien tiene mal la complejidad */
        if (cadena.isEmpty()) return 0;

        int promedio = (cantidadTransacciones == 0) ? 0 : montoUltimoBloque / cantidadTransacciones;
        this.montoMedioUltimoBloque = promedio;
        return promedio;
    }

    public void hackearTx() {
    Bloque ultimoBloque = cadena.get(cadena.size() - 1);
    if (ultimoBloque.transaccionesHeap.tamaño() == 0) return;

    Heap<Transaccion>.HandleHeap h = ultimoBloque.transaccionesHeap.extraerMaximo();
    Transaccion tx = h.obtener();

    Usuario comprador = usuariosLista.obtener(tx.id_comprador());
    Usuario vendedor = usuariosLista.obtener(tx.id_vendedor());

    if (tx.id_comprador() != 0) {
        comprador.setearMonto(comprador.monto() + tx.monto());
        usuariosPorMonto.editar(comprador.obtenerHandleHeapU());
    }

    vendedor.setearMonto(vendedor.monto() - tx.monto());
    usuariosPorMonto.editar(vendedor.obtenerHandleHeapU());
    
    ultimoBloque.transaccionesLista.eliminar(tx.obtenerHandleLL());

    montoUltimoBloque -= tx.id_comprador() == 0 ? 0 : tx.monto();
    cantidadTransacciones -= tx.id_comprador() == 0 ? 0 : 1;
    int promedio = (cantidadTransacciones == 0) ? 0 : montoUltimoBloque / cantidadTransacciones;

    this.montoMedioUltimoBloque = promedio;

}

}


