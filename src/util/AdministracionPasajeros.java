package util;

import config.Configuracion;
import domain.Linea;
import domain.Parada;
import domain.Pasajero;
import java.util.List;
import java.util.Random;

public class AdministracionPasajeros {
    private static final Random random = new Random();

    public static void generarPasajeros(List<Linea> lineas) {
        int idPasajero = 0;

        for (Linea linea : lineas) {
            idPasajero = generarPasajerosParaLinea(linea, idPasajero);
        }
    }

    private static int generarPasajerosParaLinea(Linea linea, int idInicial) {
        List<Parada> paradas = linea.getParadas();
        int idPasajero = idInicial;

        for (int i = 0; i < paradas.size() - 1; i++) {
            Parada origen = paradas.get(i);
            int cantidad = pasajerosAleatorios();

            for (int j = 0; j < cantidad; j++) {
                Parada destino = elegirDestinoAleatorio(paradas, i);
                Pasajero pasajero = new Pasajero(idPasajero++, destino);
                origen.agregarPasajero(pasajero);
            }
        }

        return idPasajero;
    }

    private static int pasajerosAleatorios() {
        return random.nextInt(Configuracion.getMaxPasajerosPorParada()) + 1;
    }

    private static Parada elegirDestinoAleatorio(List<Parada> paradas, int origenIndex) {
        int destinoIndex = origenIndex + 1 + random.nextInt(paradas.size() - origenIndex - 1);
        return paradas.get(destinoIndex);
    }
}

