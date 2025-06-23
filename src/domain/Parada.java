package domain;

import java.util.ArrayList;
import java.util.List;

public class Parada {
    private final String direccion;
    private final int id;
    private final List<Pasajero> pasajerosEsperando = new ArrayList<>();

    public Parada(int id, String direccion) {
        this.id = id;
        this.direccion = direccion;
    }

    /**
     * Obtiene la dirección de la parada.
     * @return Dirección de la parada.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Obtiene el identificador único de la parada.
     * @return ID de la parada.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la lista de pasajeros que están esperando en esta parada.
     * @return Lista de pasajeros esperando.
     */
    public List<Pasajero> getPasajerosEsperando() {
        return pasajerosEsperando;
    }

    /**
     * Verifica si un pasajero está esperando en esta parada.
     * @param p Pasajero a verificar.
     * @return true si el pasajero está esperando, false en caso contrario.
     */
    public void agregarPasajero(Pasajero p) {
        pasajerosEsperando.add(p);
    }

    /**
     * Elimina un pasajero de la lista de espera en esta parada.
     * @param p Pasajero a eliminar.
     */
    public void quitarPasajero(Pasajero p) {
        pasajerosEsperando.remove(p);
    }
}