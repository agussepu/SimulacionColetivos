package util;

import java.util.List;
import java.util.Map;
import java.util.Random;

import domain.*;

public class CargaDePasajeros {
    private static final Random random = new Random();
    
    public static void generarPasajeros(List<Linea> lineas){        
        int idPasajero = 0;
        
        for(Linea l: lineas) {
            List<Parada> paradas = l.getParadas();

            for(int i = 0; i < paradas.size() - 1; i++){
                Parada origen = paradas.get(i);
                int cantidadPasajeros = random.nextInt(3) + 1;

                for(int j=0; j < cantidadPasajeros; j++) {
                    int destinoIndex = i + 1 + random.nextInt(paradas.size() - i - 1);
                    Parada destino = paradas.get(destinoIndex);
                    Pasajero pasajero = new Pasajero(idPasajero++, destino);
                    origen.agregarPasajero(pasajero);
                }
            }
        }
    }
}
