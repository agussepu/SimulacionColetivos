package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.*;

public class Simulador {

    public static void ejecutar(List<Colectivo> colectivos) {
        Map<Colectivo, Integer> posiciones = new HashMap<>();

        for (Colectivo c : colectivos) {
            posiciones.put(c, 0);
        }

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

                    // --- Bajada de pasajeros ---
                    int antesBajar = c.getPasajeros().size();
                    c.bajarPasajeros(actual);
                    int despuesBajar = c.getPasajeros().size();
                    int bajaron = antesBajar - despuesBajar;

                    // --- Subida de pasajeros ---
                    int capacidadMaxima = Configuracion.getCantidadPasajeros();
                    int disponibles = capacidadMaxima - despuesBajar;
                    int subieron = 0;

                    List<Pasajero> esperando = new ArrayList<>(actual.getPasajeros());
                    for (Pasajero p : esperando) {
                        if (disponibles == 0) break;

                        // Solo sube si su destino está en el trayecto restante
                        if (paradas.subList(pos + 1, paradas.size()).contains(p.getDestino())) {
                            c.subirPasajero(p);
                            actual.getPasajeros().remove(p);
                            subieron++;
                            disponibles--;
                            System.out.println("🔺 Pasajero " + p.getId() + " subió");
                        }
                    }

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
}
