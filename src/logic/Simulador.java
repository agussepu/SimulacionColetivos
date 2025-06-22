package logic;

import config.Configuracion;
import domain.*;
import java.util.*;
import presentacion.VistaPorConsola;
import util.EstadisticasSimulacion;

public class Simulador {
    private final int MAX_CAPACIDAD = Configuracion.getCantidadPasajeros();
    private final List<Colectivo> colectivos;
    private final Map<Colectivo, Integer> posiciones = new HashMap<>();
    private final VistaPorConsola vista;
    private final Map<Colectivo, Integer> vueltas = new HashMap<>();
    private final int MAX_VUELTAS = Configuracion.getMaxVueltas();
    private final Map<Colectivo, Map<Integer, Set<Parada>>> paradasRestantesPorColectivo = new HashMap<>();

    public Simulador(List<Colectivo> colectivos, VistaPorConsola vista) {
        this.colectivos = colectivos;
        this.vista = vista;
        inicializarColectivos();
    }

    private void inicializarColectivos() {
        for (Colectivo c : colectivos) {
            posiciones.put(c, 0);
            vueltas.put(c, 0);
            precalcularParadasRestantes(c); //?????
        }
    }

    private void precalcularParadasRestantes(Colectivo colectivo) {
        List<Parada> paradas = colectivo.getLinea().getParadas();
        Map<Integer, Set<Parada>> porPosicion = new HashMap<>();
        for (int i = 0; i < paradas.size(); i++) {
            porPosicion.put(i, new HashSet<>(paradas.subList(i + 1, paradas.size())));
        }
        paradasRestantesPorColectivo.put(colectivo, porPosicion);
    }

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

    private void mostrarEventosPasajeros(List<Pasajero> bajaron, List<Pasajero> subieron) {
        for (Pasajero p : bajaron) {
            vista.mostrarPasajeroBajo(p);
        }
        for (Pasajero p : subieron) {
            vista.mostrarPasajeroSubio(p);
        }
    }

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