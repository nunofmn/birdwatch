package output;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.Progressable;
import reducer.ReducerOutputWritable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DynamoDBOutputFormat extends OutputFormat<Text, ReducerOutputWritable> {


    public static String QUERY1_TABLE = "BW-MAXWINGSPAN";
    public static String QUERY2_TABLE = "BW-SUMWEIGHT";
    public static String QUERY3_TABLE = "BW-LASTOBSERVATION";
    public static String URL = "dynamodb.us-west-2.amazonaws.com";
    public static AmazonDynamoDBClient conn = null;


    public static AmazonDynamoDBClient getDynamoDBClient() throws IOException {
        if(conn == null) {
            conn = new AmazonDynamoDBClient(new EnvironmentVariableCredentialsProvider().getCredentials());
            conn.setEndpoint(URL);
        }

        return conn;
    }

    @Override
    public RecordWriter<Text, ReducerOutputWritable> getRecordWriter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return new RecordWriter<Text, ReducerOutputWritable>() {
            @Override
            public void write(Text text, ReducerOutputWritable reducerOutputWritable)
                    throws IOException {

                Map<String, AttributeValueUpdate> updateItems = new HashMap<String, AttributeValueUpdate>();
                HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
                String value = null;
                String table = null;

                switch(reducerOutputWritable.getQuerytype().get()) {

                    case 1: //maxwingspan
                        value = reducerOutputWritable.getTowerid().toString();
                        updateItems.put("towerid", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(value)));
                        key.put("Date", new AttributeValue().withS(text.toString()));
                        table = QUERY1_TABLE;
                        break;

                    case 2: //sumweight
                        value = reducerOutputWritable.getWeight().toString();
                        updateItems.put("weight", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(value)));
                        key.put("towerdate", new AttributeValue().withS(text.toString()));
                        table = QUERY2_TABLE;
                        break;

                    case 3: //lastobservation
                        value = reducerOutputWritable.getDate().toString();
                        updateItems.put("lastseen", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(value)));
                        key.put("birdid", new AttributeValue().withS(text.toString()));
                        table = QUERY3_TABLE;
                        break;

                }

                UpdateItemRequest updateItemRequest = new UpdateItemRequest().withTableName(table).withKey(key).withReturnValues(ReturnValue.UPDATED_NEW).withAttributeUpdates(updateItems);
                UpdateItemResult result = getDynamoDBClient().updateItem(updateItemRequest);
            }

            public void close(Reporter reporter) throws IOException {

            }

            public void close(TaskAttemptContext taskAttemptContext)
                    throws IOException, InterruptedException {

            }
        };
    }

    public void checkOutputSpecs(JobContext jobContext) throws IOException, InterruptedException {

    }

    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return new OutputCommitter() {
            @Override
            public void setupJob(JobContext jobContext) throws IOException {

            }

            @Override
            public void setupTask(TaskAttemptContext taskAttemptContext) throws IOException {

            }

            @Override
            public boolean needsTaskCommit(TaskAttemptContext taskAttemptContext) throws IOException {
                return false;
            }

            @Override
            public void commitTask(TaskAttemptContext taskAttemptContext) throws IOException {

            }

            @Override
            public void abortTask(TaskAttemptContext taskAttemptContext) throws IOException {

            }
        };
    }

}
