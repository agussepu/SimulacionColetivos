# Simulación de líneas de colectivos urbanas: 
Desarrollar un sistema para simular el funcionamiento de líneas de colectivos urbanos.

## Incremento 1
Inicialmente, se debe cargar la información correspondiente a las líneas y paradas que modelan la red de colectivos. Los mismos son cargados desde archivos de texto. Para correr la simulación se deben generar los pasajeros distribuidos en las distintas paradas y un colectivo por línea que realiza un solo recorrido por la misma. Para cada colectivo mostrar las paradas por la que va pasando y los pasajeros que suben y bajan en cada parada.

modularizar interfaz

# tasks
- [ ] Subir a git la simulacion 
- [ ] Hacer lista de temas que debo utilizar
- [ ] Hacer lista de buenas practicas a implementar
- [ ] Empezar a desarrollar logica de la simulacion
- [ ] Ver como hacer la interfaz


# Esquema del proyecto

```pgsql
colectivos-simulador/
│
├── 📁 src/
│   ├── 📁 app/                 --> Clase Main, lanzadores de simulación
│   │   └── Simulador.java
│   │
│   ├── 📁 domain/              --> Modelo del negocio: entidades Java (Linea, Parada, etc.)
│   │   ├── Linea.java
│   │   ├── Parada.java
│   │   ├── Colectivo.java
│   │   └── Pasajero.java
│   │
│   ├── 📁 service/             --> Lógica de negocio / Simulación
│   │   ├── SimulacionService.java
│   │   └── GeneradorPasajeros.java
│   │
│   ├── 📁 io/                  --> Entrada/salida: lectura de archivos, logs
│   │   ├── CargadorDeLineas.java
│   │   ├── CargadorDeParadas.java
│   │   └── ConsolaPrinter.java
│   │
│   └── 📁 utils/               --> Utilidades generales
│       └── ArchivoUtils.java
│
├── 📁 data/                    --> Archivos de texto para líneas y paradas
│   ├── lineas.txt
│   └── paradas.txt
│
├── 📄 README.md
└── 📄 pom.xml / build.gradle   --> Si usás Maven/Gradle
```