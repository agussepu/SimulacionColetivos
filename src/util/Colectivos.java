package util;

import domain.*;

import java.util.ArrayList;
import java.util.List;

public class Colectivos {
    public static List<Colectivo> generarColectivos(List<Linea> lineas){
        List<Colectivo> colectivos = new ArrayList<>();
        int id = 0;

        for(Linea l: lineas) {
            Colectivo colectivo = new Colectivo(id++, l);
            colectivos.add(colectivo);
        }

        return colectivos;
    }
}
