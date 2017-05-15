package question1;
import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class AvgPassengerReducer extends Reducer<Text, IntWritable, Text, FloatWritable> {
	private IntWritable result = new IntWritable(); 
	float totalPassenger = 0;
	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws
	IOException, InterruptedException { 
		 	int sumVal = 0;
		 	if(key.find("Passenger")>0) //if passanger is present in key
		 	{
		 		for (IntWritable val : values)
				{
					sumVal+=val.get();	
				}
		 		totalPassenger=sumVal; //total passanger
		 		
		 	//every key is written to disk for varification
		 	context.write(key, new FloatWritable(sumVal));
		 	}
		 	else
		 	{//passanger count
		 		for (IntWritable val : values)
				{
					sumVal+=val.get();	
				}
		 		
		 	context.write(key, new FloatWritable(sumVal)); // passanger count with key write on disk
		 	String [] pasKey= key.toString().split("_");
		 	
		 	//Average passanger is calculated and write to disk
		 	context.write(new Text(pasKey[0]+"_Passenger_Avg"), new FloatWritable(totalPassenger/sumVal));
		 	}
			 
					
	 }
}