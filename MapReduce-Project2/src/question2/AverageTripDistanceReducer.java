package question2;
import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class AverageTripDistanceReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {
	private IntWritable result = new IntWritable(); 
	float totalDistance = 0;
	public void reduce(Text key, Iterable<FloatWritable> values, Context context) throws
	IOException, InterruptedException { 
		 	float sumVal = 0;
		 	if(key.find("Distance")>0) //distance present in key
		 	{
		 		for (FloatWritable val : values)
				{
					sumVal+=val.get();	
				}
		 		totalDistance=sumVal;
		 		
		 	//every key is written to disk for varification
		 	context.write(key, new FloatWritable(sumVal));
		 	}
		 	else
		 	{// count for each trip
		 		for (FloatWritable val : values)
				{
					sumVal+=val.get();	
				}
		 		
		 	context.write(key, new FloatWritable(sumVal));
		 	String [] pasKey= key.toString().split("_");
		 	
		 	//Avg distance is calculated and write to disk
		 	context.write(new Text(pasKey[0]+"_Distance_Avg"), new FloatWritable(totalDistance/sumVal));
		 	}
			 
					
	 }
}