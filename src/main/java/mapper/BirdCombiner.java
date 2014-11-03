package mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import reducer.ReducerOutputWritable;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by EngSoftware on 03-11-2014.
 */
public class BirdCombiner extends Reducer<Text, MapperOutputWritable, Text, MapperOutputWritable> {

    public void reduce(Text key, Iterable<MapperOutputWritable> values, Context context)
            throws IOException, InterruptedException {


        Set<MapperOutputWritable> unique = new HashSet<MapperOutputWritable>();
        for (MapperOutputWritable value : values) {
            if (unique.add(value)) {
                context.write(key, value);
            }
        }
    }
}
