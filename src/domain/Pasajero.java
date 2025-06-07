package domain;

public class Pasajero {
    private int id;
    private Parada destino;

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

