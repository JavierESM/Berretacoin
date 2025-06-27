package aed;

public class Array<T> {
    private T[] datos;
    private int cantidad;


    public class Handle {
        private int posicionApuntada;

        public Handle(int posicion){ /*Complejidad O(1) */
            this.posicionApuntada = posicion; /*Operación básica, O(1) */
        }
        
        public T obtener(int posicion){ /*Complejidad O(1) */
            return datos[posicion]; /*Operación básica, O(1) */
        }
    }

    public Array(int cantidadUsuarios) { /*Complejidad O(p) */
        datos = (T[]) new Object[cantidadUsuarios]; /*Creación del array de tamaño P implica una complejidad de orden O(p) */
        cantidad = 0; /*Operación básica, O(1) */
    }
    
    /*Teniendo en cuenta que el único caso en el cual se crean nuevos usuarios es cuando se crea una instancia del 
     * Berretacoin, no necesito redimensionar el array a la hora de añadir usuarios ya que el array tiene el length 
     * necesario como para guardar todos los usuarios de esta instancia. Por eso no se incluye ese caso en "agregar"
     */
    public Handle agregar(T dato){ /*Complejidad O(1) */
        datos[cantidad] =  dato; /*Operación básica, O(1) */
        Handle nuevoHandle = new Handle(cantidad); /*Operación básica, O(1) */
        cantidad++; /*Operación básica, O(1) */
        return nuevoHandle; /*Operación básica, O(1) */
    }
    
    public void editar(Handle handle, T nuevoDato){ /*Complejidad O(1) */
        datos[handle.posicionApuntada] = nuevoDato; /*Operación básica, O(1) */
    }

    public T obtener(int i) { /*Complejidad O(1) */
    if (i < 0 || i >= cantidad) { /*Operación básica, O(1) */
        throw new IndexOutOfBoundsException("id de usuario inválido"); /*Operación básica, O(1) */
        }
        return datos[i]; /*Operación básica, O(1) */
    }

    public int longitud(){ /*Complejidad O(1) */
        return cantidad; /*Operación básica, O(1) */
    }
}
