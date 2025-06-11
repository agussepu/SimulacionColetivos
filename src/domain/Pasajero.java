package domain;

/**
 * Representa un pasajero que viaja en un colectivo.
 * Cada pasajero tiene un identificador único y una parada de destino.
 */
public class Pasajero {
    private final int id;
    private final Parada destino;

    /**
     * Crea un nuevo pasajero con el id y destino especificados.
     * @param id Identificador único del pasajero.
     * @param destino Parada de destino del pasajero.
     */
    public Pasajero(int id, Parada destino) {
        this.id = id;
        this.destino = destino;
    }

    /**
     * Devuelve el identificador único del pasajero.
     * @return ID del pasajero.
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve la parada de destino del pasajero.
     * @return Parada de destino.
     */
    public Parada getDestino() {
        return destino;
    }
}