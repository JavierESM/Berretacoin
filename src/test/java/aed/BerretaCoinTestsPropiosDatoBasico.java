package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BerretaCoinTestsPropiosDatoBasico {

    /* Agregado y lectura de varios elementos en un Array */
    @Test
    public void arrayBasico() {
        Array<Integer> array = new Array<>(5);
        array.agregar(1);
        array.agregar(2);
        array.agregar(3);

        assertEquals(1, array.obtener(0));
        assertEquals(2, array.obtener(1));
        assertEquals(3, array.obtener(2));
        assertEquals(3, array.longitud());
    }

    /* Array con un único elemento funcionando correctamente */
    @Test
    public void arrayUnElemento() {
        Array<Integer> array = new Array<>(1);
        array.agregar(99);
        assertEquals(99, array.obtener(0));
    }

    /* Revisa que funcione la excepción si se accede una posición fuera de los límites del Array */
    @Test
    public void arrayOutOfBounds() {
        Array<Integer> array = new Array<>(1);
        array.agregar(10);
        assertThrows(IndexOutOfBoundsException.class, () -> array.obtener(1));
    }

    /* Revisa que se alcancen los elementos y que se hace de manera secuenciada */
    @Test
    public void listaEnlazadaBasica() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.agregarAtras(10);
        lista.agregarAtras(20);
        lista.agregarAtras(30);

        Iterador<Integer> it = lista.iterador();
        assertTrue(it.haySiguiente());
        assertEquals(10, it.siguiente());
        assertEquals(20, it.siguiente());
        assertEquals(30, it.siguiente());
        assertFalse(it.haySiguiente());
    }

    /* Comportamiento de una ListaEnlazada con un solo elemento */
    @Test
    public void listaEnlazadaUnElemento() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.agregarAtras(77);
        Iterador<Integer> it = lista.iterador();
        assertTrue(it.haySiguiente());
        assertEquals(77, it.siguiente());
        assertFalse(it.haySiguiente());
    }

    /* Revisa la eliminación de un elemento intermedio */
    @Test
    public void listaEnlazadaEliminar() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        ListaEnlazada<Integer>.Handle h1 = lista.agregarAtras(1);
        ListaEnlazada<Integer>.Handle h2 = lista.agregarAtras(2);
        ListaEnlazada<Integer>.Handle h3 = lista.agregarAtras(3);

        lista.eliminar(h2);

        Iterador<Integer> it = lista.iterador();
        assertEquals(1, it.siguiente());
        assertEquals(3, it.siguiente());
        assertFalse(it.haySiguiente());
    }

    /* Eliminar el único elemento de la lista */
    @Test
    public void listaEliminarUnicoElemento() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        ListaEnlazada<Integer>.Handle h = lista.agregarAtras(5);
        lista.eliminar(h);
        Iterador<Integer> it = lista.iterador();
        assertFalse(it.haySiguiente());
    }

    /* Lanza excepción si se llama siguiente() sin haySiguiente */
    @Test
    public void listaIteradorSiguienteInvalido() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        Iterador<Integer> it = lista.iterador();
        assertThrows(IllegalStateException.class, () -> it.siguiente());
    }

    /* Comportamiento de un Heap con un solo elemento */
    @Test
    public void heapUnElemento() {
        Heap<Integer> heap = new Heap<>();
        heap.insertarDesdeLista(List.of(99));

        assertEquals(99, heap.mostrarMaximo());

        Heap<Integer>.HandleHeap h = heap.extraerMaximo();
        assertEquals(99, h.obtener());
        assertEquals(0, heap.tamaño());
    }

    /* Reviso que el máximo sea el correcto entre elementos repetidos */
    @Test
    public void heapConRepetidos() {
        Heap<Integer> heap = new Heap<>();
        heap.insertarDesdeLista(List.of(5, 5, 5));

        assertEquals(5, heap.mostrarMaximo());
    }
}
