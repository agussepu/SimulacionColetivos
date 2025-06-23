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
    /** Capacidad máxima de pasajeros por colectivo. */
    private final int MAX_CAPACIDAD = Configuracion.getCantidadPasajeros();
    /** Lista de colectivos que participan en la simulación. */
    private final List<Colectivo> colectivos;
    /** Vista para mostrar información de la simulación por consola. */
    private final VistaPorConsola vista;
    /** Mapa que almacena la posición actual de cada colectivo en su recorrido. */
    private final Map<Colectivo, Integer> posiciones = new HashMap<>();
    /** Mapa que almacena la cantidad de vueltas completadas por cada colectivo. */
    private final Map<Colectivo, Integer> vueltas = new HashMap<>();
    /** Cantidad máxima de vueltas que puede realizar cada colectivo. */
    private final int MAX_VUELTAS = Configuracion.getMaxVueltas();
    
    public Simulador(List<Colectivo> colectivos, VistaPorConsola vista) {
        this.colectivos = colectivos;
        this.vista = vista;
        inicializarColectivos();
    }

    
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
        boolean enCurso = true;
        int numeroParada = 1;

        while (enCurso) {
            vista.mostrarInicioParada(numeroParada);
            enCurso = false;
            for (Colectivo colectivo : colectivos) {
                if (procesarColectivoEnParada(colectivo)) {
                    enCurso = true;
                }
            }
            numeroParada++;
        }

        EstadisticasSimulacion.mostrarEstadisticasFinales(colectivos, MAX_CAPACIDAD, vista);
    }

    private boolean procesarColectivoEnParada(Colectivo colectivo) {
        int pos = posiciones.get(colectivo);
        List<Parada> paradas = colectivo.getLinea().getParadas();

        if (vueltas.get(colectivo) < MAX_VUELTAS) {
            if (pos < paradas.size()) {
                return procesarParada(colectivo, paradas, pos);
            } else {
                return procesarFinDeRecorrido(colectivo);
            }
        }
        return false;
    }

    private boolean procesarParada(Colectivo colectivo, List<Parada> paradas, int pos) {
        Parada actual = paradas.get(pos);
        vista.mostrarLlegadaColectivo(colectivo, actual);

        List<Pasajero> bajaron = colectivo.bajarPasajerosEn(actual);
        
        List<Pasajero> subieron = colectivo.subirPasajerosDesdeParada(actual, pos, MAX_CAPACIDAD);

        vista.mostrarEventosPasajeros(bajaron, subieron);
        vista.mostrarEstadoColectivo(colectivo, bajaron.size(), subieron.size());
        colectivo.registrarOcupacionTramo();

        if (colectivo.getCantidadPasajeros() == MAX_CAPACIDAD) {
            int esperando = contarPasajerosConDestinoValido(colectivo, actual);
            vista.mostrarAdvertenciaColectivoLleno(colectivo, actual, esperando);
        }

        posiciones.put(colectivo, pos + 1);
        return true;
    }

    private int contarPasajerosConDestinoValido(Colectivo colectivo, Parada actual) {
        int count = 0;
        for (Pasajero p : actual.getPasajerosEsperando()) {
            if (p.quiereSubirA(colectivo, actual)) {
                count++;
            }
        }
        return count;
    }

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