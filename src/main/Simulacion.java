package main;

import java.util.Map;
import java.util.List;

import util.CargaDeColectivos;
import util.CargaDeDatos;
import util.CargaDePasajeros;
import util.Configuracion;
import util.Simulador;
import domain.*;

public class Simulacion {
    public static void main(String[] args) {
        // 1) Cargo las lineas y las paradas
        Map<Integer,Parada> paradas = CargaDeDatos.cargarParadas(Configuracion.getArchivoParadas());
        List<Linea> lineas = CargaDeDatos.cargarLineas(Configuracion.getArchivoLineas(), paradas);
    
        // 2) Genero colectivos y pasajeros
        List<Colectivo> colectivos = CargaDeColectivos.generarColectivos(lineas);
        CargaDePasajeros.generarPasajeros(lineas);
        
        // 3) Corro la simulacion
        Simulador.ejecutar(colectivos);

    }
}
