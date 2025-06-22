package presentacion;

import domain.Colectivo;
import domain.Parada;
import domain.Pasajero;

/**
 * Clase encargada de mostrar los eventos y estados de la simulación por consola.
 * Centraliza todos los mensajes de salida para separar la lógica de presentación del resto del sistema.
 */
public class VistaPorConsola {

    /**
     * Muestra el inicio de una nueva parada.
     * @param numeroParada Número de la parada actual.
     */
    public void mostrarInicioParada(final int numeroParada) {
        System.out.println("\n=== PARADA " + numeroParada + " ===");
    }

    /**
     * Muestra el mensaje cuando un pasajero sube al colectivo.
     * @param p Pasajero que sube.
     */
    public void mostrarPasajeroSubio(final Pasajero p) {
        System.out.println("🔺 Pasajero " + p.getId() + " subió");
    }

    /**
     * Muestra el mensaje cuando un pasajero baja del colectivo.
     * @param p Pasajero que baja.
     */
    public void mostrarPasajeroBajo(final Pasajero p) {
        System.out.println("🔻 Pasajero " + p.getId() + " bajó");
    }

    /**
     * Muestra el estado actual del colectivo después de una parada.
     * @param c Colectivo.
     * @param bajaron Cantidad de pasajeros que bajaron.
     * @param subieron Cantidad de pasajeros que subieron.
     */
    public void mostrarEstadoColectivo(final Colectivo c, final int bajaron, final int subieron) {
        System.out.println("👥 Bajaron: " + bajaron + " | Subieron: " + subieron + " | A bordo: " + c.getCantidadPasajeros());
    }

    /**
     * Muestra la llegada de un colectivo a una parada.
     * @param c Colectivo.
     * @param p Parada a la que llegó el colectivo.
     */
    public void mostrarLlegadaColectivo(final Colectivo c, final Parada p) {
        System.out.println("🚌 Línea " + c.getLinea().getCodigo() + " llegó a " + p.getDireccion());
    }

    /**
     * Muestra el mensaje de finalización de recorrido de un colectivo.
     * @param c Colectivo que finalizó su recorrido.
     */
    public void mostrarFinRecorrido(final Colectivo c) {
        System.out.println("✅ Colectivo de línea " + c.getLinea().getCodigo() + " finalizó su recorrido.");
    }

    /**
     * Muestra el mensaje de finalización de la simulación.
     */
    public void mostrarFinSimulacion() {
        System.out.println("\n🛑 Simulación finalizada.");
    }

    /**
     * Muestra una advertencia cuando una parada referenciada no se encuentra.
     * @param idParada ID de la parada no encontrada.
     */
    public void mostrarAdvertenciaParadaNoEncontrada(final int idParada) {
        System.err.println("⚠️ Parada no encontrada para ID: " + idParada);
    }

    /**
     * Muestra un mensaje cuando el colectivo está lleno y quedan pasajeros esperando.
     * @param c Colectivo.
     * @param p Parada.
     * @param cantidad Cantidad de pasajeros que quedaron esperando.
     */
    public void mostrarColectivoLlenoYPasajerosEsperando(final Colectivo c, final Parada p, final int cantidad) {
        System.out.println("⚠️ Colectivo de línea " + c.getLinea().getCodigo() +
            " está lleno en " + p.getDireccion() +
            ". Quedaron " + cantidad + " pasajeros esperando.");
    }

    /**
     * Muestra el índice de satisfacción calculado al finalizar la simulación.
     * @param indice Valor del índice de satisfacción (entre 0 y 1).
     */
    public void mostrarIndiceSatisfaccion(final double indice) {
        System.out.printf("⭐ Índice de satisfacción: %.2f%n", indice);
    }

    /**
     * Muestra el promedio de ocupación de un colectivo durante la simulación.
     * @param colectivo Colectivo del que se muestra la ocupación.
     * @param promedio Valor promedio de ocupación (entre 0 y 1).
     */
    public void mostrarOcupacionPromedio(final Colectivo colectivo, final double promedio) {
        System.out.printf("🚏 Colectivo %d (Línea %s) - Ocupación promedio: %.2f%n",
            colectivo.getId(), colectivo.getLinea().getCodigo(), promedio);
    }

    /**
     * Muestra una advertencia cuando el ID de una parada en el archivo de líneas no es válido.
     * @param idStr ID de parada no válido como string.
     */
    public void mostrarAdvertenciaParadaNoValida(String idStr) {
        System.err.println("[!] ID de parada no válido en archivo de líneas: " + idStr);
    }
}