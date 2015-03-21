import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


public class FutureMeetingTest {

    private FutureMeetingImpl testMeeting = new FutureMeetingImpl();
    private Calendar testDate = Calendar.getInstance();
    private Set<Contact> testContacts = new HashSet<Contact>();
    private String testNotes = "test notes";

    @Before
    public void setUp() {
        testContacts.add(new ContactImpl("test name", ""));
        testMeeting.setContacts(testContacts);
        testMeeting.setDate(testDate);
        testMeeting.addNotes(testNotes);
    }

    @Test
    public void testGetId() {
        assertEquals(36, testMeeting.getId());
    }

    @Test
    public void testGetDate() {
        assertEquals(testDate, testMeeting.getDate());
    }

    @Test
    public void testSetDate() {
        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.DAY_OF_YEAR, 1);
        testMeeting.setDate(futureDate);
        assertEquals(futureDate, testMeeting.getDate());
    }

    @Test
    public void testGetContacts() {
        assertEquals(testContacts, testMeeting.getContacts());
    }

    @Test
    public void testGetNotes() {
        assertEquals(testNotes, testMeeting.getNotes());
    }


    @Test
    public void testSetContacts() throws Exception {
        testContacts.add(new ContactImpl("test name 2", ""));
        testMeeting.setContacts(testContacts);
        assertEquals(testContacts, testMeeting.getContacts());
    }

    @Test
    public void testAddContact() throws Exception {
        Contact thirdContact = new ContactImpl("test name 3", "");
        testMeeting.addContact(thirdContact);
        testContacts.add(thirdContact);
        assertEquals(testContacts, testMeeting.getContacts());

    }
}