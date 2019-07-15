package mixedblue;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class CalendarScheduleTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CalendarScheduleTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(CalendarScheduleTest.class);
    }

    /**
     * Rigourous Test
     */
    public void testApp() throws Exception{
        CalendarSchedule calendar = new CalendarSchedule();
        Map<String, LocalDate> breakDays = calendar.findHolidays();
        String weekday = "Wednesday";
        CalendarSchedule.addVacations(breakDays, "Japan", 7, 13, 7, 27);
        Map<String, LocalDate> missedMeetings = new HashMap<>();
        int numMeetings = calendar.findNumMeetings(calendar.getMeetings(weekday), breakDays, missedMeetings);
        assertEquals(21, numMeetings);
    }

    public void testAppMVP() throws Exception{
        CalendarMVP calendar = new CalendarMVP();
        Map<String, LocalDate> breakDays = new HashMap<String, LocalDate>();
        String weekday = "Wednesday";
        CalendarSchedule.addVacations(breakDays, "Japan", 7, 13, 7, 27);
        Map<String, LocalDate> missedMeetings = new HashMap<>();
        int numMeetings = calendar.findNumMeetings(calendar.getMeetings(weekday), breakDays, missedMeetings);
        assertEquals(22, numMeetings);
    }
}
