import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BirdReducer extends Reducer<Text, ArrayWritable, Text, BirdWritable>{

    /* BirdReducer Class
        Input:
        -> Key: (date)
        -> Value: (tower_id, bird, wingspan, weight)
        Output:
        -> Key: (date)
        -> Value: ((tower_id, , [ birds ], sum(weight), tower_id, max(weight))
     */
    public void reduce(Text key, Iterable<ArrayWritable> values, Context context)
            throws IOException, InterruptedException {

        BirdWritable outputvalue = new BirdWritable();

        int maxwingspan = 0;
        String maxwingspan_tower = "";
        MapWritable tower_weight = new MapWritable();
        Set<Text> birds = new HashSet<Text>();


        for(ArrayWritable val : values) {

            Writable[] data = val.get();

            //tower_id
            if(tower_weight.containsKey(data[0])) {
                int dateweight = ((IntWritable)tower_weight.get(val.get())).get();
                dateweight = dateweight + Integer.parseInt(((Text)data[3]).toString());
                ((IntWritable)tower_weight.get(data[0])).set(dateweight);
            }else{
                tower_weight.put(data[0], data[3]);
            }

            //bird
            birds.add((Text)data[1]);

            //wingspan
            if(((IntWritable)data[2]).get() > maxwingspan) {
                maxwingspan = ((IntWritable)data[2]).get();
                maxwingspan_tower = ((Text)data[0]).toString();
            }

        }

        outputvalue.setBirds(new ArrayWritable(Text.class, (Text[])birds.toArray()));
        outputvalue.setTower(tower_weight);
        outputvalue.setTowerId(new Text(maxwingspan_tower));
        outputvalue.setWingspan(new IntWritable(maxwingspan));

        context.write(key, outputvalue);

    }

}
