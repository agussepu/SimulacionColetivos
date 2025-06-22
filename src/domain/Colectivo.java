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
    private final List<Integer> ocupacionPorTramo = new ArrayList<>();

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

        for (int i = 0; i < subieron.size(); i++) {
            Pasajero p = subieron.get(i);
            switch (p.getColectivosEsperados()) {
                case 0 -> { // Subio en el primer coletivo que paso
                    if (pasajeros.size() + i < maxCapacidad / 2) {
                        p.setCalificacion(5); // Consiguió asiento
                    } else {
                        p.setCalificacion(4); // Viajó parado
                    }
                }
                case 1 -> p.setCalificacion(3); // Espero un colectivo para subir 
                default -> p.setCalificacion(2); // Esperó más de dos colectivos para subir
                // en caso de no subir a ningun colectivo ya tienen asignado por defecto 1
            }
        }

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

    /**
     * Registra la cantidad de pasajeros a bordo en el tramo actual del recorrido.
     * Este método debe llamarse después de cada parada para llevar un historial de la ocupación del colectivo.
     */
    public void registrarOcupacionTramo() {
        ocupacionPorTramo.add(pasajeros.size());
    }

    
    // Getters

    /**
     * Devuelve la lista de ocupación registrada por tramo durante el recorrido del colectivo.
     * Cada elemento de la lista representa la cantidad de pasajeros a bordo después de cada parada.
     *
     * @return Lista de ocupación por tramo.
     */
    public List<Integer> getOcupacionPorTramo() {
        return ocupacionPorTramo;
    }
    
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