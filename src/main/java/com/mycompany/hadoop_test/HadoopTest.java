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
        JobConf conf = new JobConf(HadoopTest.class);
        conf.set("fs.defaultFS", "hdfs://localhost:9000");

        try {
            if (args.length != 4) {
                System.out.println("Error de parametros de entrada porfavor intentar");
                System.out.println("-------------------------------");
                System.out.println("hadoop jar target/hadoop_test-1.0-SNAPSHOT.jar "
                        + "com.mycompany.hadoop_test.HadoopTest "
                        + "<input> "
                        + "<output> "
                        + "<cantidad de palabras a analizar(1-2)> "
                        + "\n------------------------------");
                return;
            }
            FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), conf);
            boolean exists = fs.exists(new Path("/"));
            System.out.println("HDFS está accesible: " + exists);
            Path outputPath = new Path(args[2]);
            Path inputPath = new Path(args[1]);
            fs.close();

            System.out.println("--------------------------");
            System.out.println("Directorio de entrada: " + inputPath);
            System.out.println("Directorio de salida: " + outputPath);
            System.out.println("--------------------------");

            conf.setJobName("WordCount");
            conf.setOutputKeyClass(Text.class);
            conf.setOutputValueClass(IntWritable.class);

            if (Integer.valueOf(args[3]) == 1) {
                conf.setMapperClass(WC_Mapper.class);
                conf.setCombinerClass(WC_Reducer.class);
                conf.setReducerClass(WC_Reducer.class);
            } else {
                conf.setMapperClass(BigramMapper.class);
                conf.setCombinerClass(BigramReducer.class);
                conf.setReducerClass(BigramReducer.class);
            }

            conf.setInputFormat(TextInputFormat.class);
            conf.setOutputFormat(TextOutputFormat.class);

            FileInputFormat.setInputPaths(conf, inputPath);
            FileOutputFormat.setOutputPath(conf, outputPath);

            JobClient.runJob(conf);

        } catch (IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
