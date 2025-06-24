package util;

import domain.Colectivo;
import domain.Pasajero;
import java.util.List;
import presentacion.SimulacionOutput;

public class EstadisticasSimulacion {

    /**
     * Muestra las estadísticas finales de la simulación, incluyendo el índice de satisfacción
     * y la ocupación promedio por colectivo.
     *
     * @param colectivos Lista de colectivos que participaron en la simulación.
     * @param maxCapacidad Capacidad máxima de cada colectivo.
     * @param vista Instancia de VistaPorConsola para mostrar los resultados.
     */
    public static void mostrarEstadisticasFinales(List<Colectivo> colectivos, int maxCapacidad, SimulacionOutput vista) {
        vista.mostrarFinSimulacion();
        mostrarIndiceSatisfaccion(util.AdministracionPasajeros.getTodosLosPasajeros(), vista);
        mostrarOcupacionPromedioPorColectivo(colectivos, maxCapacidad, vista);
    }

    /**
     * Calcula y muestra el índice de satisfacción promedio de los pasajeros.
     * El índice se calcula como la suma de las calificaciones dividida por el máximo posible.
     *
     * @param todos Lista de todos los pasajeros.
     * @param vista Instancia de VistaPorConsola para mostrar el resultado.
     */
    public static void mostrarIndiceSatisfaccion(List<Pasajero> todos, SimulacionOutput vista) {
        int suma = 0;
        for (Pasajero p : todos) {
            suma += p.getCalificacion();
        }
        double indice = (double) suma / (todos.size() * 5);
        vista.mostrarIndiceSatisfaccion(indice);
    }

    /**
     * Calcula y muestra la ocupación promedio de cada colectivo durante la simulación.
     * La ocupación promedio se calcula como el promedio de la ocupación relativa por tramo.
     *
     * @param colectivos Lista de colectivos simulados.
     * @param maxCapacidad Capacidad máxima de cada colectivo.
     * @param vista Instancia de VistaPorConsola para mostrar los resultados.
     */
    public static void mostrarOcupacionPromedioPorColectivo(List<Colectivo> colectivos, int maxCapacidad, SimulacionOutput vista) {
        for (Colectivo colectivo : colectivos) {
            List<Integer> ocupaciones = colectivo.getOcupacionPorTramo();
            double sumaOcupacion = 0;
            for (int ocupacion : ocupaciones) {
                sumaOcupacion += (double) ocupacion / maxCapacidad;
            }
            double promedio = ocupaciones.isEmpty() ? 0 : sumaOcupacion / ocupaciones.size();
            vista.mostrarOcupacionPromedio(colectivo, promedio);
        }
    }
}