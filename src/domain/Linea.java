package domain;

import java.util.List;
import java.util.ArrayList;

public class Linea {
    private String codigo;
    private List<Parada> paradas;

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
