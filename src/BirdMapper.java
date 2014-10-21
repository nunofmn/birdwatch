

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
//??
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class BirdMapper extends org.apache.hadoop.mapreduce.Mapper<Object, Text, Text, ArrayWritable> {

    private Text keyWord = new Text();

    public void map(Object key, Text value, Context context
    ) throws IOException, InterruptedException {

        final String[] s = value.toString().split("\\s+");
        final String keyString = s[1] + " " + s[0];
        final String[]  valuesArr = {s[3],s[4],(s[6].equals(2))?s[5]:"0"};

        keyWord.set(keyString);
        context.write(keyWord, new ArrayWritable(valuesArr));
    }
}
