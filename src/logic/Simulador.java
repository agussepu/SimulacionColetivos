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
    /** Mapa que lleva la cuenta de las vueltas realizadas por cada colectivo. */
    private final Map<Colectivo, Integer> vueltas = new HashMap<>();
    private final int MAX_VUELTAS = Configuracion.getMaxVueltas();
    /**
     * Crea un simulador con la lista de colectivos y la vista especificada.
     * @param colectivos Lista de colectivos a simular.
     * @param vista Instancia de la vista para mostrar información al usuario.
     */
    public Simulador(List<Colectivo> colectivos, VistaPorConsola vista) {
        this.colectivos = colectivos;
        this.vista = vista;
        colectivos.forEach(c -> {
            posiciones.put(c, 0);
            vueltas.put(c, 0);
        });
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

                if (vueltas.get(colectivo) < MAX_VUELTAS) {
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
                        colectivo.registrarOcupacionTramo();

                        // NUEVO: Mostrar advertencia si el colectivo está lleno y cuantos pasajeros quedan esperando
                        if (colectivo.getCantidadPasajeros() == MAX_CAPACIDAD) {
                            // Contar cuántos pasajeros esperan con destino válido
                            long esperando = actual.getPasajerosEsperando().stream()
                                .filter(p -> paradas.subList(pos + 1, paradas.size()).contains(p.getDestino()))
                                .count();
                            if (esperando > 0) {
                                vista.mostrarColectivoLlenoYPasajerosEsperando(colectivo, actual, (int) esperando);
                            }
                        }

                        posiciones.put(colectivo, pos + 1);
                        enCurso = true;
                    } else {
                        vista.mostrarFinRecorrido(colectivo);
                        int vueltasActuales = vueltas.get(colectivo) + 1;
                        vueltas.put(colectivo, vueltasActuales);
                        if (vueltasActuales < MAX_VUELTAS) {
                            posiciones.put(colectivo, 0); // Reinicia el recorrido del colectivo
                            enCurso = true; // Permite que siga la simulación
                        }
                    }
                }
            }

            numeroParada++;
        }
        
        vista.mostrarFinSimulacion();
        
        // Asignar calificación 1 a los pasajeros que nunca subieron (quedaron esperando)
        for (Colectivo colectivo : colectivos) {
            List<Parada> paradas = colectivo.getLinea().getParadas();
            for (Parada parada : paradas) {
                for (Pasajero p : parada.getPasajerosEsperando()) {
                    p.setCalificacion(1);
                }
            }
        }

        // Calcular y mostrar el índice de satisfacción
        List<Pasajero> todos = util.AdministracionPasajeros.getTodosLosPasajeros();
        int suma = 0;
        for (Pasajero p : todos) {
            suma += p.getCalificacion();
        }
        double indice = (double) suma / (todos.size() * 5);
        vista.mostrarIndiceSatisfaccion(indice);

        for (Colectivo colectivo : colectivos) {
            List<Integer> ocupaciones = colectivo.getOcupacionPorTramo();
            double sumaOcupacion = 0;
            for (int ocupacion : ocupaciones) {
                sumaOcupacion += (double) ocupacion / MAX_CAPACIDAD;
            }
            double promedio = ocupaciones.isEmpty() ? 0 : sumaOcupacion / ocupaciones.size();
            vista.mostrarOcupacionPromedio(colectivo, promedio);
        }
    }
}