package presentacion;

import domain.Colectivo;
import domain.Parada;
import domain.Pasajero;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

/**
 * Clase encargada de mostrar los eventos y estados de la simulación por consola.
 * Centraliza todos los mensajes de salida para separar la lógica de presentación del resto del sistema.
 * Permite opcionalmente guardar la salida en un archivo.
 */
public class SimulacionOutput {

    private PrintStream archivoOut = null;

    public SimulacionOutput() {} // Constructor por defecto: solo salida por consola.

    /**
     * Constructor que permite especificar un archivo para guardar la salida.
     * @param rutaArchivo Ruta del archivo de salida.
     * @throws FileNotFoundException Si no se puede crear el archivo.
     */
    public SimulacionOutput(String rutaArchivo) throws FileNotFoundException {
        archivoOut = new PrintStream(rutaArchivo);
    }

    // =========================
    // Métodos de impresión (privados)
    // =========================

    /**
     * Imprime un mensaje en la consola y, si se ha definido un archivo de salida, también lo escribe allí.
     * @param mensaje Mensaje a imprimir.
     */
    private void imprimir(String mensaje) {
        System.out.println(mensaje);
        if (archivoOut != null) archivoOut.println(mensaje);
    }

    /**
     * Imprime un mensaje de error en la consola y en el archivo de salida si está definido.
     * @param mensaje Mensaje de error a imprimir.
     */
    private void imprimirError(String mensaje) {
        System.err.println(mensaje);
        if (archivoOut != null) archivoOut.println(mensaje);
    }

    /**
     * Imprime un mensaje formateado, similar a printf.
     * @param formato Formato del mensaje.
     * @param args Argumentos para el formato.
     */
    private void imprimirf(String formato, Object... args) {
        System.out.printf(formato, args);
        if (archivoOut != null) archivoOut.printf(formato, args);
    }

    // =========================
    // Métodos de cierre y fábrica
    // =========================

    /**
     * Cierra el archivo de salida si está abierto.
     */
    public void cerrarArchivo() {
        if (archivoOut != null) archivoOut.close();
    }

    /**
     * Crea una instancia de VistaPorConsola que guarda la salida en el archivo indicado.
     * Si ocurre un error, retorna una instancia que solo muestra por consola.
     * @param rutaArchivo Ruta del archivo de salida.
     * @return VistaPorConsola configurada.
     */
    public static SimulacionOutput crearConArchivo(String rutaArchivo) {
        try {
            return new SimulacionOutput(rutaArchivo);
        } catch (FileNotFoundException e) {
            System.err.println("No se pudo crear el archivo de salida en " + rutaArchivo + ", solo se mostrará por consola.");
            return new SimulacionOutput();
        }
    }

    // =========================
    // Métodos de eventos generales (inicio/finalización, advertencias)
    // =========================

    /**
     * Muestra el inicio de una nueva parada.
     * @param numeroParada Número de la parada actual.
     */
    public void mostrarInicioParada(final int numeroParada) {
        imprimir("=================\n=== PARADA " + numeroParada + " === \n=================");
    }

    /**
     * Muestra el mensaje de finalización de la simulación.
     */
    public void mostrarFinSimulacion() {
        imprimir("\n🛑 Simulación finalizada.");
    }

    /**
     * Muestra una advertencia cuando una parada referenciada no se encuentra.
     * @param idParada ID de la parada no encontrada.
     */
    public void mostrarAdvertenciaParadaNoEncontrada(final int idParada) {
        imprimirError("⚠️ Parada no encontrada para ID: " + idParada);
    }

    /**
     * Muestra una advertencia cuando el ID de una parada en el archivo de líneas no es válido.
     * @param idStr ID de parada no válido como string.
     */
    public void mostrarAdvertenciaParadaNoValida(String idStr) {
        imprimirError("[!] ID de parada no válido en archivo de líneas: " + idStr);
    }

    // =========================
    // Métodos de eventos de colectivos
    // =========================

    /**
     * Muestra la llegada de un colectivo a una parada.
     * @param c Colectivo.
     * @param p Parada a la que llegó el colectivo.
     */
    public void mostrarLlegadaColectivo(final Colectivo c, final Parada p) {
        imprimir("🚌 Línea " + c.getLinea().getCodigo() + " llegó a " + p.getDireccion());
    }

    /**
     * Muestra el mensaje de finalización de recorrido de un colectivo.
     * @param c Colectivo que finalizó su recorrido.
     */
    public void mostrarFinRecorrido(final Colectivo c) {
        imprimir("✅ Colectivo de línea " + c.getLinea().getCodigo() + " finalizó su recorrido.");
    }

    /**
     * Muestra el estado actual del colectivo después de una parada.
     * @param c Colectivo.
     * @param bajaron Cantidad de pasajeros que bajaron.
     * @param subieron Cantidad de pasajeros que subieron.
     */
    public void mostrarEstadoColectivo(final Colectivo c, final int bajaron, final int subieron) {
        imprimir("👥 Bajaron: " + bajaron + " | Subieron: " + subieron + " | A bordo: " + c.getCantidadPasajeros());
    }

    /**
     * Muestra el promedio de ocupación de un colectivo durante la simulación.
     * @param colectivo Colectivo del que se muestra la ocupación.
     * @param promedio Valor promedio de ocupación (entre 0 y 1).
     */
    public void mostrarOcupacionPromedio(final Colectivo colectivo, final double promedio) {
        imprimirf("🚏 Colectivo %d (Línea %s) - Ocupación promedio: %.2f%n",
            colectivo.getId(), colectivo.getLinea().getCodigo(), promedio);
    }

    /**
     * Muestra el índice de satisfacción calculado al finalizar la simulación.
     * @param indice Valor del índice de satisfacción (entre 0 y 1).
     */
    public void mostrarIndiceSatisfaccion(final double indice) {
        imprimirf("⭐ Índice de satisfacción: %.2f%n", indice);
    }

    /**
     * Muestra una advertencia si el colectivo está lleno y hay pasajeros esperando en la parada actual.
     * @param colectivo Colectivo que está lleno.
     * @param actual Parada actual donde se encuentra el colectivo.
     * @param esperando Cantidad de pasajeros esperando con destino válido.
     */
    public void mostrarAdvertenciaColectivoLleno(Colectivo colectivo, Parada actual, int esperando) {
        if (esperando > 0) {
            mostrarColectivoLlenoYPasajerosEsperando(colectivo, actual, esperando);
        }
    }

    /**
     * Muestra un mensaje cuando el colectivo está lleno y quedan pasajeros esperando.
     * @param c Colectivo.
     * @param p Parada.
     * @param cantidad Cantidad de pasajeros que quedaron esperando.
     */
    public void mostrarColectivoLlenoYPasajerosEsperando(final Colectivo c, final Parada p, final int cantidad) {
        imprimir("⚠️ Colectivo de línea " + c.getLinea().getCodigo() +
            " está lleno en " + p.getDireccion() +
            ". Quedaron " + cantidad + " pasajeros esperando.");
    }

    // =========================
    // Métodos de eventos de pasajeros
    // =========================

    /**
     * Muestra el mensaje cuando un pasajero sube al colectivo.
     * @param p Pasajero que sube.
     */
    public void mostrarPasajeroSubio(final Pasajero p) {
        imprimir("🔺 Pasajero " + p.getId() + " subió");
    }

    /**
     * Muestra el mensaje cuando un pasajero baja del colectivo.
     * @param p Pasajero que baja.
     */
    public void mostrarPasajeroBajo(final Pasajero p) {
        imprimir("🔻 Pasajero " + p.getId() + " bajó");
    }

    /**
     * Muestra los eventos de subida y bajada de pasajeros en una parada.
     * @param bajaron Lista de pasajeros que bajaron del colectivo.
     * @param subieron Lista de pasajeros que subieron al colectivo.
     */
    public void mostrarEventosPasajeros(List<Pasajero> bajaron, List<Pasajero> subieron) {
        for (Pasajero p : bajaron) {
            mostrarPasajeroBajo(p);
        }
        for (Pasajero p : subieron) {
            mostrarPasajeroSubio(p);
        }
    }
}