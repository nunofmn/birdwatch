package birdwatch;

import java.io.IOException;


import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
//??
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;


public static class BirdWatchMapper
      extends Mapper<Object, Text, Text, ArrayWritable>{

    private final static ArrayWritable values;

    private Text key = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {

      final String[] s = value.toString().split("\\s+");
      final String keyString = s[1] + " " + s[0];
      final String[]  valuesArr = {s[3],s[4],(s[6].equals(2))?s[5]:"0"};

      key.set(keyString);
      context.write(key, new ArrayWritable(valuesArr));
    }
}