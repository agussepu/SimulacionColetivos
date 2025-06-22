package io;

import domain.Linea;
import domain.Parada;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import presentacion.VistaPorConsola;

/**
 * Clase encargada de la carga de datos desde archivos de texto para la simulación.
 * Permite cargar las paradas y líneas de colectivos a partir de archivos configurados.
 */
public class Datos { 
    private final String archivoParadas;
    private final String archivoLineas;
    private static final String SEPARADOR = ";";
    private static final String COMENTARIO = "#";
    private final VistaPorConsola vista;

    /**
     * Crea una instancia de Datos con los archivos de paradas y líneas especificados.
     * @param archivoParadas Ruta del archivo de paradas.
     * @param archivoLineas Ruta del archivo de líneas.
     * @param vista Instancia de la vista para mostrar advertencias o mensajes.
     */
    public Datos(String archivoParadas, String archivoLineas, VistaPorConsola vista) {
        this.archivoParadas = archivoParadas;
        this.archivoLineas = archivoLineas;
        this.vista = vista;
    }
    
    /**
     * Carga las paradas desde el archivo configurado.
     * @return Mapa de paradas, donde la clave es el ID de la parada y el valor es el objeto Parada.
     * @throws RuntimeException si ocurre un error de lectura del archivo.
     */
    public Map<Integer, Parada> cargarParadas() {
        Map<Integer, Parada> paradasMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoParadas))) {
            String lineaTexto;
            while ((lineaTexto = br.readLine()) != null) {
                Parada parada = parsearParada(lineaTexto);
                if (parada != null) {
                    paradasMap.put(parada.getId(), parada);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar paradas desde archivo: " + archivoParadas, e);
        }
        return paradasMap;
    }

    /**
     * Parsea una línea de texto del archivo de paradas y crea un objeto Parada.
     * @param lineaTexto Línea de texto a parsear.
     * @return Objeto Parada si la línea es válida, o null si es inválida o un comentario.
     */
    private Parada parsearParada(String lineaTexto) {
        if (lineaTexto.trim().isEmpty() || lineaTexto.startsWith(COMENTARIO)) return null;

        String[] partes = lineaTexto.split(SEPARADOR);
        if (partes.length >= 2) {
            int id = Integer.parseInt(partes[0].trim());
            String direccion = partes[1].trim();
            return new Parada(id, direccion);
        }
        return null;
    }

    /**
     * Carga las líneas de colectivos desde el archivo configurado, asociando las paradas previamente cargadas.
     * Si una parada referenciada en el archivo de líneas no existe, se muestra una advertencia por consola.
     * @param paradas Mapa de paradas disponibles, indexadas por su ID.
     * @return Lista de objetos Linea cargados desde el archivo.
     * @throws RuntimeException si ocurre un error de lectura del archivo.
     */
    public List<Linea> cargarLineas(Map<Integer, Parada> paradas) {
        List<Linea> lineas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoLineas))) {
            String lineaTexto;
            while ((lineaTexto = br.readLine()) != null) {
                Linea linea = parsearLinea(lineaTexto, paradas);
                if (linea != null) {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar líneas desde archivo: " + archivoLineas, e);
        }
        return lineas;
    }

    /**
     * Parsea una línea de texto del archivo de líneas y crea un objeto Linea.
     * Si alguna parada referenciada no existe, muestra una advertencia y la omite.
     * @param lineaTexto Línea de texto a parsear.
     * @param paradas Mapa de paradas disponibles.
     * @return Objeto Linea si la línea es válida, o null si es inválida o un comentario.
     */
    private Linea parsearLinea(String lineaTexto, Map<Integer, Parada> paradas) {
        if (lineaTexto.trim().isEmpty() || lineaTexto.startsWith(COMENTARIO)) return null;

        String[] partes = lineaTexto.split(SEPARADOR);
        if (partes.length < 2) return null;

        String codigo = partes[0].trim();
        Linea linea = new Linea(codigo);

        for (int i = 1; i < partes.length; i++) {
            String idStr = partes[i].trim();
            if (!idStr.isEmpty()) {
                try {
                    int idParada = Integer.parseInt(idStr);
                    Parada parada = paradas.get(idParada);
                    if (parada != null) {
                        linea.agregarParada(parada);
                    } else {
                        vista.mostrarAdvertenciaParadaNoEncontrada(idParada);
                    }
                } catch (NumberFormatException ex) {
                    vista.mostrarAdvertenciaParadaNoValida(idStr);
                }
            }
        }
        return linea;
    }

}