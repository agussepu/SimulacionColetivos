package logic;

import config.Configuracion;
import domain.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import presentacion.VistaPorConsola;

public class Simulador {
    private final int MAX_CAPACIDAD = Configuracion.getCantidadPasajeros();
    private final List<Colectivo> colectivos;
    private final Map<Colectivo, Integer> posiciones = new HashMap<>();
    private final VistaPorConsola vista;

    public Simulador(List<Colectivo> colectivos, VistaPorConsola vista) {
        this.colectivos = colectivos;
        this.vista = vista;
        colectivos.forEach(c -> posiciones.put(c, 0));
    }

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
                    List<Pasajero> subieron = colectivo.subirPasajerosDesdeParada(actual, new HashSet<>(paradas.subList(pos + 1, paradas.size())), MAX_CAPACIDAD);

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