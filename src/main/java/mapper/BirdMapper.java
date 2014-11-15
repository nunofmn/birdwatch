package mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import java.io.IOException;


public class BirdMapper extends org.apache.hadoop.mapreduce.Mapper<Object, Text, Text, MapperOutputWritable> {

    private Text keyWordQ1Q2 = new Text();
    private Text keyWordQ3 = new Text();
    private MapperOutputWritable outputMapperQ1Q2 = new MapperOutputWritable();
    private MapperOutputWritable outputMapperQ3 = new MapperOutputWritable();

    public void map(Object key, Text value, Context context
    ) throws IOException, InterruptedException {


        final String[] totrim = value.toString().split(",");
        final String[] s = new String[totrim.length];

        for(int i=0; i<totrim.length; i++)
            s[i]=totrim[i].trim();

        if(!(s[3].equals("-1"))) {
            final String wing = (s[6].equals("2")) ? s[5] : "0";
            outputMapperQ1Q2.setQueryType(new IntWritable(0));
            outputMapperQ1Q2.setBirdid(new Text(s[3]));
            outputMapperQ1Q2.setBirdweight(new IntWritable(Integer.parseInt(s[4])));
            outputMapperQ1Q2.setTowerid(new Text(s[0]));
            outputMapperQ1Q2.setWingspan(new IntWritable(Integer.parseInt(wing)));
            keyWordQ1Q2.set(s[1]);
            context.write(keyWordQ1Q2, outputMapperQ1Q2);
        }

        if(!(s[3].equals("0")) && !(s[3].equals("-1"))) {
            outputMapperQ3.setQueryType(new IntWritable(1));
            outputMapperQ3.setDate(new Text(s[1]));
            keyWordQ3.set(s[3]); //birdId
            context.write(keyWordQ3, outputMapperQ3);
        }

    }
}
