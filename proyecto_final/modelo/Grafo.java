package proyecto_final.modelo;
import java.util.*;

/**
 * Clase Grafo que representa un grafo dirigido con nodos y aristas.
 * Permite agregar nodos, aristas y obtener información sobre las conexiones del grafo.
 */
public class Grafo {
    private final Map<Nodo, List<Arista>> adj = new HashMap<>();

    /**
     * Agrega un nodo al grafo. Si el nodo ya existe, no se realiza ninguna acción.
     *
     * @param n El nodo a agregar.
     */
    public void addNodo(Nodo n) {
        adj.putIfAbsent(n, new ArrayList<>());
    }

    /**
     * Agrega una arista dirigida entre dos nodos con un peso especificado.
     * Si los nodos no existen en el grafo, se agregan automáticamente.
     *
     * @param u El nodo origen de la arista.
     * @param v El nodo destino de la arista.
     * @param peso El peso de la arista.
     */
    public void addArista(Nodo u, Nodo v, int peso , boolean estado, double costo) {
        addNodo(u);
        addNodo(v);
        adj.get(u).add(new Arista(u, v, peso, estado, costo));
    }

    /**
     * Obtiene la lista de aristas que salen desde un nodo específico.
     *
     * @param n El nodo desde el cual se obtendrán las aristas.
     * @return Una lista de aristas que salen desde el nodo, o una lista vacía si no hay aristas.
     */
    public List<Arista> getAristasDesde(Nodo n) {
        return adj.getOrDefault(n, Collections.emptyList());
    }

    /**
     * Obtiene las aristas adyacentes a un nodo específico.
     * Solo incluye las aristas cuyo origen coincide con el nodo dado.
     *
     * @param nodo El nodo para el cual se obtendrán las aristas adyacentes.
     * @return Una lista de aristas adyacentes al nodo.
     */
    public List<Arista> getAdyacentes(Nodo nodo) {
        List<Arista> adyacentes = new ArrayList<>();
        for (Arista arista : adj.getOrDefault(nodo, Collections.emptyList())) {
            if (arista.getOrigen().equals(nodo)) {
                adyacentes.add(arista);
            }
        }
        return adyacentes;
    }

    /**
     * Obtiene el conjunto de nodos presentes en el grafo.
     *
     * @return Un conjunto de nodos del grafo.
     */
    public Set<Nodo> getNodos() {
        return adj.keySet();
    }
}