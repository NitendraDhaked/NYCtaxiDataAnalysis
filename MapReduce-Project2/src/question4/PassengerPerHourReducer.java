package question4;
import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class PassengerPerHourReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {
	private FloatWritable result = new FloatWritable(); 
	float tripPassenger = 0;
	float totalNumberTrip=0;
	float hoursTripCount=0;
	boolean flag=true;
	public void reduce(Text key, Iterable<FloatWritable> values, Context context) throws
	IOException, InterruptedException {
	
		 		float sumVal = 0;
		 		if(key.find("TotalTripNumber")>0) //total trip for every hour
		 		{
		 			for (FloatWritable val : values)
					{
						sumVal+=val.get();
						
					}
		 			hoursTripCount=sumVal;
		 		}
				if(key.find("TotalTripNumber")==-1) // keys other than total trip
				{
					
					for (FloatWritable val : values)
					{
						sumVal+=val.get();		
					}
					if(key.find("TripPassanger")>0) //Trip passager average is calculaed for each hour
						context.write(new Text(key+"_Avg"),new FloatWritable(sumVal/hoursTripCount));
					else
						context.write(key,new FloatWritable(sumVal));
				}
				
				if(key.equals(new Text("Total Trip Passanger")))
				{	
					//Total trip passanger
					tripPassenger=sumVal;
				}
				if(key.equals(new Text("Total Number of Trip")))
				{
					//Total number of trip
					totalNumberTrip=sumVal;
				}
				if(flag && tripPassenger>0 &&totalNumberTrip>0)
				{
					flag=false;
					//Average trip passanger
					context.write(new Text("Average Trip Passanger") ,new FloatWritable(tripPassenger/totalNumberTrip));
				}

			
	 }
}