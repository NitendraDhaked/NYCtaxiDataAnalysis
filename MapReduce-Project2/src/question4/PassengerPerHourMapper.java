package question4;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class PassengerPerHourMapper extends Mapper<Object, Text, Text, FloatWritable> {
	//private final static IntWritable one = new IntWritable(4);
	private Text word = new Text();
	public void map(Object key, Text value, Context context) throws IOException,
	InterruptedException {
		word.set(value);
		String nextLine =word.toString();

		String [] columns=nextLine.split(",");

		//total number of columns is 18 and first columns is checked to pass first row
		if(columns.length==18 && !columns[0].equals("vendor_name"))
		{

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {

				Date date=df.parse(columns[1]);

				//uniue key is crested for each time duration
				Text columnkey=new Text("TimeDur_"+date.getHours()+"-"+(date.getHours()+1)+"_TripPassanger");
				context.write(columnkey, new FloatWritable(Float.parseFloat(columns[3])));
				
				//Total trip passanger key is created for total passanger count
				context.write(new Text("Total Trip Passanger"), new FloatWritable(Float.parseFloat(columns[3])));
				
				//Total trip for each hour
				context.write(new Text("TimeDur_"+date.getHours()+"-"+(date.getHours()+1)+"_TotalTripNumber"), new FloatWritable(1));
				
				//Total trip in general
				context.write(new Text("Total Number of Trip"), new FloatWritable(1));
			}		
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


	}

}