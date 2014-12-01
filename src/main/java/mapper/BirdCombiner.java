package mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
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


        String id;
        MapperOutputWritable lastdateObj = null;
        Map<String, MapperOutputWritable> q1q2 = new HashMap<String, MapperOutputWritable>();
        MapperOutputWritable outputMapperQ1Q2;
        for (MapperOutputWritable value : values) {

            if(value.getQueryType().get() == 1){
                //lastdate
                if(lastdateObj == null){
                    lastdateObj = new MapperOutputWritable();
                    lastdateObj.setQueryType(new IntWritable(value.getQueryType().get()));
                    lastdateObj.setDate(new Text(value.getDate().toString()));
                    continue;
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    Date lastdate = format.parse(lastdateObj.getDate().toString());
                    Date currentdate = format.parse(value.getDate().toString());

                    if(currentdate.after(lastdate)) { //rever
                        lastdateObj = new MapperOutputWritable();
                        lastdateObj.setQueryType(new IntWritable(value.getQueryType().get()));
                        lastdateObj.setDate(new Text(value.getDate().toString()));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println("Error parsing data");
                }
            } else {
                if (!value.getBirdid().toString().equals("0")) {
                    id = value.getTowerid().toString()+":"+value.getBirdid().toString();
                    if(!q1q2.containsKey(id)){
                        outputMapperQ1Q2 = new MapperOutputWritable();
                        outputMapperQ1Q2.setQueryType(new IntWritable(value.getQueryType().get()));
                        outputMapperQ1Q2.setWingspan(new IntWritable(value.getWingspan().get()));
                        outputMapperQ1Q2.setTowerid(new Text(value.getTowerid().toString()));
                        outputMapperQ1Q2.setBirdweight(new IntWritable(value.getBirdweight().get()));
                        outputMapperQ1Q2.setBirdid(new Text(value.getBirdid().toString()));
                        q1q2.put(id, outputMapperQ1Q2);

                    } else if(value.getWingspan().get()>q1q2.get(id).getWingspan().get()){
                        outputMapperQ1Q2 = new MapperOutputWritable();
                        outputMapperQ1Q2.setQueryType(new IntWritable(value.getQueryType().get()));
                        outputMapperQ1Q2.setWingspan(new IntWritable(value.getWingspan().get()));
                        outputMapperQ1Q2.setTowerid(new Text(value.getTowerid().toString()));
                        outputMapperQ1Q2.setBirdweight(new IntWritable(value.getBirdweight().get()));
                        outputMapperQ1Q2.setBirdid(new Text(value.getBirdid().toString()));
                        q1q2.put(id, outputMapperQ1Q2); //se o value anterior for 0 guardamos o que for inteiro
                    }
                }else if(value.getBirdid().toString().equals("0")){
                    context.write(new Text(key.toString()), value);
                }
            }
        }

        if(lastdateObj != null){
            context.write(new Text(key.toString()), lastdateObj);
        }

        if(!q1q2.isEmpty()){
            for(Map.Entry<String, MapperOutputWritable> entry : q1q2.entrySet()) {
                context.write(new Text(key.toString()), entry.getValue());
            }
        }
    }
}
