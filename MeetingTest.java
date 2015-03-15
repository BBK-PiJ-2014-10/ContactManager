import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Set;

import static org.junit.Assert.*;

public class MeetingTest {

    private Calendar testDate;
    private Meeting testMeeting;
    private Set<Contact> testContacts;

    @Before
    public void setUp() {
        testMeeting = new MeetingImpl(testContacts, testDate);
    }

    @Test
    public void testGetId() {
        assertEquals(1, testMeeting.getId());
    }

    @Test
    public void testGetDate() {
        assertEquals(testDate, testMeeting.getDate());
    }

    @Test
    public void testGetContacts() {
        assertEquals(testContacts, testMeeting.getContacts());
    }
}