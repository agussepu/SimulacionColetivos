# Simulación de líneas de colectivos urbanas: 
Desarrollar un sistema para simular el funcionamiento de líneas de colectivos urbanos.

## Incremento 1
Inicialmente, se debe cargar la información correspondiente a las líneas y paradas que modelan la red de colectivos. Los mismos son cargados desde archivos de texto. Para correr la simulación se deben generar los pasajeros distribuidos en las distintas paradas y un colectivo por línea que realiza un solo recorrido por la misma. Para cada colectivo mostrar las paradas por la que va pasando y los pasajeros que suben y bajan en cada parada.

## Incremento 2
Agregar a la simulación la posibilidad de que cada colectivo haga más de un recorrido dentro de su línea. Cada colectivo tiene una capacidad máxima de pasajeros que puede transportar. Puede darse el caso que queden pasajeros esperando en la parada al próximo colectivo, si el que llega a la parada completa antes su capacidad. También agregar a la simulación el cálculo del índice de satisfacción del cliente y de ocupación promedio de los colectivos (ver anexos). Junto con la aplicación entregar toda la documentación solicitada.


# Main Tasks
- [x] Carga del archivo properties
- [ ] Leer el archivo "Lineas" y generar una lista con todas las lineas `List<Linea>`
- [ ] Leer el archivo "Paradas" y generar un Map con las paradas `Map<Integer,Parada>`
- [ ] Crear / Inicializar Colectivos
- [ ] Crear / Inicializar Pasajeros en cada Parada
- [ ] Crear simulacion de movimiento de los colectivos
  - [ ] Recorrido por las paradas
  - [ ] Subida y Bajada de Pasajeros





## Secondary tasks
- [ ] Javadocs
- [ ] Ver como hacer la interfaz

## Temas vistos (deberian contenerlos el proyecto)
- [ ] Estructuras de datos fundamentales (matrices, listas enlazadas)
- [ ] Analisis de Algoritmos (Big O)
- [ ] Recursividad
- [ ] TADs (pilas, colas, mazos) 
- [ ] Listas e Iteradors
- [ ] Arboles binarios
- [ ] Mapas
- [ ] Tablas de Hash
- [ ] Grafos
- [ ] Archivos

# Esquema del proyecto

```pgsql
SimulacionDeColectivos/
│
├── 📁 src/
│   ├── 📁 main/                 --> Clase Main, lanzadores de simulación
│   │   └── Simulador.java
│   │
│   ├── 📁 domain/              --> Clases Principales: desde UML
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
└── 📁 data/                    --> Archivos de texto para líneas y paradas
    ├── config.properties
    ├── lineas.txt
    └── paradas.txt

```


# Condiciones de la presentación:
    • Introducción 
    • Planteo del problema
    • Análisis de las estructuras seleccionadas 
    • Diagrama de clases
    • Implementación de la solución
    • Manual de funcionamiento (ingreso de datos, pruebas, resultados de salida)
    • Errores detectados (si existe algún error y bajo qué condiciones se produce)
    • Lotes de prueba
    • Posibles mejoras y extensiones
    • Conclusiones