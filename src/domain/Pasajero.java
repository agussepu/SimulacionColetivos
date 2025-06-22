package domain;

/**
 * Representa un pasajero que viaja en un colectivo.
 * Cada pasajero tiene un identificador único y una parada de destino.
 */
public class Pasajero {
    private final int id;
    private final Parada destino;
    private int colectivosEsperados = 0;
    private int calificacion = 1; // Por defecto, 1 (muy malo)

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
     * Incrementa en uno la cantidad de colectivos que el pasajero ha esperado.
     */
    public void incrementarEspera() { 
        colectivosEsperados++; 
    }
    
    /**
     * Devuelve la cantidad de colectivos que el pasajero ha esperado antes de subir.
     * @return Cantidad de colectivos esperados.
     */
    public int getColectivosEsperados() { 
        return colectivosEsperados; 
    }
    
    /**
     * Asigna la calificación de satisfacción al pasajero.
     * @param calificacion Valor de calificación (1 a 5).
     */
    public void setCalificacion(int calificacion) { 
        this.calificacion = calificacion; 
    }
    
    /**
     * Devuelve la calificación de satisfacción del pasajero.
     * @return Calificación asignada.
     */
    public int getCalificacion() { 
        return calificacion; 
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