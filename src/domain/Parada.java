package domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;

public class Parada {
    private final String direccion;
    private final int id;
    private final List<Pasajero> pasajerosEsperando = new ArrayList<>();

    public Parada(int id, String direccion) {
        this.id = id;
        this.direccion = direccion;
    }

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

    public String getDireccion() {
        return direccion;
    }

    public List<Pasajero> getPasajerosEsperando() {
        return pasajerosEsperando;
    }

    public void agregarPasajero(Pasajero p) {
        pasajerosEsperando.add(p);
    }

    public int getId(){
        return id;
    }
}
