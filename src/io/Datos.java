package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Linea;
import domain.Parada;

public class Datos { 
    private final String archivoParadas;
    private final String archivoLineas;
    private static final String SEPARADOR = ";";
    private static final String COMENTARIO = "#";


    public Datos(String archivoParadas, String archivoLineas) {
        this.archivoParadas = archivoParadas;
        this.archivoLineas = archivoLineas;
    }
    
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

    public List<Linea> cargarLineas(Map<Integer, Parada> paradas) {
        List<Linea> lineas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoLineas))) {
            String lineaTexto;
            while ((lineaTexto = br.readLine()) != null) {
                if (lineaTexto.trim().isEmpty() || lineaTexto.startsWith(COMENTARIO)) continue;

                String[] partes = lineaTexto.split(SEPARADOR);
                String codigo = partes[0].trim();
                Linea linea = new Linea(codigo);

                for (int i = 1; i < partes.length; i++) {
                    String idStr = partes[i].trim();
                    if (!idStr.isEmpty()) {
                        int idParada = Integer.parseInt(idStr);
                        Parada parada = paradas.get(idParada);
                        if (parada != null) {
                            linea.agregarParada(parada);
                        } else {
                            System.err.println("⚠️ Parada no encontrada para ID: " + idParada);
                        }
                    }
                }

                lineas.add(linea);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar líneas desde archivo: " + archivoLineas, e);
        }
        return lineas;
    }

}