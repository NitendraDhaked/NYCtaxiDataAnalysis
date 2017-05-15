package question2;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class AverageTripDistanceMapper extends Mapper<Object, Text, Text, FloatWritable> {
	private final static FloatWritable one = new FloatWritable(1);
	private Text word = new Text();
	
	// Enums for month
	public enum Months
	{
	    JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC
	}
	// Enums for days
	public enum Days
	{
	    MON,TUE,WED,THU,FRI,SAT,SUN
	}
	
	public void map(Object key, Text value, Context context) throws IOException,
	InterruptedException {
		
			word.set(value);
			String nextLine =word.toString();
			
			//each line is splitted using comma
			String [] columns=nextLine.split(",");
		
					//total number of columns is 18 and first columns is checked to pass first row
					if(columns.length==18 && !columns[0].equals("vendor_name"))
					{
						//Pick up date time format
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try {
							//first column is converted to date
							Date date=df.parse(columns[1]);
							
							//unique key month wise distance
							Text monthkey=new Text(Months.values()[date.getMonth()]+"_Total_Distance");
							context.write(monthkey, new FloatWritable(Float.parseFloat(columns[4]))); // column 4 is distance 
							
							//Total trip in a month
							context.write(new Text(Months.values()[date.getMonth()]+"_Total_Trip"), new FloatWritable(1));
							
							//unique key for each day of week distance
							Text daykey=new Text(Days.values()[date.getDay()]+"_Day_Total_Distance");
							context.write(daykey, new FloatWritable(Float.parseFloat(columns[4]))); // column 4 is distance 
							
							//Total trip in a Day
							context.write(new Text(Days.values()[date.getDay()]+"_Day_Total_Trip_Number"), new FloatWritable(1));
							
							//Total trip in general
							context.write(new Text("Total_Trip_Number"), new FloatWritable(1));
							
							//Total distance in general
							context.write(new Text("Total_Distance"), new FloatWritable(Float.parseFloat(columns[4])));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}

			
		}
}