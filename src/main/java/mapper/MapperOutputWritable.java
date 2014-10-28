package mapper;

import org.apache.hadoop.io.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MapperOutputWritable implements Writable{



    private IntWritable wingspan;
    private IntWritable birdweight;
    private Text towerid;
    private Text birdid;
    private Text date;
    private IntWritable queryType;

    public MapperOutputWritable() {
        this.birdid = new Text();
        this.wingspan = new IntWritable();
        this.birdweight = new IntWritable();
        this.towerid = new Text();
        this.queryType = new IntWritable();
        this.date = new Text();
    }

    public MapperOutputWritable(IntWritable birdweight, IntWritable wingspan, IntWritable weight, Text birdid, Text towerid, IntWritable queryType, Text date) {
        this.birdweight = birdweight;
        this.birdid = birdid;
        this.wingspan = wingspan;
        this.towerid = towerid;
        this.date = date;
        this.queryType = queryType;
    }

    public IntWritable getWingspan() {
        return wingspan;
    }

    public void setWingspan(IntWritable wingspan) {
        this.wingspan = wingspan;
    }

    public IntWritable getBirdweight() {
        return birdweight;
    }

    public void setBirdweight(IntWritable birdweight) {
        this.birdweight = birdweight;
    }

    public Text getTowerid() {
        return towerid;
    }

    public void setTowerid(Text towerid) {
        this.towerid = towerid;
    }

    public Text getBirdid() {
        return birdid;
    }

    public void setBirdid(Text birdid) {
        this.birdid = birdid;
    }

    public Text getDate() {
        return date;
    }

    public void setDate(Text date) {
        this.date = date;
    }

    public IntWritable getQueryType() {
        return queryType;
    }

    public void setQueryType(IntWritable queryType) {
        this.queryType = queryType;
    }


    @Override
    public void readFields(DataInput in)
        throws IOException {
         wingspan.readFields(in);
        birdweight.readFields(in);
        towerid.readFields(in);
        birdid.readFields(in);
        date.readFields(in);
        queryType.readFields(in);
    }

    @Override
    public void write(DataOutput out)
            throws IOException {
        wingspan.write(out);
        birdweight.write(out);
        towerid.write(out);
        birdid.write(out);
        date.write(out);
        queryType.write(out);
    }
}
