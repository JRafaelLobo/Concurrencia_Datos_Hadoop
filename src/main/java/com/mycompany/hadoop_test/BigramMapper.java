package com.mycompany.hadoop_test;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class BigramMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text bigram = new Text();

    public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
        String line = value.toString();
        String[] words = line.split("\\s+");

        for (int i = 0; i < words.length - 1; i++) {
            for (int j = i + 1; j < words.length - 1; j++) {
                String word1 = words[i];
                String word2 = words[j];
                if (word1.compareTo(word2) < 0) {
                    bigram.set(word1 + " " + word2);
                } else {
                    bigram.set(word2 + " " + word1);
                }
                output.collect(bigram, one);
            }
        }
    }
}
