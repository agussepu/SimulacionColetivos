package main;

import config.Configuracion;
import domain.*;
import io.Datos;
import java.util.List;
import java.util.Map;
import logic.Simulador;
import presentacion.VistaPorConsola;
import util.*;

public class SimulacionColectivos {
    public static void main(String[] args) {
        VistaPorConsola vista = new VistaPorConsola();
        
        Datos datos = new Datos(Configuracion.getArchivoParadas(), Configuracion.getArchivoLineas(), vista);
        Map<Integer,Parada> paradas = datos.cargarParadas();
        List<Linea> lineas = datos.cargarLineas(paradas);
    
        List<Colectivo> colectivos = AdministracionColectivos.generarColectivos(lineas);
        AdministracionPasajeros.generarPasajeros(lineas);
        
        Simulador simulador = new Simulador(colectivos, vista);
        simulador.ejecutar();
    }
}
