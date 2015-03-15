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
    private String testNotes = "test notes";

    @Before
    public void setUp() {
        testContacts.add(new ContactImpl("test name", ""));
        testMeeting = new MeetingImpl(testContacts, testDate);
        testMeeting.addNotes(testNotes);
    }

    @Test
    public void testGetId() {
        assertEquals(2, testMeeting.getId());
    }

    @Test
    public void testGetDate() {
        assertEquals(testDate, testMeeting.getDate());
    }

    @Test
    public void testGetContacts() {
        assertEquals(testContacts, testMeeting.getContacts());
    }

    @Test
    public void testGetNotes() {
        assertEquals(testNotes, testMeeting.getNotes());
    }

}