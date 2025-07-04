package domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
     * Permite que los pasajeros esperen en una parada suban al colectivo.
     * Se verifica si el colectivo tiene espacio y si los pasajeros quieren subir.
     *
     * @param parada Parada desde donde se suben los pasajeros.
     * @param posicionActual Posición actual del colectivo en el recorrido.
     * @param maxCapacidad Capacidad máxima del colectivo.
     * @return Lista de pasajeros que subieron al colectivo.
     */
    public List<Pasajero> subirPasajerosDesdeParada(Parada parada, int posicionActual, int maxCapacidad) {
        int espacioDisponible = maxCapacidad - pasajeros.size();
        List<Pasajero> subieron = new ArrayList<>();
        Iterator<Pasajero> it = parada.getPasajerosEsperando().iterator();
        while (it.hasNext() && subieron.size() < espacioDisponible) {
            Pasajero p = it.next();
            if (puedeSubir(p, parada, posicionActual)) {
                subieron.add(p);
                it.remove();
                p.calificarAlSubir(pasajeros.size() + subieron.size() - 1, maxCapacidad);
            }
        }
        pasajeros.addAll(subieron);
        return subieron;
    }

    public boolean puedeSubir(Pasajero pasajero, Parada parada, int posicionActual) {
        return destinoEstaEnRecorridoFuturo(pasajero.getDestino(), posicionActual)
            && pasajero.quiereSubirA(this, parada);
    }

    /**
     * Verifica si el destino de un pasajero está en el recorrido futuro del colectivo.
     * Esto se hace comparando la posición actual del colectivo con el índice de la parada destino.
     *
     * @param destino Parada de destino del pasajero.
     * @param posicionActual Posición actual del colectivo en el recorrido.
     * @return true si el destino está en el recorrido futuro, false en caso contrario.
     */
    private boolean destinoEstaEnRecorridoFuturo(Parada destino, int posicionActual) {
        Map<Parada, Integer> indiceParadas = linea.getMapaIndicesParadas();
        Integer indiceDestino = indiceParadas.get(destino);
        return indiceDestino != null && indiceDestino > posicionActual;
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