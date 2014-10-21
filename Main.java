package birdwatch; 

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public final class Main{

  public static void main(String[] args) throws Exception {
   
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "birdwatch");
    job.setJarByClass(Main.class);
    job.setMapperClass(BirdWatchMapper.class);
   

    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);


    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(ArrayWritable.class);
    
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}