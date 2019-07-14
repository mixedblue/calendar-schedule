
import java.time.DayOfWeek;
import java.time.LocalDate;

public class test {
    public static void main(String[] args) {
        LocalDate lastMeeting = LocalDate.of(2019, 12, 31);
        LocalDate nextMeeting = LocalDate.now();
        int numMeetings = lastMeeting.compareTo(nextMeeting)/7;
        System.out.println(numMeetings);
    }
}
