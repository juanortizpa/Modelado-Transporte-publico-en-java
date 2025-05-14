package proyecto_final.algoritmos;

import proyecto_final.modelo.*;
import java.util.*;

/**
 * Clase FloydWarshall que implementa el algoritmo de Floyd-Warshall para encontrar
 * las distancias más cortas entre todos los pares de nodos en un grafo.
 */
public class FloydWarshall {

    /**
     * Calcula las distancias más cortas entre todos los pares de nodos en un grafo
     * utilizando el algoritmo de Floyd-Warshall.
     *
     * @param g      El grafo sobre el cual se ejecutará el algoritmo.
     * @param nodos  La lista de nodos del grafo, en un orden fijo.
     * @return Una matriz de enteros donde la posición [i][j] representa la distancia
     *         más corta desde el nodo i al nodo j. Si no hay camino, el valor será
     *         Integer.MAX_VALUE / 2.
     */
    public static int[][] calcular(Grafo g, List<Nodo> nodos) {
        int n = nodos.size();
        int[][] dist = new int[n][n];
        final int INF = Integer.MAX_VALUE / 2; // Valor para representar "infinito"

        // Inicialización de la matriz de distancias
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = (i == j ? 0 : INF); // Distancia a sí mismo es 0, el resto es infinito
            }
        }

        // Configuración inicial de las distancias basadas en las aristas del grafo
        for (int i = 0; i < n; i++) {
            Nodo u = nodos.get(i);
            for (Arista a : g.getAristasDesde(u)) {
                int j = nodos.indexOf(a.getDestino());
                dist[i][j] = a.getPeso(); // Peso de la arista entre los nodos
            }
        }

        // Aplicación del algoritmo de Floyd-Warshall
        for (int k = 0; k < n; k++) { // Nodo intermedio
            for (int i = 0; i < n; i++) { // Nodo de origen
                for (int j = 0; j < n; j++) { // Nodo de destino
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        return dist;
    }
}