package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Linea {
    private final String codigo;
    private final List<Parada> paradas;
    private final Map<Parada, Integer> mapaIndicesParadas; 

    /**
     * Crea una nueva línea con el código especificado.
     * @param codigo Código identificador de la línea.
     */
    public Linea(String codigo) {
        this.codigo = codigo;
        this.paradas = new ArrayList<>();
        this.mapaIndicesParadas = new HashMap<>();
    }


    /**
     * Agrega una parada a la línea y actualiza el mapa de índices.
     * @param parada Parada a agregar.
     */
    private void actualizarMapaIndices() {
        mapaIndicesParadas.clear();
        for (int i = 0; i < paradas.size(); i++) {
            mapaIndicesParadas.put(paradas.get(i), i);
        }
    }

    /**
     * Obtiene el mapa de índices de paradas para búsquedas optimizadas.
     */
    public Map<Parada, Integer> getMapaIndicesParadas() {
        return mapaIndicesParadas;
    }

    /**
     * Verifica si una parada está después de cierta posición en el recorrido.
     */
    public boolean paradaEstaEnPosicionFutura(Parada parada, int posicionActual) {
        Integer indiceParada = mapaIndicesParadas.get(parada);
        return indiceParada != null && indiceParada > posicionActual;
    }

    /**
     * Agrega una parada a la línea si no está ya incluida y actualiza el mapa de índices.
     * @param parada Parada a agregar.
     */
    public void agregarParada(Parada parada) {
        if (!paradas.contains(parada)) {
            paradas.add(parada);
            actualizarMapaIndices(); // Actualiza el mapa cada vez que se agrega una parada
        }
    }

    /**
     * Devuelve la lista de paradas de la línea.
     * @return Lista de paradas.
     */
    public List<Parada> getParadas() {
        return paradas;
    }

    /**
     * Devuelve el código identificador de la línea.
     * @return Código de la línea.
     */
    public String getCodigo() {
        return codigo;
    }
}