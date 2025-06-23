package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase utilitaria para la gestión de la configuración del sistema.
 * Carga los parámetros desde un archivo properties externo y provee métodos
 * estáticos para acceder a los valores de configuración necesarios en la simulación.
 */
public class Configuracion {
    private static final String ARCHIVO_CONFIG = "data/config.properties";
    private static final Properties properties = new Properties();
    private static final String LINEA_KEY = "linea";
    private static final String PARADA_KEY = "parada";
    private static final String CANTIDAD_PASAJEROS_KEY = "cantidadPasajeros";
    private static final String MAX_PASAJEROS_POR_PARADA_KEY = "maxPasajerosPorParada";
    private static final String MAX_VUELTAS_KEY = "maxVueltas"; 
    private static final String ARCHIVO_SALIDA_SIMULACION_KEY = "archivoSalidaSimulacion";

    // Bloque estático para cargar la configuración al iniciar la clase
    static {
        try (FileInputStream fis = new FileInputStream(ARCHIVO_CONFIG)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("[!] Error al cargar el archivo de configuración: " + ARCHIVO_CONFIG);
        }
    }

    /**
     * Obtiene la ruta del archivo de líneas desde la configuración.
     * @return Ruta del archivo de líneas.
     * @throws IllegalStateException si la propiedad no está definida.
     */
    public static String getArchivoLineas() {
        String value = properties.getProperty(LINEA_KEY);
        if (value == null) {
            throw new IllegalStateException("Propiedad 'linea' no encontrada en el archivo de configuración.");
        }
        return value;
    }

    /**
     * Obtiene la ruta del archivo de paradas desde la configuración.
     * @return Ruta del archivo de paradas.
     * @throws IllegalStateException si la propiedad no está definida.
     */
    public static String getArchivoParadas() {
        String value = properties.getProperty(PARADA_KEY);
        if (value == null) {
            throw new IllegalStateException("Propiedad 'parada' no encontrada en el archivo de configuración.");
        }
        return value;
    }

    /**
     * Obtiene la cantidad total de pasajeros a generar en la simulación.
     * @return Cantidad de pasajeros.
     * @throws IllegalStateException si la propiedad no está definida o es inválida.
     */
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

    /**
     * Obtiene la cantidad máxima de pasajeros que puede generar cada línea por parada.
     * @return Máximo de pasajeros por parada.
     * @throws IllegalStateException si la propiedad no está definida o es inválida.
     */
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

    /**
     * Obtiene la cantidad máxima de vueltas que puede realizar un colectivo en la simulación.
     * El valor se obtiene desde el archivo de configuración utilizando la clave correspondiente.
     * 
     * @return Cantidad máxima de vueltas por colectivo.
     * @throws NumberFormatException si el valor no es un número entero válido.
     * @throws NullPointerException si la propiedad no está definida en el archivo de configuración.
     */
    public static int getMaxVueltas() {
        String value = properties.getProperty(MAX_VUELTAS_KEY);
        if (value == null) {
            throw new IllegalStateException("Propiedad 'maxVueltas' no encontrada en el archivo de configuración.");
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Valor inválido para 'maxVueltas': " + value);
        }
    }

    public static String getArchivoSalidaSimulacion() {
        String value = properties.getProperty(ARCHIVO_SALIDA_SIMULACION_KEY);
        if (value == null) {
            throw new IllegalStateException("Propiedad 'archivoSalidaSimulacion' no encontrada en el archivo de configuración.");
        }
        return value;
    }   
}