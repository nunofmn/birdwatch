package reducer;

import mapper.MapperOutputWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BirdReducer extends Reducer<Text, MapperOutputWritable, Text, ReducerOutputWritable> {

    // Query 1 (K1,V1)
    private Text outputk1 = new Text();
    private ReducerOutputWritable outputv1 = new ReducerOutputWritable();

    // Query 2 (K2,V2)
    private Text outputk2 = new Text();
    private ReducerOutputWritable outputv2 = new ReducerOutputWritable();

    // Query 3 (K3,V3)
    private Text outputk3 = new Text();
    private ReducerOutputWritable outputv3 = new ReducerOutputWritable();


    public void reduce(Text key, Iterable<MapperOutputWritable> values, Context context)
        throws IOException, InterruptedException {

        int querytype = -1;
        int maxwingspan = 0;
        String towermaxwingspan = "";
        Map<String, Integer> towerweight = new HashMap<String, Integer>();
        Map<String, String> birdlastdate = new HashMap<String, String>();

        for(MapperOutputWritable value : values) {



            querytype = value.getQueryType().get();

            if(value.getQueryType().get() == 0) {
                //max(wingspan) -> Query 1
                if(value.getWingspan().get() > maxwingspan) {
                    maxwingspan = value.getWingspan().get();
                    towermaxwingspan = value.getTowerid().toString();
                }

                //sum(weight) -> Query 2
                String tower = value.getTowerid().toString();

                if(towerweight.containsKey(tower)) {
                    int weight = towerweight.get(tower);
                    weight = weight + value.getBirdweight().get();
                    towerweight.put(tower,weight);
                }else{
                    towerweight.put(tower, value.getBirdweight().get());
                }

            }else{
                String birdid = key.toString();

                //Bird last seen date -> Query 3
                if(birdlastdate.containsKey(birdid)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        Date lastdate = format.parse(birdlastdate.get(birdid));
                        Date currentdate = format.parse(value.getDate().toString());

                        if(currentdate.before(lastdate)) {
                            birdlastdate.put(birdid, value.getDate().toString());
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                        System.out.println("Error parsing data");
                    }

                }else{
                    birdlastdate.put(birdid, value.getDate().toString());
                }

            }


        }

        if(querytype == 0) {
            //Output (k1,v1) -> Query 1
            outputk1.set(key);
            outputv1.setQuerytype(new IntWritable(1));
            outputv1.setTowerid(new Text(towermaxwingspan));
            context.write(outputk1, outputv1);

            //Output (k2,v2) -> Query 2
            for(Map.Entry<String, Integer> entry : towerweight.entrySet()) {
                outputk2.set(key + ":" + entry.getKey());
                outputv2.setQuerytype(new IntWritable(2));
                outputv2.setWeight(new IntWritable(entry.getValue()));
                context.write(outputk2, outputv2);
            }

        }else{
            //Output (k3,v3) -> Query 3
            for(Map.Entry<String, String> entry : birdlastdate.entrySet()) {
                outputk3.set(entry.getKey());
                outputv3.setQuerytype(new IntWritable(3));
                outputv3.setDate(new Text(entry.getValue()));
                context.write(outputk3, outputv3);
            }
        }

    }

}
