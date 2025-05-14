package proyecto_final.simulacion;

import proyecto_final.modelo.Grafo;
import proyecto_final.modelo.Nodo;
import proyecto_final.modelo.Arista;

import java.util.*;

public class SimuladorTráfico implements Runnable {
    private final Grafo grafo;
    private final long duracionMs;
    private final Random rand = new Random();
    private final boolean estado;

    public SimuladorTráfico(Grafo grafo, long duracionMs, boolean estado) {
        this.grafo = grafo;
        this.duracionMs = duracionMs;
        this.estado = estado;
    }

    @Override
    public void run() {
        System.out.println("Simulador iniciado. Duración: " + duracionMs + " ms");
        long fin = System.currentTimeMillis() + duracionMs;
        List<Nodo> nodos = new ArrayList<>(grafo.getNodos());
        while (System.currentTimeMillis() < fin) {
            try {
                Thread.sleep(rand.nextInt(3000));
                Nodo o = nodos.get(rand.nextInt(nodos.size()));
                Nodo d = nodos.get(rand.nextInt(nodos.size()));
                System.out.printf("Procesando nodo origen: %s, nodo destino: %s%n", o, d);
                for (Arista a : grafo.getAristasDesde(o)) {
                    if (a.getDestino().equals(d)) {
                        int inc = 1 + rand.nextInt(8);
                        a.setPeso(a.getPeso() + inc);
                        System.out.printf("Incremento de %d minutos en %s -> %s%n",
                                inc, o, d);
                        // Evento aleatorio para cambiar el estado
                        if (rand.nextDouble() < 0.7 && a.getEstado()) { // 70% de probabilidad de desactivar
                            a.setEstado(false);
                            System.out.printf("Conexión %s -> %s DESACTIVADA temporalmente%n", o, d);
                            // Reactivar después de un tiempo aleatorio
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    a.setEstado(true);
                                    System.out.printf("Conexión %s -> %s REACTIVADA%n", o, d);
                                }
                            }, 7000 + rand.nextInt(12000)); // entre 2 y 7 segundos
                        }
                    }
                }
                Thread.sleep(rand.nextInt(3000));
            } catch (InterruptedException ignored) {}
        }
        System.out.println("Simulador finalizado.");
    }
}
