package util;

import domain.*;
import config.Configuracion;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

public class Simulador {
    private final int MAX_CAPACIDAD = Configuracion.getCantidadPasajeros();
    private final List<Colectivo> colectivos;
    private final Map<Colectivo, Integer> posiciones = new HashMap<>();

    public Simulador(List<Colectivo> colectivos) {
        this.colectivos = colectivos;
        colectivos.forEach(c -> posiciones.put(c, 0));
    }

    public void ejecutar() {
        boolean enCurso = true;
        int numeroParada = 1;

        while (enCurso) {
            System.out.println("\n=== PARADA " + numeroParada + " ===");
            enCurso = false;

            for (Colectivo colectivo : colectivos) {
                int pos = posiciones.get(colectivo);
                List<Parada> paradas = colectivo.getLinea().getParadas();

                if (pos < paradas.size()) {
                    Parada actual = paradas.get(pos);
                    System.out.println("🚌 Línea " + colectivo.getLinea().getCodigo() + " llegó a " + actual.getDireccion());

                    List<Pasajero> bajaron = colectivo.bajarPasajerosEn(actual);
                    List<Pasajero> subieron = colectivo.subirPasajerosDesdeParada(actual, new HashSet<>(paradas.subList(pos + 1, paradas.size())), MAX_CAPACIDAD);

                    bajaron.forEach(p -> System.out.println("🔻 Pasajero " + p.getId() + " bajó"));
                    subieron.forEach(p -> System.out.println("🔺 Pasajero " + p.getId() + " subió"));

                    System.out.println("👥 Bajaron: " + bajaron.size() + " | Subieron: " + subieron.size() + " | A bordo: " + colectivo.getCantidadPasajeros());

                    posiciones.put(colectivo, pos + 1);
                    enCurso = true;
                } else {
                    System.out.println("✅ Colectivo de línea " + colectivo.getLinea().getCodigo() + " finalizó su recorrido.");
                }
            }

            numeroParada++;
        }

        System.out.println("\n🛑 Simulación finalizada.");
    }
}
