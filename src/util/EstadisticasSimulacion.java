package util;

import domain.Colectivo;
import domain.Pasajero;
import java.util.List;
import presentacion.VistaPorConsola;

public class EstadisticasSimulacion {

    /**
     * Calcula y muestra el índice de satisfacción promedio de los pasajeros.
     * El índice se calcula como la suma de las calificaciones dividida por el máximo posible.
     *
     * @param todos Lista de todos los pasajeros.
     * @param vista Instancia de VistaPorConsola para mostrar el resultado.
     */
    public static void mostrarIndiceSatisfaccion(List<Pasajero> todos, VistaPorConsola vista) {
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
    public static void mostrarOcupacionPromedioPorColectivo(List<Colectivo> colectivos, int maxCapacidad, VistaPorConsola vista) {
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