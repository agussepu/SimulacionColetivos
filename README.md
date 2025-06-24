# Simulacion de Colectivos
**Autor:** *Matias Agustin Sepulveda*

---
# Flujo General

1. **Carga de configuración:**  
   Se lee el archivo `config.properties` para obtener las rutas de los archivos de datos y los parámetros necesarios para la simulación.

2. **Carga de datos de líneas y paradas:**  
   Se leen los archivos de texto especificados en la configuración para crear los objetos `Linea` y `Parada` que representan la red de colectivos.

3. **Instanciación de líneas y paradas:**  
   Se crean las instancias de las clases `Linea` y `Parada`, y se asocian las paradas correspondientes a cada línea.

4. **Generación de colectivos:**  
   Por cada línea, se genera un colectivo (`Colectivo`) que realizará el recorrido completo de esa línea.

5. **Generación de pasajeros:**  
   Se generan pasajeros (`Pasajero`) en las distintas paradas, asignándoles destinos válidos dentro del recorrido de la línea.

6. **Ejecución de la simulación:**  
   El simulador (`Simulador`) recorre las paradas de cada línea. En cada parada:
   - Los pasajeros cuyo destino es la parada actual bajan del colectivo.
   - Suben nuevos pasajeros que esperan en la parada y cuyo destino está más adelante en el recorrido.
   - Se muestra el estado de cada colectivo, indicando cuántos pasajeros suben, bajan y quedan a bordo.

7. **Finalización:**  
   La simulación concluye cuando todos los colectivos han completado su recorrido por sus respectivas líneas, se muestran los resultados finales por consola y ademas se guardan en data/salida_simulacion.txt.

---

## Incremento 1
**Consigna:** Inicialmente, se debe cargar la información correspondiente a las líneas y paradas que modelan la red de colectivos. Los mismos son cargados desde archivos de texto. Para correr la simulación se deben generar los pasajeros distribuidos en las distintas paradas y un colectivo por línea que realiza un solo recorrido por la misma. Para cada colectivo mostrar las paradas por la que va pasando y los pasajeros que suben y bajan en cada parada.

## Incremento 2
**Consigna:** Agregar a la simulación la posibilidad de que cada colectivo haga más de un recorrido dentro de su línea. Cada colectivo tiene una capacidad máxima de pasajeros que puede transportar. Puede darse el caso que queden pasajeros esperando en la parada al próximo colectivo, si el que llega a la parada completa antes su capacidad. También agregar a la simulación el cálculo del índice de satisfacción del cliente y de ocupación promedio de los colectivos (ver anexos). Junto con la aplicación entregar toda la documentación solicitada.

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