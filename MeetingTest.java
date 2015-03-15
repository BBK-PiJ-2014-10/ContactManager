import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.*;

public class MeetingTest {

    private Meeting testMeeting;
    private Calendar testDate = Calendar.getInstance();
    private Set<Contact> testContacts = new HashSet<Contact>();

    @Before
    public void setUp() {
        testContacts.add(new ContactImpl("test name", ""));
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