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

    public String getDireccion() {
        return direccion;
    }

    public int getId() {
        return id;
    }

    public List<Pasajero> getPasajerosEsperando() {
        return pasajerosEsperando;
    }

    public void agregarPasajero(Pasajero p) {
        pasajerosEsperando.add(p);
    }

    public void quitarPasajero(Pasajero p) {
        pasajerosEsperando.remove(p);
    }
}