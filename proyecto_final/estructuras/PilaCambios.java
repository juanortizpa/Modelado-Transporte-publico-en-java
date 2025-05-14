package proyecto_final.estructuras;

import proyecto_final.modelo.*;
import java.util.*;

/**
 * Clase PilaCambios que implementa una pila para gestionar cambios en un
 * sistema.
 * Utiliza una estructura de datos tipo Deque para almacenar los elementos.
 */
public class PilaCambios {
    private final Deque<String> pila = new ArrayDeque<>();

    /**
     * Agrega un cambio a la pila.
     *
     * @param cambio El cambio a agregar a la pila.
     */
    public void push(String cambio) {
        pila.push(cambio);
    }

    /**
     * Elimina y devuelve el cambio en la cima de la pila.
     *
     * @return El cambio eliminado, o null si la pila está vacía.
     */
    public String pop() {
        return pila.isEmpty() ? null : pila.pop();
    }

    /**
     * Verifica si la pila está vacía.
     *
     * @return true si la pila está vacía, false en caso contrario.
     */
    public boolean isEmpty() {
        return pila.isEmpty();
    }
}