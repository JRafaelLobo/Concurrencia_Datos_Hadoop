# README

## Descripción

Esta aplicación tiene el objetivo de calcular las frecuencias de palabras en los archivos. Ofrece dos métodos de análisis:

- **Frecuencia de palabras individuales**: Calcula la frecuencia de cada palabra por separado.
- **Frecuencia de pares de palabras**: Calcula la frecuencia de cada combinación posible de dos palabras que aparezcan en la misma línea.

La aplicación está diseñada para integrarse con el programa disponible en [Frequency Analysis](https://github.com/MarcelaTovar/Concurrencia.git).

## Configuración de Hadoop

Para utilizar el programa, es fundamental que Hadoop esté configurado y en funcionamiento en los puertos correctos. Los puertos por defecto son:

- **NameNode**: 9000 (especificado en `core-site.xml`)
- **ResourceManager**: 8032
- **NodeManager**: 8040
- **DataNodes**: Usualmente 50010 (especificado en `hdfs-site.xml`)

## Cómo Usarlo

1. **Preparar el entorno**:
   - Navega a la carpeta del proyecto y abre una consola en esa ubicación.

2. **Ejecutar el comando**:
   - Usa el siguiente comando para ejecutar el programa:

     ```bash
     hadoop jar target/hadoop_test-1.0-SNAPSHOT.jar com.mycompany.hadoop_test.HadoopTest <ruta_entrada> <ruta_salida> <tipo_frecuencia>
     ```

   - **`<ruta_entrada>`**: Es recomendable utilizar el programa enlazado anteriormente para preprocesar el archivo y luego cargarlo en Hadoop.
   - **`<ruta_salida>`**: Recuerda la ruta de salida para utilizar el programa enlazado posteriormente.
   - **`<tipo_frecuencia>`**: 
     - `1` para analizar la frecuencia de cada palabra individual.
     - `2` para analizar la frecuencia de combinaciones de dos palabras.
	 

## Comandos Utiles de Hadoop


- **Listar archivos**:
`` hdfs dfs -ls <ruta> ``
- **Copiar archivos desde el sistema local a Hadoop**:
``hdfs dfs -copyFromLocal <ruta_local> <ruta_de_hadoop> ``
- **Copiar archivos desde Hadoop al sistema local:**:
`` hdfs dfs -ls <ruta> ``
- **Eliminar archivos:**:
`` hdfs dfs -rm <ruta>
 ``
- **Eliminar carpetas:**:
``hdfs dfs -rm -r <ruta> ``
