import org.apache.hadoop.io.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class BirdWritable implements Writable{


    private MapWritable towerweight;
    private IntWritable wingspan;
    private Text towerid;
    private ArrayWritable birds;

    public BirdWritable() {
        this.towerweight = new MapWritable();
        this.birds = new ArrayWritable(Text.class);
        this.wingspan = new IntWritable();
        this.towerid = new Text();
    }

    public BirdWritable(MapWritable towerweight, IntWritable wingspan, IntWritable weight, ArrayWritable birds, Text towerid) {
        this.towerweight = towerweight;
        this.birds = birds;
        this.wingspan = wingspan;
        this.towerid = towerid;
    }

    public Text getTowerId() {
        return towerid;
    }

    public void setTowerId(Text towerid) {
        this.towerid = towerid;
    }

    public MapWritable getTower() {
        return towerweight;
    }

    public void setTower(MapWritable towerweigth) {
        this.towerweight = towerweigth;
    }

    public IntWritable getWingspan() {
        return wingspan;
    }

    public void setWingspan(IntWritable wingspan) {
        this.wingspan = wingspan;
    }

    public ArrayWritable getBirds() {
        return birds;
    }

    public void setBirds(ArrayWritable birds) {
        this.birds = birds;
    }

    @Override
    public void readFields(DataInput in)
        throws IOException {
        towerweight.readFields(in);
        birds.readFields(in);
        wingspan.readFields(in);
    }

    @Override
    public void write(DataOutput out)
            throws IOException {
        towerweight.write(out);
        birds.write(out);
        wingspan.write(out);
    }
}
