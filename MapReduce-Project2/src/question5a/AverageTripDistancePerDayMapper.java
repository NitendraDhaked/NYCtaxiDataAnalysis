package question5a;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class AverageTripDistancePerDayMapper extends Mapper<Object, Text, Text, FloatWritable> {
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
							if(date.getDay()==0 || date.getDay()==6)  //Weekend 
							{
								Text columnkey=new Text("WeekEnd"+"_TimeDur_"+date.getHours()+"-"+(date.getHours()+1)+"_TripDistance");
								context.write(columnkey, new FloatWritable(Float.parseFloat(columns[4])));
								context.write(new Text("Week End Trip Distance"), new FloatWritable(Float.parseFloat(columns[4])));
								context.write(new Text("Week End Trip Count"), new FloatWritable(1));
								context.write(new Text("WeekEnd"+"_TimeDur_"+date.getHours()+"-"+(date.getHours()+1)+"_TotalTripNumber"), new FloatWritable(1));
								
							}
							else
							{	//working days
								Text columnkey=new Text("WorkingDay"+"_TimeDur_"+date.getHours()+"-"+(date.getHours()+1)+"_TripDistance");
								context.write(columnkey, new FloatWritable(Float.parseFloat(columns[4])));
								context.write(new Text("Working Day Trip Count"), new FloatWritable(1));
								context.write(new Text("Working Day Trip Distance"), new FloatWritable(Float.parseFloat(columns[4])));
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