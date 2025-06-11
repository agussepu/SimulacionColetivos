package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracion {
    private static final String ARCHIVO_CONFIG = "data/config.properties";
    private static final Properties properties = new Properties();
    private static final String LINEA_KEY = "linea";
    private static final String PARADA_KEY = "parada";
    private static final String CANTIDAD_PASAJEROS_KEY = "cantidadPasajeros";
    private static final String MAX_PASAJEROS_POR_PARADA_KEY = "maxPasajerosPorParada";


    static {
        try (FileInputStream fis = new FileInputStream(ARCHIVO_CONFIG)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("[!] Error al cargar el archivo de configuración: " + ARCHIVO_CONFIG);
        }
    }

    public static String getArchivoLineas() {
        String value = properties.getProperty(LINEA_KEY);
        if (value == null) {
            throw new IllegalStateException("Propiedad 'linea' no encontrada en el archivo de configuración.");
        }
        return value;
    }

    public static String getArchivoParadas() {
        String value = properties.getProperty(PARADA_KEY);
        if (value == null) {
            throw new IllegalStateException("Propiedad 'parada' no encontrada en el archivo de configuración.");
        }
        return value;
    }

    public static int getCantidadPasajeros() {
        String value = properties.getProperty(CANTIDAD_PASAJEROS_KEY);
        if (value == null) {
            throw new IllegalStateException("Propiedad 'cantidadPasajeros' no encontrada en el archivo de configuración.");
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Valor inválido para 'cantidadPasajeros': " + value);
        }
    }

    public static int getMaxPasajerosPorParada() {
        String value = properties.getProperty(MAX_PASAJEROS_POR_PARADA_KEY);
        if (value == null) {
            throw new IllegalStateException("Propiedad 'maxPasajerosPorParada' no encontrada en el archivo de configuración.");
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Valor inválido para 'maxPasajerosPorParada': " + value);
        }
    }
}