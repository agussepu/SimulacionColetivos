package domain;

import java.util.ArrayList;
import java.util.List;

public class Linea {
    private final String codigo;
    private final List<Parada> paradas;

    public Linea(String codigo) {
        this.codigo = codigo;
        this.paradas = new ArrayList<>();
    }

    public void agregarParada(Parada parada) {
        if (!paradas.contains(parada)) {
            paradas.add(parada);
        }
    }

    public List<Parada> getParadas() {
        return paradas;
    }

    public String getCodigo() {
        return codigo;
    }
}
