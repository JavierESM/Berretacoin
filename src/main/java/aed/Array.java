package aed;

public class Array<T> {
    private T[] datos;
    private int cantidad;


    public class Handle {
        private int posicionApuntada;

        public Handle(int posicion){
            this.posicionApuntada = posicion;
        }
        
        public T obtener(int posicion){
            return datos[posicion];
        }
    }

    public Array(int cantidadUsuarios) {
        datos = (T[]) new Object[cantidadUsuarios];
        cantidad = 0;
    }
    
    /*Teniendo en cuenta que el único caso en el cual se crean nuevos usuarios es cuando se crea una instancia del 
     * Berretacoin, no necesito redimensionar el array a la hora de añadir usuarios ya que el array tiene el length 
     * necesario como para guardar todos los usuarios de esta instancia. Por eso no se incluye ese caso en "agregar"
     */
    public Handle agregar(T dato){
        datos[cantidad] =  dato;
        Handle nuevoHandle = new Handle(cantidad);
        cantidad++;
        return nuevoHandle;
    }
    
    public void editar(Handle handle, T nuevoDato){
        datos[handle.posicionApuntada] = nuevoDato;
    }

    public T obtener(int i) {
    if (i < 0 || i >= cantidad) {
        throw new IndexOutOfBoundsException("id de usuario inválido");
        }
        return datos[i];
    }

    public int longitud(){
        return cantidad;
    }
}
