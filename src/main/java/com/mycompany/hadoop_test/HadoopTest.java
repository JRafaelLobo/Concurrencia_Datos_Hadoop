package com.mycompany.hadoop_test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

import java.io.IOException;
import java.net.URI;

public class HadoopTest {

    public static void main(String[] args) {
        // Configuración de Hadoop
        JobConf conf = new JobConf(HadoopTest.class);
        conf.set("fs.defaultFS", "hdfs://104.198.29.159:9000");

        try {
            // Crear un cliente para el sistema de archivos
            FileSystem fs = FileSystem.get(new URI("hdfs://104.198.29.159:9000"), conf);

            // Verificar si el directorio raíz está accesible
            boolean exists = fs.exists(new Path("/"));
            System.out.println("HDFS está accesible: " + exists);

            // Cerrar el cliente de archivos
            fs.close();

            // Configurar y ejecutar el trabajo MapReduce
            conf.setJobName("WordCount");
            conf.setOutputKeyClass(Text.class);
            conf.setOutputValueClass(IntWritable.class);
            conf.setMapperClass(WC_Mapper.class);
            conf.setCombinerClass(WC_Reducer.class);
            conf.setReducerClass(WC_Reducer.class);
            conf.setInputFormat(TextInputFormat.class);
            conf.setOutputFormat(TextOutputFormat.class);

            FileInputFormat.setInputPaths(conf, new Path(args[0]));
            FileOutputFormat.setOutputPath(conf, new Path(args[1]));

            JobClient.runJob(conf);

        } catch (IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
