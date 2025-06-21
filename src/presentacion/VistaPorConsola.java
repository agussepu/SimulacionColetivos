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
    public void mostrarInicioParada(int numeroParada) {
        System.out.println("\n=== PARADA " + numeroParada + " ===");
    }

    /**
     * Muestra el mensaje cuando un pasajero sube al colectivo.
     * @param p Pasajero que sube.
     */
    public void mostrarPasajeroSubio(Pasajero p) {
        System.out.println("🔺 Pasajero " + p.getId() + " subió");
    }

    /**
     * Muestra el mensaje cuando un pasajero baja del colectivo.
     * @param p Pasajero que baja.
     */
    public void mostrarPasajeroBajo(Pasajero p) {
        System.out.println("🔻 Pasajero " + p.getId() + " bajó");
    }

    /**
     * Muestra el estado actual del colectivo después de una parada.
     * @param c Colectivo.
     * @param bajaron Cantidad de pasajeros que bajaron.
     * @param subieron Cantidad de pasajeros que subieron.
     */
    public void mostrarEstadoColectivo(Colectivo c, int bajaron, int subieron) {
        System.out.println("👥 Bajaron: " + bajaron + " | Subieron: " + subieron + " | A bordo: " + c.getCantidadPasajeros());
    }

    /**
     * Muestra la llegada de un colectivo a una parada.
     * @param c Colectivo.
     * @param p Parada a la que llegó el colectivo.
     */
    public void mostrarLlegadaColectivo(Colectivo c, Parada p) {
        System.out.println("🚌 Línea " + c.getLinea().getCodigo() + " llegó a " + p.getDireccion());
    }

    /**
     * Muestra el mensaje de finalización de recorrido de un colectivo.
     * @param c Colectivo que finalizó su recorrido.
     */
    public void mostrarFinRecorrido(Colectivo c) {
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
    public void mostrarAdvertenciaParadaNoEncontrada(int idParada) {
        System.err.println("⚠️ Parada no encontrada para ID: " + idParada);
    }

    /**
     * Muestra un mensaje cuando el colectivo está lleno y quedan pasajeros esperando.
     * @param c Colectivo.
     * @param p Parada.
     * @param cantidad Cantidad de pasajeros que quedaron esperando.
     */
    public void mostrarColectivoLlenoYPasajerosEsperando(Colectivo c, Parada p, int cantidad) {
        System.out.println("⚠️ Colectivo de línea " + c.getLinea().getCodigo() +
            " está lleno en " + p.getDireccion() +
            ". Quedaron " + cantidad + " pasajeros esperando.");
    }

    public void mostrarIndiceSatisfaccion(double indice) {
        System.out.printf("⭐ Índice de satisfacción: %.2f%n", indice);
    }

    public void mostrarOcupacionPromedio(Colectivo colectivo, double promedio) {
        System.out.printf("Colectivo %d (Línea %s) - Ocupación promedio: %.2f%n",
            colectivo.getId(), colectivo.getLinea().getCodigo(), promedio);
    }
}