package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Linea;
import domain.Parada;

public class CargaDeDatos { 
    
    public static Map<Integer, Parada> cargarParadas(String archivo) {
        Map<Integer, Parada> paradasMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String lineaTexto;
            while ((lineaTexto = br.readLine()) != null) {
                if (lineaTexto.trim().isEmpty() || lineaTexto.startsWith("#")) continue;

                String[] partes = lineaTexto.split(";");
                if (partes.length >= 2) {
                    int id = Integer.parseInt(partes[0].trim());
                    String direccion = partes[1].trim();
                    Parada parada = new Parada(id, direccion);
                    paradasMap.put(id, parada);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar paradas desde archivo: " + archivo, e);
        }

        return paradasMap;
    }

    public static List<Linea> cargarLineas(String archivo, Map<Integer, Parada> paradas) {
        List<Linea> lineas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String lineaTexto;
            while ((lineaTexto = br.readLine()) != null) {
                if (lineaTexto.trim().isEmpty() || lineaTexto.startsWith("#")) continue;

                String[] partes = lineaTexto.split(";");
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
            throw new RuntimeException("Error al cargar líneas desde archivo: " + archivo, e);
        }
        return lineas;
    }

}