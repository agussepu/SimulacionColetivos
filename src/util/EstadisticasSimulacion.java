package util;

import domain.Colectivo;
import domain.Pasajero;
import java.util.List;
import presentacion.VistaPorConsola;

public class EstadisticasSimulacion {

    public static void mostrarIndiceSatisfaccion(List<Pasajero> todos, VistaPorConsola vista) {
        int suma = 0;
        for (Pasajero p : todos) {
            suma += p.getCalificacion();
        }
        double indice = (double) suma / (todos.size() * 5);
        vista.mostrarIndiceSatisfaccion(indice);
    }

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