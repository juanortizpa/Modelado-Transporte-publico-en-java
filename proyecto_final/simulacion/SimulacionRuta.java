package proyecto_final.simulacion;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SimulacionRuta {
    // Ruta del archivo CSV
    private static String RUTA_CSV;

    public static void setRutaCsv(String rutaCsv) {
        RUTA_CSV = rutaCsv;
    }

    // Método para generar un tiempo aleatorio basado en origen y destino
    private static int generarTiempoAleatorio(String origen, String destino) {
        // Calcular la "distancia" basada en las etiquetas de los nodos
        int distancia = 0;

        // Si el origen o destino empiezan con 'T', tomar en cuenta el segundo valor numérico
        if (origen.startsWith("T") && destino.startsWith("T")) {
            distancia = Math.abs(Character.getNumericValue(origen.charAt(1)) - Character.getNumericValue(destino.charAt(1)));
        } else {
            // Calcular la distancia basada en las etiquetas alfabéticas
            distancia = Math.abs(origen.charAt(0) - destino.charAt(0));
        }

        // Generar tiempo proporcional a la "distancia"
        Random random = new Random();
        final int TIEMPO_MIN = 5; // Tiempo mínimo en minutos
        final int TIEMPO_MAX = 100; // Tiempo máximo en minutos

        // Escalar el tiempo según la distancia con mayor diferencia
        int tiempo = TIEMPO_MIN + (distancia * (random.nextInt(3) + 1)); // Asegurar que el tiempo sea mayor con mayor distancia
        return Math.min(tiempo, TIEMPO_MAX); // Limitar al tiempo máximo
    }

    // Método para leer las rutas existentes del archivo CSV
    private static List<String[]> leerRutasExistentes() {
        List<String[]> rutas = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(RUTA_CSV))) {
            String linea;
            lector.readLine(); // Saltar la línea de encabezados
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                rutas.add(datos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rutas;
    }

    // Método para actualizar el archivo CSV con tiempos aleatorios
    public static void actualizarCSV() {
        List<String[]> rutas = leerRutasExistentes();
        try (FileWriter escritor = new FileWriter(RUTA_CSV)) {
            // Escribir encabezados
            escritor.write("origen,destino,tiempo,estado,costo\n");
            for (String[] ruta : rutas) {
                // Generar un nuevo tiempo aleatorio basado en origen y destino
                int nuevoTiempo = generarTiempoAleatorio(ruta[0], ruta[1]);
                // Escribir la ruta con el nuevo tiempo
                escritor.write(ruta[0] + "," + ruta[1] + "," + nuevoTiempo + "," + "true" + "," + "3200" + "\n");
            }
            escritor.flush(); // Asegurar que los datos se escriban inmediatamente
            System.out.println("Archivo actualizado con nuevos tiempos.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método principal
    public static void main(String[] args) {
        actualizarCSV();
    }
}