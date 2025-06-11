package domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Representa una parada de colectivo, identificada por un id y una dirección.
 * Gestiona los pasajeros que esperan en la parada.
 */
public class Parada {
    private final String direccion;
    private final int id;
    private final List<Pasajero> pasajerosEsperando = new ArrayList<>();

    /**
     * Crea una nueva parada con el id y la dirección especificados.
     * @param id Identificador único de la parada.
     * @param direccion Dirección de la parada.
     */
    public Parada(int id, String direccion) {
        this.id = id;
        this.direccion = direccion;
    }

    /**
     * Selecciona los pasajeros que pueden subir al colectivo desde la parada,
     * según los destinos válidos y el espacio disponible.
     * Los pasajeros seleccionados se eliminan de la lista de espera.
     *
     * @param destinosValidos Conjunto de paradas válidas como destino.
     * @param espacioDisponible Cantidad de lugares disponibles en el colectivo.
     * @return Lista de pasajeros que suben al colectivo.
     */
    public List<Pasajero> seleccionarPasajerosParaSubir(Set<Parada> destinosValidos, int espacioDisponible) {
        List<Pasajero> seleccionados = new ArrayList<>();
        Iterator<Pasajero> it = pasajerosEsperando.iterator();

        while (it.hasNext() && seleccionados.size() < espacioDisponible) {
            Pasajero p = it.next();
            if (destinosValidos.contains(p.getDestino())) {
                seleccionados.add(p);
                it.remove();
            }
        }

        return seleccionados;
    }

    // Getters

    /**
     * Devuelve la dirección de la parada.
     * @return Dirección de la parada.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Devuelve la lista de pasajeros que esperan en la parada.
     * @return Lista de pasajeros esperando.
     */
    public List<Pasajero> getPasajerosEsperando() {
        return pasajerosEsperando;
    }

    /**
     * Agrega un pasajero a la lista de espera de la parada.
     * @param p Pasajero a agregar.
     */
    public void agregarPasajero(Pasajero p) {
        pasajerosEsperando.add(p);
    }

    /**
     * Devuelve el identificador único de la parada.
     * @return ID de la parada.
     */
    public int getId(){
        return id;
    }
}