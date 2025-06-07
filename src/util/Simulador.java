package util;

import domain.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Simulador {
    private final List<Colectivo> colectivos;
    private final Map<Colectivo, Integer> posiciones = new HashMap<>();
    private final int capacidadMaxima;

    public Simulador(List<Colectivo> colectivos, int capacidadMaxima) {
        this.colectivos = colectivos;
        this.capacidadMaxima = capacidadMaxima;

        for (Colectivo c : colectivos) {
            posiciones.put(c, 0);
        }
    }

    public void ejecutar() {
        boolean sigue = true;
        int parada = 1;

        while (sigue) {
            System.out.println("\n=== PARADA " + parada + " ===");
            sigue = false;

            for (Colectivo c : colectivos) {
                int pos = posiciones.get(c);
                List<Parada> paradas = c.getLinea().getParadas();

                if (pos < paradas.size()) {
                    Parada actual = paradas.get(pos);
                    System.out.println("🚌 Línea " + c.getLinea().getCodigo() + " llegó a " + actual.getDireccion());

                    int bajaron = bajarPasajeros(c, actual);
                    int subieron = subirPasajeros(c, actual, paradas, pos);

                    System.out.println("👥 Bajaron: " + bajaron + " | Subieron: " + subieron +
                            " | A bordo: " + c.getPasajeros().size());

                    posiciones.put(c, pos + 1);
                    sigue = true;
                } else {
                    System.out.println("✅ Colectivo de línea " + c.getLinea().getCodigo() + " finalizó su recorrido.");
                }
            }

            parada++;
        }

        System.out.println("\n🛑 Simulación finalizada.");
    }

    private int bajarPasajeros(Colectivo colectivo, Parada paradaActual) {
        int antes = colectivo.getPasajeros().size();
        colectivo.bajarPasajeros(paradaActual);
        return antes - colectivo.getPasajeros().size();
    }

    private int subirPasajeros(Colectivo colectivo, Parada paradaActual, List<Parada> paradas, int pos) {
        int disponibles = capacidadMaxima - colectivo.getPasajeros().size();
        int subieron = 0;

        List<Pasajero> esperando = new ArrayList<>(paradaActual.getPasajeros());
        Set<Parada> destinosRestantes = new HashSet<>(paradas.subList(pos + 1, paradas.size()));

        for (Pasajero p : esperando) {
            if (disponibles == 0) break;
            if (destinosRestantes.contains(p.getDestino())) {
                colectivo.subirPasajero(p);
                paradaActual.getPasajeros().remove(p);
                System.out.println("🔺 Pasajero " + p.getId() + " subió");
                subieron++;
                disponibles--;
            }
        }

        return subieron;
    }
}

