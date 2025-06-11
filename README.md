# Simulacion de Colectivos
**Autor:** *Matias Agustin Sepulveda*

---
# Flujo General

1. **Carga de configuración:**  
   Se lee el archivo `config.properties` para obtener los nombres y ubicaciones de los archivos de datos y parámetros de la simulación.

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
   - Se muestra por consola el estado de cada colectivo, indicando cuántos pasajeros suben, bajan y quedan a bordo.

7. **Finalización:**  
   La simulación termina cuando todos los colectivos han completado su recorrido por sus respectivas líneas.

## Nota a tener en cuenta
Cada colectivo genera como maximo 3 pasajeros por cada parada, esto puede modificarse desde el properties

## Incremento 1
**Consigna:** Inicialmente, se debe cargar la información correspondiente a las líneas y paradas que modelan la red de colectivos. Los mismos son cargados desde archivos de texto. Para correr la simulación se deben generar los pasajeros distribuidos en las distintas paradas y un colectivo por línea que realiza un solo recorrido por la misma. Para cada colectivo mostrar las paradas por la que va pasando y los pasajeros que suben y bajan en cada parada.



