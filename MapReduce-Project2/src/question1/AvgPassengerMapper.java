package question1;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class AvgPassengerMapper extends Mapper<Object, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
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
				//unique key month wise passanger
				Text monthkey=new Text(Months.values()[date.getMonth()]+"_Total_Passenger");
				context.write(monthkey, new IntWritable(Integer.parseInt(columns[3]))); //column 3 value is passanger value
				
				//Total trip in a month
				context.write(new Text(Months.values()[date.getMonth()]+"_Total_Trip"), new IntWritable(1));

				//unique key for each day passanger
				Text daykey=new Text(Days.values()[date.getDay()]+"_Day_Total_Passenger");
				context.write(daykey, new IntWritable(Integer.parseInt(columns[3])));
				
				//Total trip on particular day is intialized with one
				context.write(new Text(Days.values()[date.getDay()]+"_Day_Total_Trip"), new IntWritable(1));

				context.write(new Text("Total_Trip_Number"), new IntWritable(1));
				context.write(new Text("Total_Passenger_Avg"), new IntWritable(Integer.parseInt(columns[3])));
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}


	}
}