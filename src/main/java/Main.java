import mapper.BirdCombiner;
import mapper.BirdMapper;
import mapper.MapperOutputWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import output.DynamoDBOutputFormat;
import reducer.BirdReducer;
import reducer.ReducerOutputWritable;


public final class Main{

  public static void main(String[] args) throws Exception {

      // Create a new Job
      Job job = new Job(new Configuration());
      job.setJarByClass(Main.class);

      // Specify various job-specific parameters
      job.setJobName("myjob");

      job.setMapperClass(BirdMapper.class);
      job.setReducerClass(BirdReducer.class);

      job.setMapOutputKeyClass(Text.class);
      job.setMapOutputValueClass(MapperOutputWritable.class);

      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(ReducerOutputWritable.class);

      job.setInputFormatClass(TextInputFormat.class);
      job.setOutputFormatClass(DynamoDBOutputFormat.class);

      FileInputFormat.addInputPath(job, new Path(args[0]));

      // Submit the job, then poll for progress until the job is complete
      job.waitForCompletion(true);


  }

}