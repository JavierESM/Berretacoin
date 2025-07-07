package aed;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
    private Nodo raiz;
    private int tamaño;
    private ArrayList<Nodo> nodosEnNivel;

    private class Nodo {
        T dato;
        Nodo padre, hijoIzquierdo, hijoDerecho;
        HandleHeap aSiMismo;

        Nodo(T dato) {/*Complejidad O(1) */
            this.dato = dato; /*Operación básica */
        }
    }

    public class HandleHeap {
        private Nodo nodoApuntado;

        private HandleHeap(Nodo nodo) {/*Complejidad O(1) */
            this.nodoApuntado = nodo; /*Operación básica */
        }

        public T obtener() {/*Complejidad O(1) */
            return nodoApuntado.dato;/*Operación básica */
        }
    }

    public Heap() {/*Complejidad O(1) */
        this.nodosEnNivel = new ArrayList<>();/*Operación básica O(1)*/
    }

    public HandleHeap insertar(T nuevoValor) { /*Complejidad O(1) */
        Nodo nuevoNodo = new Nodo(nuevoValor); /*Operación básica O(1) */
        HandleHeap nuevoHandle = new HandleHeap(nuevoNodo); /*Operación básica O(1) */
        nuevoNodo.aSiMismo = nuevoHandle; /*Operación básica O(1) */

        if (raiz == null) { /*Operación básica O(1) */
            raiz = nuevoNodo; /*Operación básica O(1) */
        } else {
            Nodo padre = nodosEnNivel.get((nodosEnNivel.size() - 1) / 2); /*Operación básica pues se pasa como parámetro el índice del ArrayList, O(1) */
            nuevoNodo.padre = padre; /*Operación básica O(1) */
            if (padre.hijoIzquierdo == null) { /*Operación básica O(1) */
                padre.hijoIzquierdo = nuevoNodo; /*Operación básica O(1) */
            } else {
                padre.hijoDerecho = nuevoNodo; /*Operación básica O(1) */
            }
        }

        nodosEnNivel.add(nuevoNodo); /*O(1) amortizado */
        tamaño++; /*Operación básica O(1) */
        return nuevoHandle; /*Operación básica O(1) */
    }

    public void heapifyLloyd() { /*Complejidad O(p) */
        for (int i = nodosEnNivel.size() / 2 - 1; i >= 0; i--) {
            siftDown(nodosEnNivel.get(i));
            /*Para una cantidad P de usuarios, esto pertenerce al orden O(P) ya que utiliza el algoritmo de Lloyd para la inserción.
        Primero inserto el nodo con una combinación de operaciones básicas, por lo que la inserción en sí pertenece a un orden proporcional a 
        O(1). Además, los añado en el atributo nodosEnNivel (un ArrayList) para poder utilizar lo que se vió en clase de como acceder a cada nodo y sus hijos
        mediante el cálculo de los indices según su posicion en el arraylist. Itero sobre estas posiciones realizando un siftDown. Como los nodos 
        que están "más abajo" son más y recorren menos y los nodos que estan "más arriba" son menos y recorren más, la suma de esto termina 
        con un algoritmo que pertenece a O(P)*/
        }
    }

    public void editar(HandleHeap handle) { /*Complejidad O(log p) */
        siftUp(handle.nodoApuntado); /*Por lo visto en clase, los sift tienen una complejidad de O(log p)  */
        siftDown(handle.nodoApuntado);/*O(log p) */
    }

    public T mostrarMaximo() { /*Complejidad O(1) */
        if (raiz == null) { /*Operación básica, O(1) */
            return null; /*Operación básica, O(1) */
        }
        return raiz.dato; /*El Heap está construido como un maxHeap, luego, encontrar el máximo es de complejidad O(1) */ 
    }

    public HandleHeap extraerMaximo() {/*Complejidad O(log p) */
        if (raiz == null) return null; /*Operación básica, O(1) */

        Nodo nodoMaximo = raiz; /*Operación básica, O(1) */
        HandleHeap handleMaximo = nodoMaximo.aSiMismo; /*Operación básica, O(1) */

        Nodo ultimo = nodosEnNivel.get(nodosEnNivel.size() - 1); /*Operación básica, O(1) */
        intercambiar(nodoMaximo, ultimo); /*Complejidad O(1) */

        if (ultimo.padre != null) { /*Operación básica, O(1) */
            if (ultimo.padre.hijoIzquierdo == ultimo) { /*Operación básica, O(1) */
                ultimo.padre.hijoIzquierdo = null; /*Operación básica, O(1) */
            } else {
                ultimo.padre.hijoDerecho = null; /*Operación básica, O(1) */
            }
        } else {
            raiz = null; /*Operación básica, O(1) */
        }

        nodosEnNivel.remove(nodosEnNivel.size() - 1); /*Operación básica, O(1) */
        tamaño--; /*Operación básica, O(1) */

        if (raiz != null) { /*Operación básica, O(1) */
            siftDown(raiz); /*Por lo susodicho, O(log p) */
        }

        return handleMaximo; /*Operación básica, O(1) */
    }

    private void intercambiar(Nodo a, Nodo b) {/*Complejidad O(1) */
        T guardarDato = a.dato; /*Operación básica, O(1) */
        a.dato = b.dato; /*Operación básica, O(1) */
        b.dato = guardarDato; /*Operación básica, O(1) */

        HandleHeap handleA = a.aSiMismo; /*Operación básica, O(1) */
        HandleHeap handleB = b.aSiMismo; /*Operación básica, O(1) */

        a.aSiMismo = handleB; /*Operación básica, O(1) */
        b.aSiMismo = handleA; /*Operación básica, O(1) */
        if (a.aSiMismo != null) a.aSiMismo.nodoApuntado = a; /*Operación básica, O(1) */
        if (b.aSiMismo != null) b.aSiMismo.nodoApuntado = b; /*Operación básica, O(1) */
    }

    private void siftUp(Nodo nodo) { /*Complejidad O(log p) */
        while (nodo.padre != null && nodo.dato.compareTo(nodo.padre.dato) < 0) { /*El ciclo realiza las comparaciones con la lógica de compareTo que tiene el dato T que le heap
            ordena. Como va recorriendo los niveles, en el peor de los casos es O(log p) */
            intercambiar(nodo, nodo.padre);
            nodo = nodo.padre;
        }
    }

    private void siftDown(Nodo nodo) { /*Complejidad O(log p) */
        while (true) { /*Por lo dicho en siftUp, O(log p) */
            Nodo mayor = nodo; /*Operación básica, O(1) */

            if (nodo.hijoIzquierdo != null && nodo.hijoIzquierdo.dato.compareTo(mayor.dato) < 0) { /*Operación básica, O(1) */
                mayor = nodo.hijoIzquierdo; /*Operación básica, O(1) */
            }

            if (nodo.hijoDerecho != null && nodo.hijoDerecho.dato.compareTo(mayor.dato) < 0) { /*Operación básica, O(1) */
                mayor = nodo.hijoDerecho; /*Operación básica, O(1) */
            }

            if (nodo != mayor) { /*Operación básica, O(1) */
                intercambiar(nodo, mayor); /*Operación básica, O(1) */
                nodo = mayor; /*Operación básica, O(1) */
            } else {
                break; /*Operación básica, O(1) */
            }
        }
    }

    public int tamaño() { /*Complejidad O(1) */
        return this.tamaño; /*Operación básica, O(1) */
    }
}
