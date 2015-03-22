import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Set;

import static org.junit.Assert.*;


public class PastMeetingImplTest {

    private ContactManager contactManager = new ContactManagerImpl();
    private PastMeetingImpl testMeeting = new PastMeetingImpl();
    private Contact testContact;
    private Calendar testDate = Calendar.getInstance();
    private Set<Contact> testContacts;
    private String testNotes = "test notes";

    @Before
    public void setUp() {
        contactManager.addNewContact("test name", "");
        testContacts = contactManager.getContacts("test name");
        for (Contact contact : testContacts) {
            testContact = contact;
            break;
        }
        testMeeting.addContact(testContact);
        testMeeting.setDate(testDate);
        testMeeting.addNotes(testNotes);
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
    public void testAddContact() throws Exception {
        Contact thirdContact = new ContactImpl("test name 3", "");
        testMeeting.addContact(thirdContact);
        testContacts.add(thirdContact);
        assertEquals(testContacts, testMeeting.getContacts());

    }
}