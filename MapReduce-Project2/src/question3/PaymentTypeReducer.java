package question3;
import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class PaymentTypeReducer extends Reducer<Text, IntWritable, Text, IntWritable> { 
	int cash=0;
	int credit=0;
	boolean flag=true;
	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws
	IOException, InterruptedException { 
		int sumVal = 0;
		for (IntWritable val : values)
		{
			sumVal+=val.get();	
		}
		//cash is present in lower and upper case is added
		if(key.toString().equalsIgnoreCase("cash"))
		{
			cash+=sumVal;

		}
		//credit is present in lower and upper case is added
		else if(key.toString().equalsIgnoreCase("credit"))
		{
			credit+=sumVal;

		}
		else
		{
			if(flag)
			{
				//Both cash and credit is write to disk with value
				context.write(new Text("CASH"), new IntWritable(cash));
				context.write(new Text("CREDIT"), new IntWritable(credit));
				flag=false;
			}
			// other keys with value
			context.write(key, new IntWritable(sumVal));
		}
	}
}