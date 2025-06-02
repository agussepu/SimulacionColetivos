package domain;

import java.util.List;
import java.util.ArrayList;


// Clase Colectivo
public class Colectivo {
    private int id;
    private Linea linea;
    private List<Pasajero> pasajeros;

    public Colectivo(int id, Linea linea) {
        this.id = id;
        this.linea = linea;
        this.pasajeros = new ArrayList<>();
    }

    public void subirPasajero(Pasajero pasajero) {
        pasajeros.add(pasajero);
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


