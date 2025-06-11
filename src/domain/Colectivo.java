package domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Representa un colectivo que realiza el recorrido de una línea determinada.
 * Gestiona los pasajeros a bordo y permite subir y bajar pasajeros en las paradas.
 */
public class Colectivo {
    private final int id;
    private final Linea linea;
    private final List<Pasajero> pasajeros = new ArrayList<>();

    /**
     * Crea un nuevo colectivo asociado a una línea.
     * @param id Identificador único del colectivo.
     * @param linea Línea que recorre el colectivo.
     */
    public Colectivo(int id, Linea linea) {
        this.id = id;
        this.linea = linea;
    }

    /**
     * Permite que los pasajeros suban al colectivo desde una parada, según los destinos disponibles y la capacidad máxima.
     * @param parada Parada actual.
     * @param destinosDisponibles Conjunto de paradas válidas como destino.
     * @param maxCapacidad Capacidad máxima del colectivo.
     * @return Lista de pasajeros que subieron.
     */
    public List<Pasajero> subirPasajerosDesdeParada(Parada parada, Set<Parada> destinosDisponibles, int maxCapacidad) {
        int espacioDisponible = maxCapacidad - pasajeros.size();
        List<Pasajero> subieron = parada.seleccionarPasajerosParaSubir(destinosDisponibles, espacioDisponible);
        pasajeros.addAll(subieron);
        return subieron;
    }

    /**
     * Permite que los pasajeros cuyo destino es la parada actual bajen del colectivo.
     * @param parada Parada actual.
     * @return Lista de pasajeros que bajaron.
     */
    public List<Pasajero> bajarPasajerosEn(Parada parada) {
        List<Pasajero> bajan = new ArrayList<>();
        Iterator<Pasajero> it = pasajeros.iterator();
        while (it.hasNext()) {
            Pasajero p = it.next();
            if (p.getDestino().equals(parada)) {
                bajan.add(p);
                it.remove();
            }
        }
        return bajan;
    }

    // Getters
    
    /**
     * Obtiene la cantidad de pasajeros actualmente a bordo del colectivo.
     * @return Cantidad de pasajeros.
     */
    public int getCantidadPasajeros() {
        return pasajeros.size();
    }

    /**
     * Obtiene el identificador único del colectivo.
     * @return ID del colectivo.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la línea que recorre el colectivo.
     * @return Línea asociada.
     */
    public Linea getLinea() {
        return linea;
    }

    /**
     * Obtiene la lista de pasajeros actualmente a bordo.
     * @return Lista de pasajeros.
     */
    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }
}