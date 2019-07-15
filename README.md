# calendar-schedule
This project calculates how many weekly meetings for a day of the week will be scheduled in a period of time.

This is the more robust version and checks holidays for the given year as well as a way to input vacations for the user.
The MVP does not get holidays from the API. The user can input the holidays manually as vacations.
The free version of API does not provide holidays for current year, making the MVP a cheaper alternative.

The app will output the number of meetings for any given weekly meeting.

# Running the project

Building and running the project requires maven.

To build the project, run the command `mvn clean package`. The compiled jar can be found in the target folder.

To run the project, run the command `java -jar target\calendar-schedule-1.0-SNAPSHOT.jar`

Took 13 hours total

# Example input and output

```
Enter a weekday:
monday
Do you have any vacations? y/n
y
Enter name of vacation:
test
Enter numerical month of start vacation date:
12
Enter numerical day of start vacation date:
10
Enter numerical month of end vacation date:
12
Enter numerical day of end vacation date:
20
Do you have any vacations? y/n
n
Number of monday meetings until the end of the year: 23
Public holidays or vacations during meetings:
Name: Veterans Day date: 2019-11-11
Name: test day 7 date: 2019-12-16
Would you like to check another weekday? (y/n)
n
```
