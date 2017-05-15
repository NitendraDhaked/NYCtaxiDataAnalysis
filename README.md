# NYCtaxiDataAnalysis
NYC taxi data analysis using Hadoop Map-Reduce programming

# Introduction:
NYC Taxi & Limousine Commission data is available for yellow, green and FHV taxi data set, this data 
set is freely availabe for analysis, This taxi records includes the user pickup location time, drop time, distance, number of passanger, payment type, fares and altitude of location.Average number of
passanger, distance per trip in general, par day , during the week ends or week days or per hour is 
done using the map reduce jobs. This reports includes requirement, challanges and fix.

# Requirement: Analyze “NYC Taxi & Limousine Commission” yellow trips data.

NYC yellow trip data is downloaded in csv format from  NYC site.

Format of CSV file 

vendor_name,Trip_Pickup_DateTime,Trip_Dropoff_DateTime,Passenger_Count,Trip_Distance,Sta rt_Lon,Start_Lat,Rate_Code,store_and_forward,End_Lon,End_Lat,Payment_Type,Fare_Amt,surch arge,mta_tax,Tip_Amt,Tolls_Amt,Total_Amt

# Script for Data Download

#!/bin/bash

months=( "01" "02" "03" "04" "05" "06" "07" "08" "09" "10" "11" "12" );

colors=( ""yellow");

for year in {2009..2010}

do

for month in "${months[@]}" 

do 

for color in "${colors[@]}" 

do 

wget "https://s3.amazonaws.com/nyc­ data/"$color"_tripdata_"$year"­"$month".csv"; 

done 

done 

done

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



# Conclusions: 

Average distance in night During the weekdays is high and low for weekends and average distance during the day or till midnight for weekends is high but for weekdays is low. Average distance in general, is almost similar to the average distance during the weekday.Another study for an average passenger on weekend is high compared to the Average passenger in general or average passenger weekdays. We can also conclude that average passenger in General, is higher than average passenger 
weekdays. Most of the transaction is done using a credit card and some passenger is doing their 
payment by cash. Cloud is good for adding the new machine in the cluster and easy to change the size
of machine and data disk, because of lack of sources, I am not able to add all 8 years data for analysis 
but data is inserted for all 8 years.

# Technology used

Azure Cloud

Cluster Setup using Virtual Machines

Map-Reduce in Java
