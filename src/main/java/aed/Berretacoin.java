package aed;

import java.util.ArrayList;

public class Berretacoin {
    private Array<Usuario> usuariosLista;
    private Heap<Usuario> usuariosPorMonto;
    private ArrayList<Bloque> cadena;
    private int montoUltimoBloque;
    private int cantidadTransacciones;
    public Berretacoin(int usuariosAGenerar) { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(P), P SIENDO usuariosAGenerar */

        
        int totalUsuarios = usuariosAGenerar + 1; /*Operación básica, O(1) */
        this.usuariosLista = new Array<>(totalUsuarios + 1); /*Complejidad O(p)*/
        this.usuariosPorMonto = new Heap<>(); /*Operación básica, O(1) */
        this.cadena = new ArrayList<>(); /*Operación básica, O(1) */
        Usuario sistema = new Usuario(0, 0); /*Operación básica, O(1) */
        Array<Usuario>.Handle handleSistemaArray = usuariosLista.agregar(sistema); /*Agregar en este array está en orden O(1) ya que 
        agrega utilizando como índice la cantidad de elementos que tiene el array (es decir, agrega al final) y luego suma 1 a 
        la cantidad del array*/
        sistema.setearHandleArray(handleSistemaArray); /*Operación básica, O(1) */


        for (int i = 1; i <= usuariosAGenerar; i++) {/*Complejidad O(p) */
            Usuario usuario = new Usuario(i, 0); /*Operación básica, O(1) */
            Array<Usuario>.Handle handleArray = usuariosLista.agregar(usuario); /*O(1), por lo dicho anteriormente*/
            usuario.setearHandleArray(handleArray); /*Operación básica, O(1) */
            Heap<Usuario>.HandleHeap handleHeap = usuariosPorMonto.insertar(usuario); /*Operación básica, O(1) */
            usuario.setearHandleHeapU(handleHeap); /*Operación básica, O(1) */
        }  

        usuariosPorMonto.heapifyLloyd(); /*O(P), por lo dicho en la estructura de Heap */
    }

    public void agregarBloque(Transaccion[] transacciones) { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(N * LOG P), n siendo la cantidad
        de transacciones */
    Heap<Transaccion> heapTransacciones = new Heap<>(); /*Operación básica, O(1)*/
    ListaEnlazada<Transaccion> listaTransacciones = new ListaEnlazada<>();/*Operación básica, O(1)*/

    montoUltimoBloque = 0; /*Operación básica, O(1)*/
    cantidadTransacciones = 0; /*Operación básica, O(1)*/

    for (Transaccion t : transacciones) { /*Itero sobre las transacciones recibidas, O(n)*/
        int idComprador = t.id_comprador(); /*Operación básica, O(1)*/
        int idVendedor = t.id_vendedor(); /*Operación básica, O(1)*/

        if (idComprador < 0 || idComprador >= usuariosLista.longitud()) continue; /*Operación básica, O(1)*/
        if (idVendedor < 0 || idVendedor >= usuariosLista.longitud()) continue; /*Operación básica, O(1)*/

        Usuario comprador = usuariosLista.obtener(idComprador); /*Operación básica, O(1)*/
        Usuario vendedor = usuariosLista.obtener(idVendedor); /*Operación básica, O(1)*/

        if (comprador == null || vendedor == null) continue; /*Operación básica, O(1)*/
        if (comprador.id() != 0 && comprador.monto() < t.monto()) continue; /*Operación básica, O(1)*/

      
        Heap<Transaccion>.HandleHeap handleHeap = heapTransacciones.insertar(t); /*O(1), por lo dicho anteriormente*/
        ListaEnlazada<Transaccion>.Handle handleLista = listaTransacciones.agregarAtras(t);/*O(1), porque ya se donde está la cola*/
        t.setearHandleLL(handleLista); /*Operación básica, O(1)*/
        t.setearHandleHeapT(handleHeap); /*Operación básica, O(1)*/
       

        if (comprador.id() == 0) {
            vendedor.setearMonto(vendedor.monto() + t.monto()); /*Operación básica, O(1)*/
            usuariosPorMonto.editar(vendedor.obtenerHandleHeapU()); /*Si bien editar en un heap normalmente implica buscar el nodo a editar, gracias al handle se puede rápidamente
            encontrar el nodo y ejecutar los sifts sobre el, por lo que queda en complejidad O(log p)*/
        } else {
            comprador.setearMonto(comprador.monto() - t.monto()); /*Operación básica, O(1)*/
            usuariosPorMonto.editar(comprador.obtenerHandleHeapU()); /*Si bien editar en un heap normalmente implica buscar el nodo a editar, gracias al handle se puede rápidamente
            encontrar el nodo y ejecutar los sifts sobre el, por lo que queda en complejidad O(log p)*/

            vendedor.setearMonto(vendedor.monto() + t.monto()); /*Operación básica, O(1)*/
            usuariosPorMonto.editar(vendedor.obtenerHandleHeapU()); /*Si bien editar en un heap normalmente implica buscar el nodo a editar, gracias al handle se puede rápidamente
            encontrar el nodo y ejecutar los sifts sobre el, por lo que queda en complejidad O(log p)*/

            montoUltimoBloque += t.monto(); /*Operación básica, O(1)*/
            cantidadTransacciones++; /*Operación básica, O(1)*/
        }
    }

    heapTransacciones.heapifyLloyd(); /*O(n), por lo dicho anteriormente*/
    Bloque nuevoBloque = new Bloque(heapTransacciones, listaTransacciones); /*Operación básica, O(1)*/
    this.cadena.add(nuevoBloque); /*Operación básica, O(1)*/
    /*Sumando la complejidad de Lloyd en las transacciones a la iteración sobre transacciones y que en cada una de ellas tengo que editar
     * el heap de los usuarios, tengo... 
     * O(n * log P) + O(n) = O(n * log P) por las reglas de complejidad vistas en clase
     */
}


    public Transaccion txMayorValorUltimoBloque() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
        
        Bloque ultimoBloque = cadena.get(cadena.size() - 1); /*Operación básica, O(1) */
        return ultimoBloque.transaccionesHeap.mostrarMaximo(); /*Como es un max-heap, la raiz es el máximo. O(1) */
    }

    public Transaccion[] txUltimoBloque() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(n) */

        if (cadena.isEmpty()) return new Transaccion[0]; /*Operación básica, O(1) */

        Bloque ultimoBloque = cadena.get(cadena.size() - 1); /*Operación básica, O(1) */
        ListaEnlazada<Transaccion> lista = ultimoBloque.transaccionesLista; /*Operación básica, O(1) */
        ArrayList<Transaccion> resultado = new ArrayList<>(); /*Operación básica, O(1) */
        Iterador<Transaccion> it = lista.iterador(); /*Operación básica, O(1) */
        while (it.haySiguiente()) {
            resultado.add(it.siguiente());
        } /*Itero sobre n transacciones, o(n) */

        return resultado.toArray(new Transaccion[0]); /*O(n) */
    }

    public int maximoTenedor() {/* COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1) */
        Usuario maximo = usuariosPorMonto.mostrarMaximo(); /*Por lo dicho anteriormente sobre los max-heaps, O(1) */
        if(usuariosPorMonto.tamaño() == 0){ /*Operación básica, O(1) */
            return (0); /*Operación básica, O(1) */
        } else { 
            return maximo.id(); /*Operación básica, O(1) */
        }
    }

    public int montoMedioUltimoBloque() { /*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(1)*/
        if (cadena.isEmpty()) return 0; /*Operación básica, O(1) */

        int promedio = (cantidadTransacciones == 0) ? 0 : montoUltimoBloque / cantidadTransacciones; /*Operación básica, O(1) */
        
        return promedio; /*Operación básica, O(1) */
    }

    public void hackearTx() {/*COMPLEJIDAD DEL MÉTODO PERTENECE AL ORDEN O(log n + log p) */
        Bloque ultimoBloque = cadena.get(cadena.size() - 1); /*Operación básica, O(1) */
        if (ultimoBloque.transaccionesHeap.tamaño() == 0) return; /*Operación básica, O(1) */

        Heap<Transaccion>.HandleHeap h = ultimoBloque.transaccionesHeap.extraerMaximo(); /*Extraer de un max-heap su máximo implica
        O(1) a la hora de encontrarlo y O(log n) finalmente debido a que muevo el último nodo (último en sentido de completitud del heap) a la raíz
        y luego le aplico siftDown hasta que se haya restaurado el inv del heap, luego O(log n)*/
        Transaccion t = h.obtener(); /*Operación básica, O(1) */

        Usuario comprador = usuariosLista.obtener(t.id_comprador()); /*Operación básica, O(1) */
        Usuario vendedor = usuariosLista.obtener(t.id_vendedor()); /*Operación básica, O(1) */

        if (t.id_comprador() != 0) { /*Operación básica, O(1) */
            comprador.setearMonto(comprador.monto() + t.monto()); /*Operación básica, O(1) */
            usuariosPorMonto.editar(comprador.obtenerHandleHeapU()); /*Por lo susodicho, complejidad O(log p) */
        }

        vendedor.setearMonto(vendedor.monto() - t.monto()); /*Operación básica, O(1) */
        usuariosPorMonto.editar(vendedor.obtenerHandleHeapU()); /*Por lo susodicho, complejidad O(log p) */
        
        ultimoBloque.transaccionesLista.eliminar(t.obtenerHandleLL()); /*Gracias al handle sé "dónde está" el nodo en la lista,
         proporcional a O(1)*/

        montoUltimoBloque -= t.id_comprador() == 0 ? 0 : t.monto(); /*Operación básica, O(1) */
        cantidadTransacciones -= t.id_comprador() == 0 ? 0 : 1; /*Operación básica, O(1) */
        
        

    }

}


