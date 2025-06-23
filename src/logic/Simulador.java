package logic;

import config.Configuracion;
import domain.*;
import java.util.*;
import presentacion.VistaPorConsola;
import util.EstadisticasSimulacion;

/**
 * Clase principal encargada de simular el recorrido de colectivos,
 * la subida y bajada de pasajeros, y la recopilación de estadísticas finales.
 */
public class Simulador {
    // Capacidad máxima de pasajeros por colectivo.
    private final int MAX_CAPACIDAD = Configuracion.getCantidadPasajeros();
    // Vista para mostrar información de la simulación por consola.
    private final VistaPorConsola vista;
    // Lista de colectivos que participan en la simulación.
    private final List<Colectivo> colectivos;
    // Mapa que almacena la posición actual de cada colectivo en su recorrido. 
    private final Map<Colectivo, Integer> posiciones = new HashMap<>();
    // Mapa que almacena la cantidad de vueltas completadas por cada colectivo. 
    private final Map<Colectivo, Integer> vueltas = new HashMap<>();
    // Cantidad máxima de vueltas que puede realizar cada colectivo. 
    private final int MAX_VUELTAS = Configuracion.getMaxVueltas();
    
    public Simulador(List<Colectivo> colectivos, VistaPorConsola vista) {
        this.colectivos = colectivos;
        this.vista = vista;
        inicializarColectivos();
    }

    /**
     * Inicializa los mapas de posiciones y vueltas para cada colectivo.
     * Asigna la posición inicial 0 y 0 vueltas a cada colectivo.
     */
    private void inicializarColectivos() {
        for (Colectivo c : colectivos) {
            posiciones.put(c, 0);
            vueltas.put(c, 0);
        }
    }

    /**
     * Ejecuta la simulación completa, procesando las paradas y mostrando estadísticas finales.
     */
    public void ejecutar() {
        int numeroParada = 1;

        boolean hayColectivosEnCirculacion;
        do {
            vista.mostrarInicioParada(numeroParada);
            hayColectivosEnCirculacion = false;

            for (Colectivo colectivo : colectivos) {
                if (procesarColectivoEnParada(colectivo)) {
                    hayColectivosEnCirculacion = true;
                }
            }

            numeroParada++;
        } while (hayColectivosEnCirculacion);

        EstadisticasSimulacion.mostrarEstadisticasFinales(colectivos, MAX_CAPACIDAD, vista);
    }

    /**
     * Procesa la llegada de un colectivo a una parada.
     * Si el colectivo ha completado su recorrido, se registra el fin del recorrido.
     * Si no, se procesa la parada actual y se actualiza la posición del colectivo.
     *
     * @param colectivo El colectivo que llega a la parada.
     * @return true si el colectivo sigue en circulación, false si ha finalizado su recorrido.
     */
    private boolean procesarColectivoEnParada(Colectivo colectivo) {
        int pos = posiciones.get(colectivo);
        List<Parada> paradas = colectivo.getLinea().getParadas();

        if (vueltas.get(colectivo) < MAX_VUELTAS) {
            if (pos < paradas.size()) {
                procesarParada(colectivo, paradas, pos);
                return true;
            } else {
                return procesarFinDeRecorrido(colectivo);
            }
        }
        return false;
    }

    /**
     * Procesa la llegada de un colectivo a una parada específica.
     * Muestra la llegada del colectivo, gestiona la subida y bajada de pasajeros,
     * y actualiza el estado del colectivo.
     *
     * @param colectivo El colectivo que llega a la parada.
     * @param paradas La lista de paradas de la línea del colectivo.
     * @param pos La posición actual del colectivo en la lista de paradas.
     */
    private void procesarParada(Colectivo colectivo, List<Parada> paradas, int pos) {
        Parada actual = paradas.get(pos);
        vista.mostrarLlegadaColectivo(colectivo, actual);

        List<Pasajero> bajaron = colectivo.bajarPasajerosEn(actual);
        List<Pasajero> subieron = colectivo.subirPasajerosDesdeParada(actual, pos, MAX_CAPACIDAD);

        vista.mostrarEventosPasajeros(bajaron, subieron);
        vista.mostrarEstadoColectivo(colectivo, bajaron.size(), subieron.size());
        colectivo.registrarOcupacionTramo();

        verificarYMostrarAdvertenciaColectivoLleno(colectivo, actual);

        posiciones.put(colectivo, pos + 1);
    }

    /**
     * Verifica si el colectivo está lleno y muestra una advertencia si es necesario.
     * También cuenta cuántos pasajeros están esperando con un destino válido en la parada actual.
     *
     * @param colectivo El colectivo que se está procesando.
     * @param actual La parada actual donde se encuentra el colectivo.
     */
    private void verificarYMostrarAdvertenciaColectivoLleno(Colectivo colectivo, Parada actual) {
        if (colectivo.getCantidadPasajeros() == MAX_CAPACIDAD) {
            int esperando = contarPasajerosConDestinoValido(colectivo, actual);
            vista.mostrarAdvertenciaColectivoLleno(colectivo, actual, esperando);
        }
    }

    /**
     * Cuenta cuántos pasajeros en la parada actual tienen un destino válido para subir al colectivo.
     * Un destino válido es aquel que está en el recorrido futuro del colectivo.
     *
     * @param colectivo El colectivo que se está procesando.
     * @param actual La parada actual donde se encuentran los pasajeros.
     * @return El número de pasajeros que quieren subir al colectivo desde la parada actual.
     */
    private int contarPasajerosConDestinoValido(Colectivo colectivo, Parada actual) {
        int count = 0;
        for (Pasajero p : actual.getPasajerosEsperando()) {
            if (p.quiereSubirA(colectivo, actual)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Procesa el fin del recorrido de un colectivo.
     * Muestra el mensaje de fin de recorrido, incrementa el contador de vueltas,
     * y reinicia la posición del colectivo si aún no ha alcanzado el máximo de vueltas.
     *
     * @param colectivo El colectivo que ha finalizado su recorrido.
     * @return true si el colectivo continuará en circulación, false si ha alcanzado el máximo de vueltas.
     */
    private boolean procesarFinDeRecorrido(Colectivo colectivo) {
        vista.mostrarFinRecorrido(colectivo);
        int vueltasActuales = vueltas.get(colectivo) + 1;
        vueltas.put(colectivo, vueltasActuales);
        if (vueltasActuales < MAX_VUELTAS) {
            posiciones.put(colectivo, 0);
            return true;
        }
        return false;
    }

}