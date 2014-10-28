import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BirdWritable implements Writable{

    private IntWritable wingspan;
    private IntWritable weigth;
    private ArrayWritable birds;

    public BirdWritable() {
        this.birds = new ArrayWritable(Text.class);
        this.wingspan = new IntWritable();
        this.weigth = new IntWritable();
    }

    public BirdWritable(IntWritable wingspan, IntWritable weight, ArrayWritable birds) {
        this.birds = birds;
        this.wingspan = wingspan;
        this.weigth = weight;
    }

    @Override
    public void readFields(DataInput in)
        throws IOException {
        birds.readFields(in);
        wingspan.readFields(in);
        weigth.readFields(in);
    }

    @Override
    public void write(DataOutput out)
            throws IOException {
        birds.write(out);
        wingspan.write(out);
        weigth.write(out);
    }
}
