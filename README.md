# NYCtaxiDataAnalysis
NYC taxi data analysis using Hadoop Map-Reduce programming
# How to execute
1. Run analysis for each question
hadoop jar jarname <Main class> <Input path directory>  <output path directory>

#question1
hadoop jar nycProject2.jar question1.AvgPassengerJobControl /nycdata /ques1output

#question2
hadoop jar nycProject2.jar question2.AverageTripDistanceJobControl /nycdata /ques2output

#question3
hadoop jar nycProject2.jar question3.PaymentTypeJobControl /nycdata /ques3output

#question4
hadoop jar nycProject2.jar question4.PassengerPerHourJobControl /nycdata /ques4output

#question4a
hadoop jar nycProject2.jar question4a.DayPassengerPerHourJobControl /nycdata /ques1aoutput

#question5
hadoop jar nycProject2.jar question5.AverageTripDistanceJobControl /nycdata /ques5output

#question5a
hadoop jar nycProject2.jar question5a.AverageTripDistancePerDayJobControl /nycdata /ques5aaoutput

hadoop fs -cat /ques1output/part-r-00000



