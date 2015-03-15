import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


public class ContactManagerTest {

    private ContactManager contactManager;
    private Meeting testMeeting;
    private Calendar testDate = Calendar.getInstance();
    private Set<Contact> testContacts = new HashSet<Contact>();
    private String testNotes = "test notes";

    @Before
    public void setUp() {
        contactManager = new ContactManagerImpl();
        testContacts.add(new ContactImpl("test name", ""));
        testMeeting = new MeetingImpl();
        testMeeting.setContacts(testContacts);
        testMeeting.setDate(testDate);
        testMeeting.addNotes(testNotes);
    }

    @Test
    public void testAddFutureMeeting() {
        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.DAY_OF_YEAR, 1);
        int id = contactManager.addFutureMeeting(testContacts, futureDate);
        FutureMeeting futureMeeting = contactManager.getFutureMeeting(id);
        assertEquals(futureDate, futureMeeting.getDate());
        assertEquals(testContacts, futureMeeting.getContacts());
    }

    @Test
    public void testGetPastMeeting() {
        Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.DAY_OF_YEAR, -1);
        int id = contactManager.addNewPastMeeting();
        PastMeeting pastMeeting = contactManager.getPastMeeting(id);
        assertEquals(pastDate, pastMeeting.getDate());
        assertEquals(testContacts, pastMeeting.getContacts());
    }

    @Test
    public void testGetFutureMeeting() {

    }

    @Test
    public void testGetMeeting() {

    }

    @Test
    public void testGetFutureMeetingList() {

    }

    @Test
    public void testGetFutureMeetingList1() {

    }

    @Test
    public void testGetPastMeetingList() {

    }

    @Test
    public void testAddNewPastMeeting() {

    }

    @Test
    public void testAddMeetingNotes() {

    }

    @Test
    public void testAddNewContact() {

    }

    @Test
    public void testGetContacts() {

    }

    @Test
    public void testGetContacts1() {

    }
}