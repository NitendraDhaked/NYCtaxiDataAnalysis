package question3;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class PaymentTypeJobControl {
 public static void main(String[] args) throws Exception {
 Configuration conf = new Configuration();
 Job job = new Job();
 job.setJarByClass(PaymentTypeJobControl.class);
 job.setMapperClass(PaymentTypeMapper.class);
 job.setCombinerClass(PaymentTypeCombiner.class);
 job.setReducerClass(PaymentTypeReducer.class);
 job.setOutputKeyClass(Text.class);
 job.setOutputValueClass(IntWritable.class);
 FileInputFormat.addInputPath(job, new Path(args[0]));
 FileOutputFormat.setOutputPath(job, new Path(args[1]));
 System.exit(job.waitForCompletion(true) ? 0 : 1);
 }
}