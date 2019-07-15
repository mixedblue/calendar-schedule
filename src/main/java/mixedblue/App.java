package mixedblue;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Map;

/**
 * This class is the main class for the App. It will run the CalendarSchedule and read in user input.
 * There are multiple inputs.
 * Enter a weekday ("monday, tuesday, wednesday ... sunday")
 * Do you have any vacations (y,n)
 * Then you would enter the month and day of start followed by month and day of end.
 * It will output the number of days of meetings from the current day until the end of the year
 */
public class App {
    public static void main(String args[]) throws Exception{
        CalendarSchedule calendar = new CalendarSchedule();
        Map<String, LocalDate> breakDays = calendar.findHolidays();
        boolean again = true;
        Scanner scanner = new Scanner(System.in);
        while (again) {
            System.out.println("Enter a weekday: ");
            String weekday = scanner.next();
            boolean askVacation = true;
            while(askVacation) {
                System.out.println("Do you have any vacations? y/n");
                String resp = scanner.next();
                if (resp.equals("y")) {
                    try {
                        System.out.println("Enter name of vacation: ");
                        String name = scanner.next();
                        System.out.println("Enter numerical month of start vacation date: ");
                        int startMonth = scanner.nextInt();
                        System.out.println("Enter numerical day of start vacation date: ");
                        int startDay = scanner.nextInt();
                        System.out.println("Enter numerical month of end vacation date: ");
                        int endMonth = scanner.nextInt();
                        System.out.println("Enter numerical day of end vacation date: ");
                        int endDay = scanner.nextInt();
                        CalendarSchedule.addVacations(breakDays, name, startMonth, startDay, endMonth, endDay);
                    } catch(InputMismatchException e) {
                        System.out.println("Please enter an integer");
                        askVacation = false;
                    }
                } else {
                    askVacation = false;
                }
            }
            Map<String, LocalDate> missedMeetings = new HashMap<>();
            System.out.println("Number of " + weekday + " meetings until the end of the year: "
                    + calendar.findNumMeetings(calendar.getMeetings(weekday), breakDays, missedMeetings));
            again = false;
            if (!missedMeetings.isEmpty()) {
                System.out.println("Public holidays or vacations during meetings: ");
            }
            for (String name: missedMeetings.keySet()) {
                System.out.println("Name: " + name + " date: " + missedMeetings.get(name));
            }
            System.out.println("Would you like to check another weekday? (y/n)");//check input
            String response = scanner.next();
            if (response.equals("y")) {
                again = true;
            }
        }
        scanner.close();
    }
}
