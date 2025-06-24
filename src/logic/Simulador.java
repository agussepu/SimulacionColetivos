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
    private final int MAX_CAPACIDAD = Configuracion.getCantidadPasajeros();
    private final VistaPorConsola vista;
    private final List<Colectivo> colectivos;
    private final Map<Colectivo, Integer> posiciones = new HashMap<>();
    private final Map<Colectivo, Integer> vueltas = new HashMap<>();
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
                Parada actual = paradas.get(pos);
                procesarParada(colectivo, actual, pos);
                return true;
            } else {
                return procesarFinDeRecorrido(colectivo);
            }
        }
        return false;
    }

    /**
     * Procesa todas las acciones que ocurren cuando un colectivo llega a una parada.
     * Esto incluye mostrar la llegada, gestionar la bajada y subida de pasajeros,
     * mostrar los eventos ocurridos, actualizar el estado del colectivo,
     * registrar la ocupación del tramo, mostrar advertencias si el colectivo está lleno,
     * y avanzar la posición del colectivo al siguiente tramo.
     *
     * @param colectivo El colectivo que está procesando la parada.
     * @param actual La parada actual donde se encuentra el colectivo.
     * @param pos La posición actual del colectivo en la lista de paradas.
     */
    private void procesarParada(Colectivo colectivo, Parada actual, int pos) {
        vista.mostrarLlegadaColectivo(colectivo, actual);

        List<Pasajero> bajaron = colectivo.bajarPasajerosEn(actual);
        List<Pasajero> subieron = colectivo.subirPasajerosDesdeParada(actual, pos, MAX_CAPACIDAD);

        vista.mostrarEventosPasajeros(bajaron, subieron);
        vista.mostrarEstadoColectivo(colectivo, bajaron.size(), subieron.size());
        colectivo.registrarOcupacionTramo();

        if (colectivo.getCantidadPasajeros() == MAX_CAPACIDAD) 
            mostrarAdvertenciaColectivoLleno(colectivo, actual);

        posiciones.put(colectivo, pos + 1);
    }

    /**
     * Muestra una advertencia cuando el colectivo está lleno y hay pasajeros esperando
     * en la parada actual que podrían haber subido si hubiera espacio disponible.
     *
     * Cuenta cuántos pasajeros en la parada actual desean y pueden subir al colectivo,
     * pero no pueden hacerlo porque el colectivo ya alcanzó su capacidad máxima.
     * Luego, muestra esta información a través de la vista.
     *
     * @param colectivo El colectivo que está lleno.
     * @param actual La parada actual donde se encuentran los pasajeros esperando.
     */
    private void mostrarAdvertenciaColectivoLleno(Colectivo colectivo, Parada actual) {
        int esperando = 0;
        for (Pasajero p : actual.getPasajerosEsperando()) {
            if (p.quiereSubirA(colectivo, actual)) {
                esperando++;
            }
        }
        vista.mostrarAdvertenciaColectivoLleno(colectivo, actual, esperando);
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

        boolean sigueEnCirculacion = vueltasActuales < MAX_VUELTAS;

        if (sigueEnCirculacion) 
            posiciones.put(colectivo, 0); // Reinicia el recorrido
        
        return sigueEnCirculacion;
    }


}