package domain;

import config.Configuracion;

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

    public void calificarAlSubir(int posicion, int maxCapacidad) {
        int cantidadAsientos = Configuracion.getCantidadAsientos();
        switch (colectivosEsperados) {
            case 0 -> {
                if (posicion < cantidadAsientos) {
                    setCalificacion(5); // Consiguió asiento
                } else {
                    setCalificacion(4); // Viajó parado
                }
            }
            case 1 -> setCalificacion(3); // Esperó un colectivo para subir
            default -> setCalificacion(2); // Esperó más de dos colectivos para subir
        }
    }

    /**
     * Decide si el pasajero quiere subir al colectivo.
     * Simplificado - la lógica de verificación del recorrido se maneja en Colectivo.
     */
    public boolean quiereSubirA(Colectivo colectivo, Parada parada) {
        // Lógica simple: siempre quiere subir si la línea va a su destino
        // La verificación de si el destino está en el recorrido futuro se hace en Colectivo
        return colectivo.getLinea().getParadas().contains(destino);
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