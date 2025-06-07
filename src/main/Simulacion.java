package main;

import domain.*;
import util.*;

import java.util.Map;
import config.Configuracion;
import java.util.List;

public class Simulacion {
    public static void main(String[] args) {
        // 1) Lineas y Paradas
        Datos datos = new Datos(Configuracion.getArchivoParadas(), Configuracion.getArchivoLineas());
        
        Map<Integer,Parada> paradas = datos.cargarParadas();
        List<Linea> lineas = datos.cargarLineas(paradas);
    
        // 2) Colectivos y Pasajeros
        List<Colectivo> colectivos = Colectivos.generarColectivos(lineas);
        Pasajeros.generarPasajeros(lineas);
        
        // 3) Simulacion
        Simulador simulador = new Simulador(colectivos, Configuracion.getCantidadPasajeros());
        simulador.ejecutar();
    }
}
