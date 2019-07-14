package mixedblue;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Scanner;

public class temp{//interface, javadocs, junit, documentation
    public static void main(String args[]) throws Exception{
        JsonArray holidayArr = findHolidays();
        boolean again = true;
        Scanner reader = new Scanner(System.in);
        while (again) {
            System.out.println("Number of meetings until the end of the year: " + findNumMeetings(holidayArr));
            again = false;
            System.out.println("Would you like to check another weekday? (y/n)");//check input
            String response = reader.next();
            if (response.equals("y")) {
                again = true;
            }
        }
        reader.close();
    }
    public static int findNumMeetings(JsonArray holidayArr) {
        String[] meetings = getMeetings();
        int numMeetings = meetings.length;
        for (JsonElement holidayElement: holidayArr) {
            JsonObject holiday = holidayElement.getAsJsonObject();
            String date = holiday.get("date").getAsString();
            if (isHolidayDuringMeeting(meetings, date)) { //country, public
                numMeetings--;
            }
            String name = holiday.get("name").getAsString(); //print holdiays
            String observed = holiday.get("observed").getAsString(); //check if same
            boolean publicDate = holiday.get("public").getAsBoolean();//which arent public
            String country = holiday.get("country").getAsString();
        }
        return numMeetings;
    }
    public static String[] getMeetings() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a weekday: ");//explain numbers, country, timezone
        int weekday = reader.nextInt();
        reader.close();

        Calendar nextWeekday = Calendar.getInstance();//timezone?
        nextWeekday.setMinimalDaysInFirstWeek(7); //mod 52 if first week //what if last week
        int currWeekday = nextWeekday.get(Calendar.DAY_OF_WEEK);
        if (weekday - currWeekday < 0) {
            nextWeekday.roll(Calendar.WEEK_OF_YEAR, true);
        }
        nextWeekday.set(Calendar.DAY_OF_WEEK, weekday);
        System.out.println("The next weekday day is: " + nextWeekday.getTime());
        int weekNextWeekday = nextWeekday.get(Calendar.WEEK_OF_YEAR);

        Calendar lastDay = Calendar.getInstance();
        lastDay.set(Calendar.DAY_OF_YEAR, lastDay.getActualMaximum(Calendar.DAY_OF_YEAR));
        int lastWeekday = lastDay.get(Calendar.DAY_OF_WEEK);
        if (weekday - lastWeekday > 0) {
            lastDay.roll(Calendar.WEEK_OF_YEAR, false);
        }
        lastDay.roll(Calendar.DAY_OF_WEEK, weekday - lastWeekday);
        System.out.println("The Last Day is: " + lastDay.getTime());
        lastDay.setMinimalDaysInFirstWeek(7);
        int weekLastDay = lastDay.get(Calendar.WEEK_OF_YEAR);
        int numWeeks = weekLastDay - weekNextWeekday + 1;
        System.out.println("Number of meetings: " + numWeeks);

        String[] meetings = new String[numWeeks];
        Calendar meeting = nextWeekday;
        for (int i = 0; i < numWeeks; i++) {
            meetings[i] = "" + meeting.get(Calendar.MONTH) + "," + meeting.get(Calendar.DATE);
            meeting.roll(Calendar.WEEK_OF_YEAR, true);
        }
        return meetings;
    }

    public static boolean isHolidayDuringMeeting(String[] meetings, String date) {
        int holidayMonth = Integer.parseInt(date.substring(5, 7).replaceFirst("^0", "")) - 1;
        int holidayDay = Integer.parseInt(date.substring(8).replaceFirst("^0", ""));
        for (int m = 0; m < meetings.length; m++) {
            String[] dates = meetings[m].split(",");
            int meetingMonth = Integer.parseInt(dates[0]);
            int meetingDay = Integer.parseInt(dates[1]);
            if (holidayMonth == meetingMonth && holidayDay == meetingDay) {
                return true;
            }
        }
        return false;
    }

    public static JsonArray findHolidays() throws Exception {
        try {
            String strUrl = "https://holidayapi.com/v1/holidays";
            String query = "key=5a386574-7dd7-42f0-8b31-9ad91656767a&country=US&year=2018";
            URL url = new URL(strUrl + "?" + query);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
            System.out.println(jsonObject.get("holidays"));
            return jsonObject.get("holidays").getAsJsonArray();

        } catch(Exception e) { //exceptions?
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
