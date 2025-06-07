package aed;

public class AVL<T extends Comparable<T>>{


    private class Nodo{
    T valor;
    int altura;
    Nodo izquierdo, derecho;

    Nodo (T valor) {
        this.valor = valor;
        izquierdo = null;
        derecho = null;
        altura = 1;
    }
}

private Nodo raiz;

private int altura(Nodo nodo) {
    if (nodo == null) {
        return 0;
    } else {
        return nodo.altura;
    }
}

int factorDeBalanceo (Nodo nodo) {
    if (nodo == null) {
        return 0;
    } else {
        return altura(nodo.derecho) - altura(nodo.izquierdo);
    }
}

private int maximo (int a, int b) {
    return (a > b) ? a : b;
}

/*Voy a simplificar y establecer rotaciones que funcionen para LR Y RL cuidando las ramas que no rotan*/


private Nodo rotIzq (Nodo nodo) {

    Nodo hijoDerecho = nodo.derecho;
    Nodo ramaInterna = hijoDerecho.izquierdo;
    
    hijoDerecho.izquierdo = nodo;
    nodo.derecho = ramaInterna;
    
    nodo.altura = maximo(altura(nodo.izquierdo), altura(nodo.derecho)) + 1;
    hijoDerecho.altura = maximo(altura(hijoDerecho.izquierdo), altura(hijoDerecho.derecho)) + 1; 



    return hijoDerecho;

}

private Nodo rotDer (Nodo nodo) {

    Nodo hijoIzquierdo = nodo.izquierdo; 
    Nodo ramaExterna = hijoIzquierdo.derecho;
    
    hijoIzquierdo.derecho = nodo;
    nodo.izquierdo = ramaExterna;

    nodo.altura = maximo(altura(nodo.izquierdo), altura(nodo.derecho)) + 1;
    hijoIzquierdo.altura = maximo(altura(hijoIzquierdo.derecho), altura(hijoIzquierdo.izquierdo)) + 1; 

    
    return hijoIzquierdo;
    
}


private Nodo insertar (Nodo raiz, T valor) {
    
    if(raiz == null) 
        return new Nodo(valor);
    

    if(valor.compareTo(raiz.valor) < 0) 
         raiz.izquierdo = insertar(raiz.izquierdo, valor);
    else if (valor.compareTo(raiz.valor) > 0) 
        raiz.derecho = insertar(raiz.derecho, valor);
    else return raiz;

    raiz.altura = maximo(altura(raiz.derecho), altura(raiz.izquierdo)) + 1;
    int factorDeBalanceo = factorDeBalanceo(raiz);
    
    if(factorDeBalanceo < -1 && valor.compareTo(raiz.izquierdo.valor) < 0) return rotDer(raiz);
    if(factorDeBalanceo >  1 && valor.compareTo(raiz.derecho.valor) > 0) return rotIzq(raiz);
    
    /*Implemento LR */
    if (factorDeBalanceo < -1 && valor.compareTo(raiz.izquierdo.valor) > 0) {
        raiz.izquierdo = rotIzq(raiz.izquierdo);
        return rotDer(raiz);
    }
    /*Implemento RL */

    if (factorDeBalanceo > 1 && valor.compareTo(raiz.izquierdo.valor) < 0) {
        raiz.derecho = rotDer(raiz.derecho);
        return rotIzq(raiz);
    }
    
    return (raiz); 
    
}
    

/*Uso sobrecarga para facilitar la utilización del método, como lo utilizo por fuera de la clase también
 * lo declaro público.
*/
public void insertar (T valor) {
     raiz = insertar(raiz, valor);
}

public Nodo encontrarSucesor (Nodo nodo) {
    while (nodo.izquierdo != null) {
        nodo = nodo.izquierdo;
    }

    return nodo;
}


private Nodo eliminar (Nodo raiz, T valor) {
    if (raiz == null) 
        return null;
    if(valor.compareTo(raiz.valor) < 0) 
         raiz.izquierdo = eliminar(raiz.izquierdo, valor);
    else if (valor.compareTo(raiz.valor) > 0) 
        raiz.derecho = eliminar(raiz.derecho, valor);
    else {
        if (raiz.izquierdo == null || raiz.derecho == null) {
            Nodo hijo = (raiz.izquierdo != null) ? raiz.izquierdo : raiz.derecho;
            if (hijo == null) {
                raiz = null;
            }
            else 
                raiz = hijo;
        } else {
            Nodo sucesor = encontrarSucesor(raiz.derecho);
            raiz.valor = sucesor.valor;
            raiz.derecho = eliminar(raiz.derecho, sucesor.valor);
        } 
    }
    /*Esto es para cortar la ejecución en caso de que el arbol haya quedado vacío*/ 
    if (raiz == null) 
        return null;

    /*Implemento las mismas rotaciones que antes por si al eliminar se desbalanceó el AVL*/
    raiz.altura = maximo(altura(raiz.derecho), altura(raiz.izquierdo)) + 1;
    int factorDeBalanceo = factorDeBalanceo(raiz);
    
    if(factorDeBalanceo < -1 && valor.compareTo(raiz.izquierdo.valor) < 0) return rotDer(raiz);
    if(factorDeBalanceo >  1 && valor.compareTo(raiz.derecho.valor) > 0) return rotIzq(raiz);


    if (factorDeBalanceo < -1 && valor.compareTo(raiz.izquierdo.valor) > 0) {
        raiz.izquierdo = rotIzq(raiz.izquierdo);
        return rotDer(raiz);
    }


    if (factorDeBalanceo > 1 && valor.compareTo(raiz.izquierdo.valor) < 0) {
        raiz.derecho = rotDer(raiz.derecho);
        return rotIzq(raiz);
    }
    
    return (raiz);
}

public void eliminar(T valor) {
    raiz = eliminar(raiz, valor);
}

private T buscar(Nodo raiz, T valor){
    if (raiz ==null)
        return null;
    
    int comparacion = valor.compareTo(raiz.valor);

    if (comparacion == 0)
        return raiz.valor;
    else if (comparacion > 0)
        return buscar(raiz.derecho, valor);
    else 
        return buscar(raiz.izquierdo, valor);
    
}

public T buscar(T valor) {
    return raiz.valor = buscar(raiz, valor);
}

}

