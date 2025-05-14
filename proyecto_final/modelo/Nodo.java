package proyecto_final.modelo;

/**
 * Clase Nodo que representa un nodo en un grafo.
 * Cada nodo tiene un identificador único que lo distingue de otros nodos.
 */
public class Nodo {
    private final String id; // Identificador único del nodo

    /**
     * Constructor de la clase Nodo.
     *
     * @param id El identificador único del nodo.
     */
    public Nodo(String id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador del nodo.
     *
     * @return El identificador del nodo.
     */
    public String getId() {
        return id;
    }

    /**
     * Verifica si este nodo es igual a otro objeto.
     * Dos nodos se consideran iguales si tienen el mismo identificador.
     *
     * @param o El objeto a comparar.
     * @return true si los nodos tienen el mismo identificador, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nodo)) return false;
        return id.equals(((Nodo) o).id);
    }

    /**
     * Calcula el código hash del nodo basado en su identificador.
     *
     * @return El código hash del nodo.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Devuelve una representación en forma de cadena del nodo.
     *
     * @return El identificador del nodo como cadena.
     */
    @Override
    public String toString() {
        return id;
    }
}