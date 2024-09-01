package com.mycompany.hadoop_test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

public class HadoopTest {
    public static void main(String[] args) {
        System.out.println("Hola");
        // Configuración de Hadoop
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://104.198.29.159:9000");

        try {
            // Crear un cliente para el sistema de archivos
            FileSystem fs = FileSystem.get(new URI("hdfs://104.198.29.159:9000"), conf);

            // Verificar si el directorio raíz está accesible
            boolean exists = fs.exists(new Path("/"));
            System.out.println("HDFS está accesible: " + exists);

            // Cerrar el cliente de archivos
            fs.close();
        } catch (IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
