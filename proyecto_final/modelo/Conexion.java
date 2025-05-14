package proyecto_final.modelo;

/**
 * Clase Conexion que representa una conexión desde un nodo hacia otro nodo en un grafo.
 * Cada conexión tiene un nodo destino y un peso asociado, que puede representar
 * una métrica como distancia, tiempo o costo.
 */
public class Conexion {
    private Nodo destino; // Nodo destino de la conexión
    private int peso;     // Peso de la conexión (por ejemplo, distancia o tiempo)

    /**
     * Constructor de la clase Conexion.
     *
     * @param destino El nodo destino de la conexión.
     * @param peso El peso de la conexión (por ejemplo, distancia o tiempo).
     */
    public Conexion(Nodo destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

    /**
     * Obtiene el nodo destino de la conexión.
     *
     * @return El nodo destino.
     */
    public Nodo getDestino() {
        return destino;
    }

    /**
     * Obtiene el peso de la conexión.
     *
     * @return El peso de la conexión.
     */
    public int getPeso() {
        return peso;
    }
}