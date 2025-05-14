package proyecto_final.modelo;

/**
 * Clase Arista que representa una conexión entre dos nodos en un grafo.
 * Cada arista tiene un nodo origen, un nodo destino y un peso asociado,
 * que puede representar el tiempo en minutos u otra métrica relevante.
 */
public class Arista {
    private final Nodo origen;  // Nodo de origen de la arista
    private final Nodo destino; // Nodo de destino de la arista
    private int peso;     // Peso de la arista (por ejemplo, tiempo en minutos)
    private boolean estado;  
    private double costo;     

    /**
     * Constructor de la clase Arista.
     *
     * @param origen El nodo origen de la arista.
     * @param destino El nodo destino de la arista.
     * @param peso El peso de la arista (por ejemplo, tiempo en minutos).
     */
    public Arista(Nodo origen, Nodo destino, int peso, boolean estado, double costo) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.estado = estado;
        this.costo = costo;
        this.costo = costo;
    }

    /**
     * Obtiene el nodo origen de la arista.
     *
     * @return El nodo origen.
     */
    public Nodo getOrigen() { return origen; }


    public boolean getEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    /**
     * Obtiene el nodo destino de la arista.
     *
     * @return El nodo destino.
     */
    public Nodo getDestino() { return destino; }

    /**
     * Obtiene el peso de la arista.
     *
     * @return El peso de la arista.
     */
    public int getPeso() { return peso; }

    /**
     * Establece un nuevo peso para la arista.



    public void getcosto(double costo) {return costo}
public void setcosto(double costo){this.costo = costo;}     *
     * @param peso El nuevo peso de la arista.
     */
    public void setPeso(int peso) { this.peso = peso; }
}