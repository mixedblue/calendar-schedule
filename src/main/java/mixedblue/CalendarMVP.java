package mixedblue;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.lang.Exception;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Finds the number of meetings on a certain weekday until the end of the year excluding holidays and vacations.
 * This implementation also considers holidays and vacations. Use this if the user has vacations and holidays that
 * interfere with the meeting day.
 * @author Sonia Thakur
 */
public class CalendarMVP implements Schedule {
    /**
     * Generates a list of expected meetings
     * @param weekday the weekday the meetings are on
     * @throws IllegalArgumentException if user mistypes the weekday
     * @return a list of meetings from the next weekday until the last weekday of the year
     */
    @Override
    public ArrayList<LocalDate> getMeetings(String weekday) throws IllegalArgumentException {
        weekday = weekday.toUpperCase().trim();
        try {
            DayOfWeek.valueOf(weekday);
        } catch(Exception IllegalArgumentException) {
            System.out.println("Weekday mistyped");
        }

        LocalDate today = LocalDate.now();
        int weekdayInt = DayOfWeek.valueOf(weekday).getValue();
        LocalDate nextMeeting = today.with(ChronoField.DAY_OF_WEEK, weekdayInt);
        if (nextMeeting.isBefore(today)) {
            nextMeeting = nextMeeting.plusWeeks(1);
        }

        LocalDate lastDay = LocalDate.of(today.getYear(), 12, 31);
        LocalDate lastMeeting = lastDay.with(ChronoField.DAY_OF_WEEK, weekdayInt);
        if (lastMeeting.isAfter(lastDay)) {
            lastMeeting = lastMeeting.minusWeeks(1);
        }

        ArrayList<LocalDate> meetings = new ArrayList<>();
        LocalDate meeting = nextMeeting;
        while (!meeting.isAfter(lastMeeting)) {
            meetings.add(meeting);
            meeting = meeting.plusWeeks(1);
        }
        return meetings;
    }

    /**
     * Finds the number of meetings excluding breaks
     * @param meetings list of expected meetings
     * @param breakDays the holidays and vacations
     * @param missedMeetings the holidays and vacations that are the cause for missing meetings
     * @return the number of meetings
     */
    @Override
    public int findNumMeetings(ArrayList<LocalDate> meetings, Map<String, LocalDate> breakDays, Map<String, LocalDate> missedMeetings) {
        int numMeetings = meetings.size();
        for (String missedName: breakDays.keySet()) {
            LocalDate date = breakDays.get(missedName);
            if (isDateDuringMeeting(meetings, date)) {
                numMeetings--;
                missedMeetings.put(missedName, date);
            }
        }
        return numMeetings;
    }

    /**
     * Checks if the holiday or vacation date is during an expected meeting
     * @param meetings list of expected meetings
     * @param date the date of a holiday or vacation
     * @return if the date is during the meeting
     */
    public boolean isDateDuringMeeting(ArrayList<LocalDate> meetings, LocalDate date) {
        for (LocalDate meeting: meetings) {
            if (date.equals(meeting)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the vacation dates to the map of break days including holidays
     * @param breakDays the dates that would cause a meeting to be missed
     * @param name the name of the vacation
     * @param startMonth the month of the start date of the vacation
     * @param startDay the day of the start date of the vacation
     * @param endMonth the month of the end date of the vacation
     * @param endDay the day of the end date of the vacation
     */
    public static void addVacations(Map<String, LocalDate> breakDays, String name, int startMonth, int startDay,
                                    int endMonth, int endDay) {
        int year = LocalDate.now().getYear();
        LocalDate startDate = LocalDate.of(year, startMonth, startDay);
        LocalDate endDate = LocalDate.of(year, endMonth, endDay);
        LocalDate vacationDay = startDate;
        int i = 1;
        while (!vacationDay.isAfter(endDate)) {
            breakDays.put(name + " day " + i, vacationDay);
            vacationDay = vacationDay.plusDays(1);
            i++;
        }
    }
}
