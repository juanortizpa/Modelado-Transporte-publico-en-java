package proyecto_final;

import java.io.IOException;
import proyecto_final.modelo.*;
import proyecto_final.algoritmos.*;
import proyecto_final.estructuras.*;
import proyecto_final.simulacion.*;
import proyecto_final.io.*;
import proyecto_final.simulacion.SimulacionRuta; // Importar la clase SimulacionRuta
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;

/**
 * Clase principal `App` que permite interactuar con un sistema de gestión de
 * grafos.
 * Proporciona un menú para realizar diversas operaciones como calcular rutas,
 * detectar inconsistencias y gestionar cambios en un grafo cargado desde un
 * archivo CSV.
 */
public class App {

    private static String copiaCSV = ""; // Variable para almacenar el estado anterior del CSV
    private static String rutaCSV = ""; // Variable para almacenar la ruta del archivo CSV

    private static void guardarEstadoCSV(String newPath) {
        try {
            copiaCSV = new String(Files.readAllBytes(Paths.get(newPath)));
        } catch (IOException e) {
            System.err.println("Error al guardar el estado del CSV: " + e.getMessage());
        }
    }

    private static void restaurarEstadoCSV(String newPath) {
        if (!copiaCSV.isEmpty()) {
            try {
                Files.write(Paths.get(newPath), copiaCSV.getBytes());
                System.out.println("Estado del CSV restaurado correctamente.");
            } catch (IOException e) {
                System.err.println("Error al restaurar el estado del CSV: " + e.getMessage());
            }
        } else {
            System.out.println("No hay un estado anterior guardado para restaurar.");
        }
    }

    public static void main(String[] args) throws IOException {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("=== Sistema de Simulación de Rutas ===");
            System.out.print("🔍 Ingrese la ruta al archivo CSV de rutas: ");
            rutaCSV = sc.nextLine(); // Guardar la ruta del archivo CSV
            Grafo grafo = CSVLoader.cargar(rutaCSV);
            PilaCambios pila = new PilaCambios();
            Double ValorMantenimientoMinuto = 1500.0;

            List<Nodo> nodos = new ArrayList<>(grafo.getNodos());

            while (true) {
                System.out.println("\n╔════════════════════════════════╗");
                System.out.println("║           MENÚ PRINCIPAL       ║");
                System.out.println("╠════════════════════════════════╣");
                System.out.println("║ 1. Ruta más rápida (Dijkstra)  ║");
                System.out.println("║ 2. Recorrido completo (Floyd)  ║");
                System.out.println("║ 3. Replanificar rutas          ║");
                System.out.println("║ 4. Detectar inconsistencias    ║");
                System.out.println("║ 5. Deshacer último cambio      ║");
                System.out.println("║ 6. Mostrar congestiones        ║");
                System.out.println("║ 7. importar csv                ║");
                System.out.println("║ 8. Salir del sistema           ║");
                System.out.println("╚════════════════════════════════╝");
                System.out.print("👉 Elige una opción (1-7): ");

                int op = -1;
                try {
                    op = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("⚠️  Entrada inválida. Por favor ingresa un número entre 1 y 7.");
                    continue;
                }

                if (op < 1 || op > 8) {
                    System.out.println("❌ Opción inválida. Por favor selecciona un número válido del 1 al 7.");
                    continue;
                }
                switch (op) {
                    case 1: {
                        System.out.print("🚏Terminal de origen: ");
                        // Obtener nodo de origen
                        Nodo o = new Nodo(sc.nextLine());
                        System.out.println("🏁Terminal de destino: ");
                        // Obtener nodo de destino
                        Nodo d = new Nodo(sc.nextLine());

                        // Iniciar simulación de tráfico (20 segundos)

                        Thread simulador = new Thread(new SimuladorTráfico(grafo, 20000, true));
                        simulador.start();
                        System.out.println("🕒 Simulación en curso. Observa los cambios en la consola...");

                        System.out.println("🕒 Calculando ruta más rápida... espera 20 segundos.");
                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            System.err.println("⛔ Simulación interrumpida.");
                        }

                        Map<Nodo, Integer> distancias = Dijkstra.calcular(grafo, o, grafo);
                        Map<Nodo, Nodo> previos = Dijkstra.getPrevios();
                        
                        // Reconstruir ruta
                        List<Nodo> ruta = new ArrayList<>();
                        Nodo actual = d;
                        simulador.interrupt(); // Detener simulador
                        while (actual != null && !actual.equals(o)) {
                            ruta.add(actual);
                            actual = previos.get(actual);
                        }
                        
                        if (actual == null) {
                            System.out.println("⚠️ No existe una ruta desde " + o + " a " + d);
                        } else {
                            ruta.add(o);
                            Collections.reverse(ruta);

                            System.out.print("🛣️  Ruta calculada: ");
                            for (int i = 0; i < ruta.size(); i++) {
                                System.out.print(ruta.get(i));
                                if (i < ruta.size() - 1)
                                    System.out.print(" -> ");
                            }
                            System.out.println();

                            System.out.printf("%n🕓 Tiempo estimado: %d minutos%n", distancias.get(d));

                            Double ValorMantenimientoTotal = distancias.get(d) * ValorMantenimientoMinuto;
                            System.out.printf("💰 Costo de mantenimiento: $%.2f%n", ValorMantenimientoTotal);

                            Random rand = new Random();
                            int factor = rand.nextInt(100 * (distancias.get(d) / 20)) + 1; // Número aleatorio entre 1 y
                                                                                           // 100
                            System.out.println("🧍Pasajeros estimados: " + factor);
                            double Ganancia = 3200 * (factor); // Ganancia por minuto
                            System.out.println("💰 Ganancia bruta: $" + Ganancia);
                            System.out.printf("📈 Ganancia estimada: $%.2f%n", Ganancia-ValorMantenimientoTotal);
                            if (Ganancia - ValorMantenimientoTotal < 0) {
                                System.out.println("⚠️ La ganancia es negativa. Revisa la ruta.");
                            } else {
                                System.out.println("✅ La ruta es rentable.");
                                
                            }
                            System.out.println("Ruta calculada y mostrada correctamente.");
                            break;
                        }
                    }
                    case 2: {
                        System.out.print("🚏 Estacion origen: ");
                        Nodo o = new Nodo(sc.nextLine());
                        System.out.print("🏁 Estacion destino: ");
                        Nodo d = new Nodo(sc.nextLine());

                        // Iniciar simulación de tráfico (20 segundos)
                        System.out.println("🕒 Iniciando simulación de tráfico...");
                        Thread simulador = new Thread(new SimuladorTráfico(grafo, 20000, true));
                        simulador.start();
                        System.out.println("🕒 Simulación en curso. Observa los cambios en la consola...");

                        System.out.println("🕒 Calculando ruta más rápida... espera 20 segundos.");
                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            System.err.println("⛔ Simulación interrumpida.");
                        }

                        Map<Nodo, Integer> distancias = Dijkstra.calcular(grafo, o, grafo);
                        Map<Nodo, Nodo> previos = Dijkstra.getPrevios();

                        // Reconstruir ruta
                        List<Nodo> ruta = new ArrayList<>();
                        Nodo actual = d;
                        while (actual != null && !actual.equals(o)) {
                            ruta.add(actual);
                            actual = previos.get(actual);
                        }

                        if (actual == null) {
                            System.out.println("⚠️ No hay ruta desde " + o + " a " + d);
                        } else {
                            ruta.add(o);
                            Collections.reverse(ruta);

                            System.out.print("🛣️  Ruta: ");
                            for (int i = 0; i < ruta.size(); i++) {
                                System.out.print(ruta.get(i));
                                if (i < ruta.size() - 1)
                                    System.out.print(" -> ");
                            }
                            System.out.println();

                            System.out.printf("%n🕓 Tiempo estimado: %d minutos%n", distancias.get(d));
                            simulador.interrupt(); // Detener simulador
                            System.out.println("Ruta calculada y mostrada correctamente.");
                        }

                        pila.push("Dijkstra de " + o + " a " + d);
                        Double ValorMantenimientoTotal = distancias.get(d) * ValorMantenimientoMinuto;
                        System.out.printf("%n🕓 Tiempo estimado: %d minutos%n", distancias.get(d));

                            System.out.printf("💰 Costo de mantenimiento: $%.2f%n", ValorMantenimientoTotal);

                            Random rand = new Random();
                            int factor = rand.nextInt(100 * (distancias.get(d) / 20)) + 1; // Número aleatorio entre 1 y
                                                                                           // 100
                            System.out.println("🧍Pasajeros estimados: " + factor);
                            double Ganancia = 3200 * (factor); // Ganancia por minuto
                            System.out.println("💰 Ganancia bruta: $" + Ganancia);
                            System.out.printf("📈 Ganancia estimada: $%.2f%n", Ganancia-ValorMantenimientoTotal);
                            if (Ganancia - ValorMantenimientoTotal < 0) {
                                System.out.println("⚠️ La ganancia es negativa. Revisa la ruta.");
                            } else {
                                System.out.println("✅ La ruta es rentable.");
                                
                            }
                        System.out.println("Ruta calculada y mostrada correctamente.");
                        break;
                    }
                    case 3: {
                        guardarEstadoCSV(rutaCSV); // Guardar el estado actual del CSV
                        SimulacionRuta.setRutaCsv(rutaCSV); // Asegurar que se actualice el archivo especificado
                        SimulacionRuta.actualizarCSV(); // Actualizar el archivo CSV
                        int[][] distFW = FloydWarshall.calcular(grafo, nodos);
                        System.out.println("Matriz de distancias entre estaciones:");
                        System.out.print("\t");
                        for (Nodo n : nodos) {
                            System.out.print(n + "\t");
                        }
                        System.out.println();
                        for (int i = 0; i < nodos.size(); i++) {
                            System.out.print(nodos.get(i) + "\t");
                            for (int j = 0; j < nodos.size(); j++) {
                                int dist = distFW[i][j];
                                if (dist == Integer.MAX_VALUE / 2 || dist == Integer.MAX_VALUE) {
                                    System.out.print("INF\t");
                                } else {
                                    System.out.print(dist + "\t");
                                }
                            }
                            System.out.println();
                        }
                        pila.push("Floyd-Warshall completo");
                        break;
                    }
                    case 4: {
                        System.out.print("🚏 Estacion origen: ");
                        Nodo bo = new Nodo(sc.nextLine());
                        try {
                            Map<Nodo, Integer> distBF = BellmanFord.calcular(grafo, bo);
                            distBF.forEach((n, d2) -> System.out.printf("%s: %d%n", n, d2));
                            pila.push("Bellman-Ford desde " + bo);
                        } catch (RuntimeException e) {
                            System.err.println("⛔ Error: " + e.getMessage());
                        }
                        break;
                    }
                    case 5: {
                        restaurarEstadoCSV(rutaCSV); // Restaurar el estado anterior del CSV
                        break;
                    }
                    case 6: {
                        System.out.println("🚦 Rutas con congestión (tiempo > 10 min):");
                        for (Nodo n : nodos) {
                            for (Arista a : grafo.getAristasDesde(n)) {
                                if (a.getPeso() > 10) {
                                    System.out.printf("⚠️  %s -> %s: %d minutos%n", n, a.getDestino(), a.getPeso());
                                }
                            }
                        }
                        break;
                    }
                    case 7: {
                        System.out.print("🔍 Ingrese la ruta al archivo CSV de rutas: ");
                        String path = sc.nextLine();
                        try {
                            grafo = CSVLoader.cargar(path);
                            rutaCSV = path; // Actualizar la ruta del archivo CSV
                            System.out.println("✅ Archivo CSV importado correctamente.");
                        } catch (IOException e) {
                            System.err.println("⛔ Error al importar el archivo CSV: " + e.getMessage());
                        }
                        break;
                    }
                    case 8: {
                        System.out.println("Guardando la ruta del archivo CSV antes de salir...");
                        try (FileWriter writer = new FileWriter("ruta_guardada.txt")) {
                            writer.write(rutaCSV);
                            System.out.println("Ruta guardada correctamente.");
                        } catch (IOException e) {
                            System.err.println("Error al guardar la ruta: " + e.getMessage());
                        }
                        System.out.println("👋 Gracias por usar el sistema. ¡Hasta luego!");
                        System.exit(0);
                        break;
                    }

                }
            }
        }
    }
}