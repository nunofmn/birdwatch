package reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ReducerOutputWritable implements Writable {

    private Text towerid;
    private IntWritable weight;
    private Text date;
    private IntWritable querytype;

    public ReducerOutputWritable() {
        this.towerid = new Text();
        this.weight = new IntWritable();
        this.date = new Text();
        this.querytype = new IntWritable(-1);
    }

    public ReducerOutputWritable(Text tower_id, IntWritable weight, Text date, int querytype) {
        this.towerid = tower_id;
        this.weight = weight;
        this.date = date;
        this.querytype = new IntWritable(-1);
    }

    public Text getTowerid() {
        return towerid;
    }

    public void setTowerid(Text towerid) {
        this.towerid = towerid;
    }

    public IntWritable getWeight() {
        return weight;
    }

    public void setWeight(IntWritable weight) {
        this.weight = weight;
    }

    public Text getDate() {
        return date;
    }

    public void setDate(Text date) {
        this.date = date;
    }

    public IntWritable getQuerytype() {
        return querytype;
    }

    public void setQuerytype(IntWritable querytype) {
        this.querytype = querytype;
    }

    @Override
    public String toString() {

        String result = "";

        if(querytype.get() == 1) {
            result = towerid.toString();
        }else if(querytype.get() == 2) {
            result = weight.toString();
        }else if(querytype.get() == 3) {
            result = date.toString();
        }

        return result;
    }

    @Override
    public void write(DataOutput out) throws IOException {
            towerid.write(out);
            weight.write(out);
            date.write(out);
            querytype.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
            towerid.readFields(in);
            weight.readFields(in);
            date.readFields(in);
            querytype.readFields(in);
    }
}
