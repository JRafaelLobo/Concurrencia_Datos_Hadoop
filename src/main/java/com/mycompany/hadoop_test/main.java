
package com.mycompany.hadoop_test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.StringTokenizer;
import org.checkerframework.checker.units.qual.Temperature;
/**
 *
 * @author rinal
 */
public class main {
    
    //mapper Class
     public static class RankPasswordMapper extends Mapper<Object, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text rankPassword = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] fields = value.toString().split(",");
            if (fields.length == 2) { 
                String rank = fields[0].trim();
                String password = fields[1].trim();
                rankPassword.set(rank + "," + password);
                context.write(rankPassword, one);
            }
        }
    }

    // Reducer class
    public static class CountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }


    public static void main(String[] args) throws Exception {
        Configuration conf= new Configuration();
        //conf.set("yarn.resourcemanager.address","http//localhost:8032");
        
        Job job= Job.getInstance(conf, "ElNombreMasPerron");
        job.setMapperClass(RankPasswordMapper.class);
        job.setOutputKeyClass(Text.class); 
        job.setOutputValueClass(IntWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[0]));
        System.out.println("Hello World!");
        
        System.exit(job.waitForCompletion(true)?0:1);
    }
}

