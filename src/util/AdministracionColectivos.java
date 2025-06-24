package util;

import domain.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilitaria para la administración y generación de colectivos.
 */
public class AdministracionColectivos {

    /**
     * Genera una lista de colectivos, uno por cada línea recibida.
     * Asigna un identificador único incremental a cada colectivo.
     *
     * @param lineas Lista de líneas para las cuales se deben crear colectivos.
     * @return Lista de colectivos generados, cada uno asociado a una línea.
     */
    public static List<Colectivo> generarColectivos(List<Linea> lineas){
        List<Colectivo> colectivos = new ArrayList<>();
        int id = 0;

        for(Linea linea: lineas) {
            Colectivo colectivo = new Colectivo(id++, linea);
            colectivos.add(colectivo);
        }

        return colectivos;
    }
}