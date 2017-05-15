package question5a;
import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class AverageTripDistancePerDayReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {
	private FloatWritable result = new FloatWritable(); 
	float WeekEndTripDist = 0;
	float WeekDayTripDist =0;
	float totalNumberTrip=0;
	float hoursTripDistance=0;
	float hoursTripCount=0;
	float weekEndTripCount=0;
	float WeekDayTripCount=0;
	boolean flag=true;
	boolean flag1=true;
	boolean flag2=true;
	public void reduce(Text key, Iterable<FloatWritable> values, Context context) throws
	IOException, InterruptedException {
	
		 		float sumVal = 0;
		 		if(key.find("TotalTripNumber")>0) ////total trip for every hour
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
					 //Trip distance average is calculaed for each hour of weekend and week days
					if(key.find("WeekEnd")==0 ||key.find("WorkingDay")==0)
						context.write(new Text(key+"_Avg"),new FloatWritable(sumVal/hoursTripCount));
					else
						context.write(key,new FloatWritable(sumVal));
				}
				
				if(key.equals(new Text("Week End Trip Distance")))
				{		
				//Week end total trip distance
					WeekEndTripDist=sumVal;
				}
				if(key.equals(new Text("Week End Trip Count")))
				{	
					//Week end total trip count
					weekEndTripCount=sumVal;
				}
				
				if(key.equals(new Text("Working Day Trip Distance")))
				{
					//Week end total trip distance
					WeekDayTripDist=sumVal;
				}
				if(key.equals(new Text("Working Day Trip Count")))
				{
					//Week end total trip count
					weekEndTripCount=sumVal;
				}
				
				if(key.equals(new Text("Total Number of Trip")))
				{
					//total numbber of trip in general
					totalNumberTrip=sumVal;
				}
				if(flag1 && WeekEndTripDist>0 && weekEndTripCount>0)
				{
					flag1=false;
					//Weekend Average trip distance is calualted and write to disk
					context.write(new Text("Week End Average Trip Distance") ,new FloatWritable(WeekEndTripDist/weekEndTripCount));
				}
				if(flag2 && WeekDayTripDist>0 && WeekDayTripCount>0)
				{
					flag2=false;
					//Week days Average trip distance is calualted and write to disk
					context.write(new Text("Week Day Average Trip Distance") ,new FloatWritable(WeekDayTripDist/WeekDayTripCount));
				}
				
				if(flag && WeekEndTripDist>0 && WeekDayTripDist>0 &&totalNumberTrip>0)
				{
					flag=false;
					float totalTripDistance = WeekDayTripDist+WeekEndTripDist;
					//Average trip distance in general is calualted and write to disk
					context.write(new Text("Average Trip Distance") ,new FloatWritable(totalTripDistance/totalNumberTrip));
				}			
	 }
}