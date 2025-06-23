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
    /**
     * Mapa que almacena, para cada colectivo y posición, el conjunto de paradas restantes
     * donde pueden bajar los pasajeros.
     */
    private final Map<Colectivo, Map<Integer, Set<Parada>>> paradasRestantesPorColectivo = new HashMap<>();

    /**
     * Crea un simulador con la lista de colectivos y la vista especificada.
     *
     * @param colectivos Lista de colectivos a simular.
     * @param vista Vista para mostrar información de la simulación.
     */
    public Simulador(List<Colectivo> colectivos, VistaPorConsola vista) {
        this.colectivos = colectivos;
        this.vista = vista;
        inicializarColectivos();
    }

    /**
     * Inicializa las posiciones y vueltas de los colectivos,
     * y precalcula las paradas restantes para cada uno.
     */
    private void inicializarColectivos() {
        for (Colectivo c : colectivos) {
            posiciones.put(c, 0);
            vueltas.put(c, 0);
            precalcularParadasRestantes(c); 
        }
    }

    /**
     * Precalcula para cada posición del recorrido de un colectivo,
     * el conjunto de paradas restantes donde pueden bajar los pasajeros.
     *
     * @param colectivo Colectivo para el que se realiza el precálculo.
     */
    private void precalcularParadasRestantes(Colectivo colectivo) {
        List<Parada> paradas = colectivo.getLinea().getParadas();
        Map<Integer, Set<Parada>> porPosicion = new HashMap<>();
        for (int i = 0; i < paradas.size(); i++) {
            porPosicion.put(i, new HashSet<>(paradas.subList(i + 1, paradas.size())));
        }
        paradasRestantesPorColectivo.put(colectivo, porPosicion);
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

        mostrarEstadisticasFinales();
    }

    /**
     * Procesa el avance de un colectivo en una parada, gestionando vueltas y fin de recorrido.
     *
     * @param colectivo Colectivo a procesar.
     * @return true si el colectivo sigue en simulación, false si terminó.
     */
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

    /**
     * Procesa la lógica de llegada de un colectivo a una parada,
     * incluyendo bajada y subida de pasajeros y registro de eventos.
     *
     * @param colectivo Colectivo que llega a la parada.
     * @param paradas Lista de paradas del recorrido.
     * @param pos Posición actual en la lista de paradas.
     * @return true si el colectivo debe continuar, false en caso contrario.
     */
    private boolean procesarParada(Colectivo colectivo, List<Parada> paradas, int pos) {
        Parada actual = paradas.get(pos);
        vista.mostrarLlegadaColectivo(colectivo, actual);

        List<Pasajero> bajaron = colectivo.bajarPasajerosEn(actual);
        List<Pasajero> subieron = colectivo.subirPasajerosDesdeParada(
            actual,
            paradasRestantesPorColectivo.get(colectivo).get(pos),
            MAX_CAPACIDAD
        );

        mostrarEventosPasajeros(bajaron, subieron);
        vista.mostrarEstadoColectivo(colectivo, bajaron.size(), subieron.size());
        colectivo.registrarOcupacionTramo();
        mostrarAdvertenciaColectivoLleno(colectivo, actual, pos);

        posiciones.put(colectivo, pos + 1);
        return true;
    }

    /**
     * Procesa el fin de recorrido de un colectivo, gestionando vueltas.
     *
     * @param colectivo Colectivo que finaliza el recorrido.
     * @return true si debe reiniciar el recorrido, false si finalizó todas las vueltas.
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

    /**
     * Muestra los eventos de bajada y subida de pasajeros en la vista.
     *
     * @param bajaron Lista de pasajeros que bajaron.
     * @param subieron Lista de pasajeros que subieron.
     */
    private void mostrarEventosPasajeros(List<Pasajero> bajaron, List<Pasajero> subieron) {
        for (Pasajero p : bajaron) {
            vista.mostrarPasajeroBajo(p);
        }
        for (Pasajero p : subieron) {
            vista.mostrarPasajeroSubio(p);
        }
    }

    /**
     * Muestra una advertencia si el colectivo está lleno y hay pasajeros esperando.
     *
     * @param colectivo Colectivo que está lleno.
     * @param actual Parada actual.
     * @param pos Posición actual en la lista de paradas.
     */
    private void mostrarAdvertenciaColectivoLleno(Colectivo colectivo, Parada actual, int pos) {
        if (colectivo.getCantidadPasajeros() == MAX_CAPACIDAD) {
            int esperando = 0;
            Set<Parada> paradasRestantes = paradasRestantesPorColectivo.get(colectivo).get(pos);
            for (Pasajero p : actual.getPasajerosEsperando()) {
                if (paradasRestantes.contains(p.getDestino())) {
                    esperando++;
                }
            }
            if (esperando > 0) {
                vista.mostrarColectivoLlenoYPasajerosEsperando(colectivo, actual, esperando);
            }
        }
    }

    /**
     * Muestra las estadísticas finales de la simulación utilizando la vista.
     */
    private void mostrarEstadisticasFinales() {
        vista.mostrarFinSimulacion();
        EstadisticasSimulacion.mostrarIndiceSatisfaccion(
            util.AdministracionPasajeros.getTodosLosPasajeros(), vista
        );
        EstadisticasSimulacion.mostrarOcupacionPromedioPorColectivo(
            colectivos, MAX_CAPACIDAD, vista
        );
    }
}