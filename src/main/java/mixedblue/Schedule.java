package mixedblue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

/**
 * This interface contains the methods needed to schedule meetings.
 */
public interface Schedule {
    /**
     * Generates a list of expected meetings
     * @param weekday the weekday the meetings are on
     * @throws IllegalArgumentException if user mistypes the weekday
     * @return a list of meetings from the next weekday until the last weekday of the year
     */
    ArrayList<> getMeetings(String weekday) throws Exception;
    /**
     * Finds the number of meetings excluding breaks
     * @param meetings list of expected meetings
     * @param breakDays the holidays and vacations
     * @param missedMeetings the holidays and vacations that are the cause for missing meetings
     * @return the number of meetings
     */
    public int findNumMeetings(ArrayList<LocalDate> meetings, Map<String, LocalDate> breakDays, Map<String, LocalDate> missedMeetings);
}
