package question5;
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
							
								Text columnkey=new Text("TimeDur_"+date.getHours()+"-"+(date.getHours()+1)+"_TripDistance");
								context.write(columnkey, new FloatWritable(Float.parseFloat(columns[4])));
								context.write(new Text("Trip Distance"), new FloatWritable(Float.parseFloat(columns[4])));
								context.write(new Text("TimeDur_"+date.getHours()+"-"+(date.getHours()+1)+"_TotalTripNumber"), new FloatWritable(1));
								context.write(new Text("Total Number of Trip"), new FloatWritable(1));
							}		
						catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				
			
		}
	
}