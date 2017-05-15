package question3;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class PaymentTypeMapper extends Mapper<Object, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();

	public void map(Object key, Text value, Context context) throws IOException,
	InterruptedException {
		
			word.set(value);
			String nextLine =word.toString();
			
			String [] columns=nextLine.split(",");
			
			//total number of columns is 18 and first columns is checked to pass first row		
					if(columns.length==18 && !columns[0].equals("vendor_name"))
					{
						//column 11 is payment type is consiered as key
						context.write(new Text(columns[11]), one);
					}

			
		}
}