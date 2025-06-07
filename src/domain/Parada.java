package domain;

import java.util.List;
import java.util.ArrayList;

public class Parada {
    private int id;
    private String direccion;
    private List<Pasajero> pasajeros;

    public Parada(int id, String direccion) {
        this.id = id;
        this.direccion = direccion;
        this.pasajeros = new ArrayList<>();
    }

    public void agregarPasajero(Pasajero pasajero) {
        pasajeros.add(pasajero);
    }

    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public int getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }
}

