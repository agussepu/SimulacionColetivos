package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracion {
    private static final String ARCHIVO_CONFIG = "data/config.properties";
    private static Properties properties = new Properties();

    // Carga única al iniciar
    static {
        try (FileInputStream fis = new FileInputStream(ARCHIVO_CONFIG)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("[!] Error al cargar el archivo de configuración: " + ARCHIVO_CONFIG);
            e.printStackTrace();
        }
    }

    // Métodos de acceso a los parámetros
    public static String getArchivoLineas() {
        return properties.getProperty("linea");
    }

    public static String getArchivoParadas() {
        return properties.getProperty("parada");
    }

    public static int getCantidadPasajeros() {
        return Integer.parseInt(properties.getProperty("cantidadPasajeros"));
        // Esto se hace ya que en caso que no tenga valor la cantidad de pasajeros le asigna uno por defecto
        //return Integer.parseInt(properties.getProperty("cantidadPasajeros", "0"));
    }
}

