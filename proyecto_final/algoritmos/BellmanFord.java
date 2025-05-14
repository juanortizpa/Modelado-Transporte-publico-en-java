/**
 * Clase BellmanFord que implementa el algoritmo de Bellman-Ford para encontrar
 * las distancias más cortas desde un nodo origen a todos los demás nodos en un grafo.
 */
package proyecto_final.algoritmos;

import proyecto_final.modelo.*;
import java.util.*;

public class BellmanFord {

    /**
     * Calcula las distancias más cortas desde un nodo origen a todos los demás nodos
     * en un grafo utilizando el algoritmo de Bellman-Ford.
     *
     * @param g      El grafo sobre el cual se ejecutará el algoritmo. Debe contener
     *               nodos y aristas con pesos.
     * @param origen El nodo desde el cual se calcularán las distancias más cortas.
     * @return Un mapa que asocia cada nodo con su distancia mínima desde el nodo origen.
     *         Si un nodo no es alcanzable desde el origen, su distancia será Integer.MAX_VALUE.
     * @throws RuntimeException Si se detecta un ciclo de peso negativo en el grafo.
     */
    public static Map<Nodo, Integer> calcular(Grafo g, Nodo origen) {
        // Inicialización de las distancias: todas a infinito excepto el nodo origen.
        Map<Nodo, Integer> dist = new HashMap<>();
        for (Nodo n : g.getNodos()) dist.put(n, Integer.MAX_VALUE);
        dist.put(origen, 0);

        // Número de nodos en el grafo.
        int V = g.getNodos().size();

        // Relajación de las aristas (V-1) veces.
        for (int i = 0; i < V - 1; i++) {
            for (Nodo u : g.getNodos()) {
                for (Arista a : g.getAristasDesde(u)) {
                    if (dist.get(u) != Integer.MAX_VALUE) {
                        int alt = dist.get(u) + a.getPeso();
                        if (alt < dist.get(a.getDestino())) {
                            dist.put(a.getDestino(), alt);
                        }
                    }
                }
            }
        }

        // Detección de ciclos de peso negativo.
        for (Nodo u : g.getNodos()) {
            for (Arista a : g.getAristasDesde(u)) {
                if (dist.get(u) + a.getPeso() < dist.get(a.getDestino())) {
                    throw new RuntimeException("Ciclo de peso negativo detectado");
                }
            }
        }

        return dist;
    }
}