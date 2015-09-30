package eis.lab.groupb;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;



public class ProjectionReducer extends Reducer<Text, Text, Text, Text> 
{
	protected void setup(Context context) throws IOException, InterruptedException 
	{
		Configuration conf=context.getConfiguration();
		 String trial = conf.get("namescolumns");
	    context.write(new Text(trial), new Text(""));            
	  }
	
	
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException,
			InterruptedException 
			{
//		String a = "";
		context.write(key, null);
	}
}