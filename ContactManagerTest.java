import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


public class ContactManagerTest {

    private ContactManager contactManager = new ContactManagerImpl();
    private Meeting testMeeting;
    private Contact testContact = new ContactImpl("test name", "");
    private Calendar testDate = Calendar.getInstance();
    private Set<Contact> testContacts = new HashSet<Contact>();
    private String testNotes = "test notes";

    @Before
    public void setUp() {
        testContacts.add(testContact);
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
        contactManager.addNewPastMeeting(testContacts, pastDate, testNotes);
        PastMeeting expectedPastMeeting = contactManager.getPastMeetingList(this.testContact).get(-1);
        PastMeeting resultPastMeeting = contactManager.getPastMeeting(expectedPastMeeting.getId());

        assertEquals(expectedPastMeeting, resultPastMeeting);
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
        ContactManager testContactManager = new ContactManagerImpl();
        Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.DAY_OF_YEAR, -1);
        int pastMeetingsNumberBefore = testContactManager.getPastMeetingList(this.testContact).size();
        testContactManager.addNewPastMeeting(testContacts, pastDate, testNotes);
        int pastMeetingsNumberAfter = testContactManager.getPastMeetingList(this.testContact).size();

        assertTrue((pastMeetingsNumberBefore + 1) == pastMeetingsNumberAfter);
    }

    @Test
    public void testAddNewPastMeeting() {
        Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.DAY_OF_YEAR, -1);
        int pastMeetingsNumberBefore = contactManager.getPastMeetingList(this.testContact).size();
        contactManager.addNewPastMeeting(testContacts, pastDate, testNotes);
        int pastMeetingsNumberAfter = contactManager.getPastMeetingList(this.testContact).size();

        assertTrue((pastMeetingsNumberBefore + 1) == pastMeetingsNumberAfter);
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