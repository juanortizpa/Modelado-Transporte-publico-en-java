package proyecto_final.estructuras;

import java.util.*;

/**
 * Clase ListaCongestiones que implementa una lista enlazada para gestionar
 * intersecciones congestionadas en un sistema de tráfico.
 */
public class ListaCongestiones {
    private final LinkedList<String> lista = new LinkedList<>();

    /**
     * Agrega una intersección a la lista de congestiones.
     *
     * @param interseccion El nombre de la intersección a agregar.
     */
    public void add(String interseccion) {
        lista.add(interseccion);
    }

    /**
     * Elimina y devuelve la primera intersección de la lista.
     *
     * @return El nombre de la intersección eliminada, o null si la lista está vacía.
     */
    public String remove() {
        return lista.isEmpty() ? null : lista.removeFirst();
    }

    /**
     * Verifica si la lista de congestiones está vacía.
     *
     * @return true si la lista está vacía, false en caso contrario.
     */
    public boolean isEmpty() {
        return lista.isEmpty();
    }
}