package output;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;

import java.io.IOException;

public class DynamoDBOutputFormat extends OutputFormat<Text, Text>{


    public static String QUERY1_TABLE = "BW-MAXWINGSPAN";
    public static String QUERY2_TABLE = "BW-SUMWEIGHT";
    public static String QUERY3_TABLE = "BW-LASTOBSERVATION";
    public static AmazonDynamoDBClient conn = null;


    public static AmazonDynamoDBClient getDynamoDBClient() throws IOException {
        if(conn == null) {
            conn = new AmazonDynamoDBClient();
        }

        return conn;
    }

    @Override
    public RecordWriter<Text, Text> getRecordWriter(TaskAttemptContext taskAttemptContext)
            throws IOException, InterruptedException {
        return new RecordWriter<Text, Text>() {
            @Override
            public void write(Text text, Text text2)
                    throws IOException, InterruptedException {

            }

            @Override
            public void close(TaskAttemptContext taskAttemptContext)
                    throws IOException, InterruptedException {

            }
        };
    }

    @Override
    public void checkOutputSpecs(JobContext jobContext) throws IOException, InterruptedException {

    }

    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return null;
    }
}
