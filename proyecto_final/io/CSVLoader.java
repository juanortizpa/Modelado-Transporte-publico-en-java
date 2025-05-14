package proyecto_final.io;

import proyecto_final.modelo.*;
import java.io.*;
import java.util.*;

/**
 * Clase CSVLoader que permite cargar un grafo desde un archivo CSV.
 * El archivo CSV debe tener el siguiente formato:
 * - Cada línea representa una arista del grafo.
 * - Las columnas deben estar separadas por comas y representar:
 *   1. Nodo origen
 *   2. Nodo destino
 *   3. Peso de la arista
 *   4. Estado de la arista (opcional, por defecto true)
 * - La primera línea se considera un encabezado y se ignora.
 */
public class CSVLoader {

    /**
     * Carga un grafo desde un archivo CSV.
     *
     * @param path La ruta del archivo CSV que contiene la información del grafo.
     * @return Un objeto de tipo Grafo que representa el grafo cargado.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static Grafo cargar(String path) throws IOException {
        Grafo g = new Grafo();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean primera = true;
            while ((line = br.readLine()) != null) {
                if (primera) {
                    primera = false;
                    continue; // saltar encabezado
                }
                String[] tok = line.split(",");
                if (tok.length < 3) {
                    System.err.println("Línea inválida en CSV (esperado al menos 3 columnas): " + line);
                    continue;
                }
                Nodo u = new Nodo(tok[0].trim());
                Nodo v = new Nodo(tok[1].trim());
                int peso = Integer.parseInt(tok[2].trim());
                boolean estado = true;
                if (tok.length > 3) {
                    estado = Boolean.parseBoolean(tok[3].trim());
                }
                g.addArista(u, v, peso, estado, 3200.0); // Usar el estado real del CSV, valor por defecto para el double
            }
        }
        return g;
    }
}