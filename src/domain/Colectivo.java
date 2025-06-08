package domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Colectivo {
    private final int id;
    private final Linea linea;
    private final List<Pasajero> pasajeros = new ArrayList<>();

    public Colectivo(int id, Linea linea) {
        this.id = id;
        this.linea = linea;
    }

    public List<Pasajero> subirPasajerosDesdeParada(Parada parada, Set<Parada> destinosDisponibles, int maxCapacidad) {
        int espacioDisponible = maxCapacidad - pasajeros.size();
        List<Pasajero> subieron = parada.seleccionarPasajerosParaSubir(destinosDisponibles, espacioDisponible);
        pasajeros.addAll(subieron);
        return subieron;
    }

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

    public int getCantidadPasajeros() {
        return pasajeros.size();
    }

    public int getId() {
        return id;
    }

    public Linea getLinea() {
        return linea;
    }

    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }
}



