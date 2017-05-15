package question4a;
import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class DayPassengerPerHourReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {
	private FloatWritable result = new FloatWritable(); 
	float WeekEndTripPass = 0;
	float WeekDayTripPass =0;
	float totalNumberTrip=0;
	float hoursTripCount=0;
	float weekEndTripCount=0;
	float WeekDayTripCount=0;
	boolean flag=true;
	boolean flag1=true;
	boolean flag2=true;
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
		 			//context.write(key,new FloatWritable(sumVal));
		 		}
				if(key.find("TotalTripNumber")==-1) // keys other than total trip
				{
					
					for (FloatWritable val : values)
					{
						sumVal+=val.get();		
					}
					if(key.find("WeekEnd")==0 ||key.find("WorkingDay")==0)
					{
						 //Trip passager average is calculaed for each hour of weekend and week days 
						context.write(new Text(key+"_Avg"),new FloatWritable(sumVal/hoursTripCount));
					}
					else
						context.write(key,new FloatWritable(sumVal));
				}
				
				if(key.equals(new Text("Week End Trip Passangers")))
				{	
					//Week end total trip passanger
					WeekEndTripPass=sumVal;
				}
				if(key.equals(new Text("Week End Trip Count")))
				{	
					//week end total trip counts
					weekEndTripCount=sumVal;
				}
				
				if(key.equals(new Text("Working Day Trip Passangers")))
				{
					//Week day total trip passanger
					WeekDayTripPass=sumVal;
				}
				if(key.equals(new Text("Working Day Trip Count")))
				{
					//Week day total trip count
					WeekDayTripCount=sumVal;
				}
				
				if(key.equals(new Text("Total Number of Trip")))
				{
					//Total number of trip in general
					totalNumberTrip=sumVal;
				}
				if(flag1 && WeekEndTripPass>0 && weekEndTripCount>0)
				{
					flag1=false;
					//Week end trip passanger is calulated and saved to disk
					context.write(new Text("Week End Average Trip Passanger") ,new FloatWritable(WeekEndTripPass/weekEndTripCount));
				}
				if(flag2 && WeekDayTripPass>0 && WeekDayTripCount>0)
				{
					flag2=false;
					//Week day trip passanger is calulated and saved to disk
					context.write(new Text("Week Day Average Trip Passanger") ,new FloatWritable(WeekDayTripPass/WeekDayTripCount));
				}
				if(flag && WeekEndTripPass>0 && WeekDayTripPass>0 &&totalNumberTrip>0)
				{
					flag=false;
					float totalTripDistance = WeekDayTripPass+WeekEndTripPass;
					//Average passanger in general is calulated and saved to disk
					context.write(new Text("Average Trip Passanger") ,new FloatWritable(totalTripDistance/totalNumberTrip));
				}
				
	 }
}