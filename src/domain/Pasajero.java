package domain;

public class Pasajero {
    private final int id;
    private final Parada destino;

    public Pasajero(int id, Parada destino) {
        this.id = id;
        this.destino = destino;
    }

    public int getId() {
        return id;
    }

    public Parada getDestino() {
        return destino;
    }
}

