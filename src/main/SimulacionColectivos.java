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

        // Configurar la salida de la simulación a un archivo o consola
        VistaPorConsola vista = VistaPorConsola.crearConArchivo(Configuracion.getArchivoSalidaSimulacion());
    
        // Cargar archivos de paradas y líneas usando la configuración
        Datos datos = new Datos(Configuracion.getArchivoParadas(), Configuracion.getArchivoLineas(), vista);
        
        // Cargar las paradas desde el archivo y almacenarlas en un mapa (id -> Parada)
        Map<Integer,Parada> paradas = datos.cargarParadas();
        
        // Cargar las líneas desde el archivo y asociarles las paradas correspondientes
        List<Linea> lineas = datos.cargarLineas(paradas);
    
        // Generar los colectivos para cada línea
        List<Colectivo> colectivos = AdministracionColectivos.generarColectivos(lineas);
        
        // Generar los pasajeros y asignarlos a las paradas
        AdministracionPasajeros.generarPasajeros(lineas);
        
        // Crear el simulador y ejecutar la simulación
        Simulador simulador = new Simulador(colectivos, vista);
        simulador.ejecutar();
        
        // Cerrar el archivo de salida si se está utilizando
        vista.cerrarArchivo();
    }
}