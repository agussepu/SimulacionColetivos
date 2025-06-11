package presentacion;

import domain.Colectivo;
import domain.Parada;
import domain.Pasajero;

public class VistaPorConsola {
    public void mostrarInicioParada(int numeroParada) {
        System.out.println("\n=== PARADA " + numeroParada + " ===");
    }

    public void mostrarPasajeroSubio(Pasajero p) {
        System.out.println("🔺 Pasajero " + p.getId() + " subió");
    }

    public void mostrarPasajeroBajo(Pasajero p) {
        System.out.println("🔻 Pasajero " + p.getId() + " bajó");
    }

    public void mostrarEstadoColectivo(Colectivo c, int bajaron, int subieron) {
        System.out.println("👥 Bajaron: " + bajaron + " | Subieron: " + subieron + " | A bordo: " + c.getCantidadPasajeros());
    }

    public void mostrarLlegadaColectivo(Colectivo c, Parada p) {
        System.out.println("🚌 Línea " + c.getLinea().getCodigo() + " llegó a " + p.getDireccion());
    }

    public void mostrarFinRecorrido(Colectivo c) {
        System.out.println("✅ Colectivo de línea " + c.getLinea().getCodigo() + " finalizó su recorrido.");
    }

    public void mostrarFinSimulacion() {
        System.out.println("\n🛑 Simulación finalizada.");
    }

    public void mostrarAdvertenciaParadaNoEncontrada(int idParada) {
        System.err.println("⚠️ Parada no encontrada para ID: " + idParada);
    }
}