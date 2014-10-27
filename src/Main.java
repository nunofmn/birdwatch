import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.util.Map;


public final class Main{

  public static void main(String[] args) throws Exception {
   
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "birdwatch");
    job.setJarByClass(Main.class);
    job.setMapperClass(BirdMapper.class);
   
    job.setReducerClass(BirdReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(MapperOutputWritable.class);
    
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}