package proyecto_final.algoritmos;

import proyecto_final.modelo.*;
import java.util.*;

/**
 * Clase Dijkstra que implementa el algoritmo de Dijkstra para encontrar
 * las distancias más cortas desde un nodo origen a todos los demás nodos en un grafo.
 */
public class Dijkstra {
    private static Map<Nodo, Nodo> previos; // Para rastrear los nodos previos en el camino más corto

    /**
     * Calcula las distancias más cortas desde un nodo origen a todos los demás nodos
     * en un grafo utilizando el algoritmo de Dijkstra.
     *
     * @param g      El grafo sobre el cual se ejecutará el algoritmo. Debe contener
     *               nodos y aristas con pesos no negativos.
     * @param origen El nodo desde el cual se calcularán las distancias más cortas.
     * @return Un mapa que asocia cada nodo con su distancia mínima desde el nodo origen.
     *         Si un nodo no es alcanzable desde el origen, su distancia será Integer.MAX_VALUE.
     */
    public static Map<Nodo, Integer> calcular(Grafo g, Nodo origen, Grafo estado) {
        // Mapa para almacenar las distancias mínimas desde el nodo origen
        Map<Nodo, Integer> dist = new HashMap<>();
        previos = new HashMap<>(); // Inicializar el mapa de nodos previos

        

        // Inicializar todas las distancias a infinito, excepto el nodo origen
        for (Nodo n : g.getNodos()) dist.put(n, Integer.MAX_VALUE);
        dist.put(origen, 0);

        // Cola de prioridad para seleccionar el nodo con la menor distancia
        PriorityQueue<Nodo> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));
        pq.add(origen);

        // Procesar los nodos en la cola de prioridad
        while (!pq.isEmpty()) {
            Nodo u = pq.poll(); // Extraer el nodo con la menor distancia
            for (Arista a : g.getAristasDesde(u)) { // Recorrer las aristas desde el nodo actual
                // Solo considerar aristas con estado true
                if (!a.getEstado()) continue;
                Nodo v = a.getDestino();
                int alt = dist.get(u) + a.getPeso(); // Calcular la distancia alternativa
                if (alt < dist.get(v)) { // Si se encuentra un camino más corto
                    dist.put(v, alt); // Actualizar la distancia mínima
                    previos.put(v, u); // Registrar el nodo previo
                    pq.remove(v); // Eliminar el nodo de la cola si ya estaba
                    pq.add(v); // Añadir el nodo con la nueva distancia
                }
            }
        }
        return dist;
    }

    /**
     * Devuelve el mapa de nodos previos que permite reconstruir los caminos más cortos
     * desde el nodo origen a cualquier otro nodo.
     *
     * @return Un mapa que asocia cada nodo con su nodo previo en el camino más corto.
     */
    public static Map<Nodo, Nodo> getPrevios() {
        return previos;
    }
}