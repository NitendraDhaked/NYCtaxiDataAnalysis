# NYCtaxiDataAnalysis
NYC taxi data analysis using Hadoop Map-Reduce programming

# Introduction:
NYC Taxi & Limousine Commission data is available for yellow, green and FHV taxi data set, this data set is freely availabe for analysis, This taxi records includes the user pickup location time, drop time, distance, number of passanger, payment type, fares and altitude of location. I am taking year 2009 data for analysis for all 12 moths data. Average number of passanger, distance per trip in general, par day , during the week ends or week days or per hour is done using the map reduce jobs. This reports includes requirement, challanges and fix.

# Requirement: Analyze “NYC Taxi & Limousine Commission” yellow trips data.
NYC yellow trip data is downloaded in csv format from  NYC site.
Format of CSV file 
vendor_name,Trip_Pickup_DateTime,Trip_Dropoff_DateTime,Passenger_Count,Trip_Distance,Sta rt_Lon,Start_Lat,Rate_Code,store_and_forward,End_Lon,End_Lat,Payment_Type,Fare_Amt,surch arge,mta_tax,Tip_Amt,Tolls_Amt,Total_Amt

# How to execute
1. Run analysis for each question
hadoop jar jarname <Main class> <Input path directory>  <output path directory>

# question1

hadoop jar nycProject2.jar question1.AvgPassengerJobControl /nycdata /ques1output

# question2

hadoop jar nycProject2.jar question2.AverageTripDistanceJobControl /nycdata /ques2output

# question3

hadoop jar nycProject2.jar question3.PaymentTypeJobControl /nycdata /ques3output

# question4

hadoop jar nycProject2.jar question4.PassengerPerHourJobControl /nycdata /ques4output

# question4a

hadoop jar nycProject2.jar question4a.DayPassengerPerHourJobControl /nycdata /ques1aoutput

# question5

hadoop jar nycProject2.jar question5.AverageTripDistanceJobControl /nycdata /ques5output

# question5a

hadoop jar nycProject2.jar question5a.AverageTripDistancePerDayJobControl /nycdata /ques5aaoutput

hadoop fs -cat /ques1output/part-r-00000



