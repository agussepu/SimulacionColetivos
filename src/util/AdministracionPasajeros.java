package util;

import config.Configuracion;
import domain.Linea;
import domain.Parada;
import domain.Pasajero;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase utilitaria para la administración y generación de pasajeros
 * en las distintas líneas y paradas del sistema de simulación de colectivos.
 */
public class AdministracionPasajeros {
    private static final Random random = new Random();
    private static final List<Pasajero> todosLosPasajeros = new ArrayList<>();
    private static final int MAX_PASAJEROS_POR_PARADA = Configuracion.getMaxPasajerosPorParada();

    /**
     * Genera pasajeros para cada línea proporcionada, asignando IDs únicos y
     * distribuyéndolos aleatoriamente entre las paradas de cada línea.
     *
     * @param lineas Lista de líneas sobre las que se generarán los pasajeros.
     */
    public static void generarPasajeros(List<Linea> lineas) {
        int idPasajero = 0;

        for (Linea linea : lineas) {
            idPasajero = generarPasajerosParaLinea(linea, idPasajero);
        }
    }

    /**
     * Genera pasajeros para una línea específica, asignando IDs consecutivos a partir de un valor inicial.
     * Los pasajeros se distribuyen aleatoriamente entre las paradas de la línea.
     *
     * @param linea Línea sobre la que se generarán los pasajeros.
     * @param idInicial ID inicial a asignar al primer pasajero generado.
     * @return El siguiente ID disponible después de generar los pasajeros.
     */
    private static int generarPasajerosParaLinea(Linea linea, int idInicial) {
        List<Parada> paradas = linea.getParadas();
        int idPasajero = idInicial;
        int cantidadParadas = paradas.size() - 1;

        for (int i = 0; i < cantidadParadas; i++) {
            Parada origen = paradas.get(i);
            int cantidad = random.nextInt(MAX_PASAJEROS_POR_PARADA) + 1;

            for (int j = 0; j < cantidad; j++) {
                Parada destino = elegirDestinoAleatorio(paradas, i);
                Pasajero pasajero = new Pasajero(idPasajero++, destino);
                origen.agregarPasajero(pasajero);
                todosLosPasajeros.add(pasajero);
            }
        }

        return idPasajero;
    }

    /**
     * Elige aleatoriamente una parada de destino distinta a la de origen,
     * asegurando que el destino esté después del origen en la lista de paradas.
     *
     * @param paradas Lista de paradas de la línea.
     * @param origenIndex Índice de la parada de origen.
     * @return Parada de destino seleccionada aleatoriamente.
     */
    private static Parada elegirDestinoAleatorio(List<Parada> paradas, int origenIndex) {
        int destinoIndex = origenIndex + 1 + random.nextInt(paradas.size() - origenIndex - 1);
        return paradas.get(destinoIndex);
    }

    /**
     * Devuelve la lista de todos los pasajeros generados en la simulación.
     *
     * @return Lista de todos los pasajeros.
     */
    public static List<Pasajero> getTodosLosPasajeros() {
        return todosLosPasajeros;
    }
}

