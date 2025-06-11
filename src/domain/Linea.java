package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una línea de colectivo, identificada por un código y compuesta por una lista de paradas.
 */
public class Linea {
    private final String codigo;
    private final List<Parada> paradas;

    /**
     * Crea una nueva línea con el código especificado.
     * @param codigo Código identificador de la línea.
     */
    public Linea(String codigo) {
        this.codigo = codigo;
        this.paradas = new ArrayList<>();
    }

    /**
     * Agrega una parada a la línea si no está ya incluida.
     * @param parada Parada a agregar.
     */
    public void agregarParada(Parada parada) {
        if (!paradas.contains(parada)) {
            paradas.add(parada);
        }
    }

    // Getters

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