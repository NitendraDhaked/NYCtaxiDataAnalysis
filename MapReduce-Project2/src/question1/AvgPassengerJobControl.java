package question1;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AvgPassengerJobControl {
	
//Mapper class configuration is same for all class
 public static void main(String[] args) throws Exception {
 Configuration conf = new Configuration();
 Job job = new Job();
 //Driver class 
 job.setJarByClass(AvgPassengerJobControl.class);
 
 //mapper class 
 job.setMapperClass(AvgPassengerMapper.class);
 
 //combiner class
 job.setCombinerClass(AvgPassengerCombiner.class);
 
 //reducer class
 job.setReducerClass(AvgPassengerReducer.class);
 
 //output key class
 job.setOutputKeyClass(Text.class);
 
 //output value class
 job.setOutputValueClass(IntWritable.class);
 
 //input path directory
 FileInputFormat.addInputPath(job, new Path(args[0]));
 
 //output path directory
 FileOutputFormat.setOutputPath(job, new Path(args[1]));
 System.exit(job.waitForCompletion(true) ? 0 : 1);
 
 }
}