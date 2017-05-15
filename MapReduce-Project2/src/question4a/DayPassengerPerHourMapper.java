package question4a;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class DayPassengerPerHourMapper extends Mapper<Object, Text, Text, FloatWritable> {
	//private final static IntWritable one = new IntWritable(4);
	private Text word = new Text();
	public void map(Object key, Text value, Context context) throws IOException,
	InterruptedException {
		//System.out.println(value);
		//StringTokenizer itr = new StringTokenizer(value.toString());
		//while (itr.hasMoreTokens()) {
			word.set(value);
			String nextLine =word.toString();
			
			String [] columns=nextLine.split(",");
								
					if(columns.length==18 && !columns[0].equals("vendor_name"))
					{
						
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try {
							
							Date date=df.parse(columns[1]);
							
							
							if(date.getDay()==0 || date.getDay()==6)  //check for Weekend days
							{
							
								//uniue key is created for weekend for per hour pasasnger
								Text columnkey=new Text("WeekEnd"+"_TimeDur_"+date.getHours()+"-"+(date.getHours()+1)+"_TripPassanger");
								context.write(columnkey, new FloatWritable(Float.parseFloat(columns[3])));
								
								//uniue key is created for weekend trip passanger 
								context.write(new Text("Week End Trip Passangers"), new FloatWritable(Float.parseFloat(columns[3])));
								
								
								context.write(new Text("Week End Trip Count"), new FloatWritable(1));
								
								//uniue key is created for weekend total trip
								context.write(new Text("WeekEnd"+"_TimeDur_"+date.getHours()+"-"+(date.getHours()+1)+"_TotalTripNumber"), new FloatWritable(1));
								
							}
							else
							{	//working days
								//uniue key is created for working daysfor per hour pasasnger
								Text columnkey=new Text("WorkingDay"+"_TimeDur_"+date.getHours()+"-"+(date.getHours()+1)+"_TripPassanger");
								context.write(columnkey, new FloatWritable(Float.parseFloat(columns[3])));
								
								//uniue key is created for working daystrip passanger
								context.write(new Text("Working Day Trip Passangers"), new FloatWritable(Float.parseFloat(columns[3])));
								
								//uniue key is created for weekend trip count
								context.write(new Text("Working Day Trip Count"), new FloatWritable(1));
								
								//uniue key is created for working days total trip
								context.write(new Text("WorkingDay"+"_TimeDur_"+date.getHours()+"-"+(date.getHours()+1)+"_TotalTripNumber"), new FloatWritable(1));
							}
							context.write(new Text("Total Number of Trip"), new FloatWritable(1));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				
			
		}
	
}