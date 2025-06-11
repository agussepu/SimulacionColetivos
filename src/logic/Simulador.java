package logic;

import config.Configuracion;
import domain.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import presentacion.VistaPorConsola;

/**
 * Clase principal encargada de ejecutar la simulación del recorrido de colectivos.
 * Gestiona el avance de los colectivos por sus respectivas líneas, el ascenso y descenso de pasajeros,
 * y comunica los eventos relevantes a la vista de presentación.
 */
public class Simulador {
    /** Capacidad máxima de pasajeros por colectivo, obtenida de la configuración. */
    private final int MAX_CAPACIDAD = Configuracion.getCantidadPasajeros();
    /** Lista de colectivos que participan en la simulación. */
    private final List<Colectivo> colectivos;
    /** Mapa que asocia cada colectivo con su posición actual en la línea (índice de parada). */
    private final Map<Colectivo, Integer> posiciones = new HashMap<>();
    /** Vista utilizada para mostrar los eventos de la simulación. */
    private final VistaPorConsola vista;

    /**
     * Crea un simulador con la lista de colectivos y la vista especificada.
     * @param colectivos Lista de colectivos a simular.
     * @param vista Instancia de la vista para mostrar información al usuario.
     */
    public Simulador(List<Colectivo> colectivos, VistaPorConsola vista) {
        this.colectivos = colectivos;
        this.vista = vista;
        colectivos.forEach(c -> posiciones.put(c, 0));
    }

    /**
     * Ejecuta la simulación completa del recorrido de los colectivos.
     * En cada parada, gestiona el ascenso y descenso de pasajeros, actualiza posiciones
     * y comunica los eventos a la vista.
     */
    public void ejecutar() {
        boolean enCurso = true;
        int numeroParada = 1;

        while (enCurso) {
            vista.mostrarInicioParada(numeroParada);
            enCurso = false;

            for (Colectivo colectivo : colectivos) {
                int pos = posiciones.get(colectivo);
                List<Parada> paradas = colectivo.getLinea().getParadas();

                if (pos < paradas.size()) {
                    Parada actual = paradas.get(pos);
                    vista.mostrarLlegadaColectivo(colectivo, actual);

                    List<Pasajero> bajaron = colectivo.bajarPasajerosEn(actual);
                    List<Pasajero> subieron = colectivo.subirPasajerosDesdeParada(
                        actual,
                        new HashSet<>(paradas.subList(pos + 1, paradas.size())),
                        MAX_CAPACIDAD
                    );

                    bajaron.forEach(p -> vista.mostrarPasajeroBajo(p));
                    subieron.forEach(p -> vista.mostrarPasajeroSubio(p));

                    vista.mostrarEstadoColectivo(colectivo, bajaron.size(), subieron.size());

                    posiciones.put(colectivo, pos + 1);
                    enCurso = true;
                } else {
                    vista.mostrarFinRecorrido(colectivo);
                }
            }

            numeroParada++;
        }

        vista.mostrarFinSimulacion();
    }
}