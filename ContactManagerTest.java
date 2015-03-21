import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


public class ContactManagerTest {

    private ContactManager contactManager = new ContactManagerImpl();
    private MeetingImpl testMeeting;
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
        testMeeting = new MeetingImpl();
        testMeeting.addContact(testContact);
        testMeeting.setDate(testDate);
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
        PastMeeting expectedPastMeeting = contactManager.getPastMeetingList(this.testContact).get(0);
        PastMeeting resultPastMeeting = contactManager.getPastMeeting(expectedPastMeeting.getId());

        assertEquals(expectedPastMeeting, resultPastMeeting);
    }

    @Test
    public void testGetFutureMeeting() {
        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.DAY_OF_YEAR, 2);
        int id = contactManager.addFutureMeeting(testContacts, futureDate);
        FutureMeeting futureMeeting = contactManager.getFutureMeeting(id);
        assertEquals(id, futureMeeting.getId());

    }

    @Test
    public void testGetMeeting() {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 2);
        int id = contactManager.addFutureMeeting(testContacts, date);
        Meeting meeting = contactManager.getMeeting(id);
        assertEquals(id, meeting.getId());
    }

    @Test
    public void testGetFutureMeetingListWithContact() {
        ContactManager contactManagerFuture = new ContactManagerImpl();

        contactManagerFuture.addNewContact("test name", "");
        testContacts = contactManagerFuture.getContacts("test name");
        Contact testContactFuture = null;
        for (Contact contact : testContacts) {
            testContactFuture = contact;
            break;
        }

        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.DATE, 1);
        int futureMeetingsNumberBefore = contactManagerFuture.getFutureMeetingList(testContactFuture).size();
        contactManagerFuture.addFutureMeeting(testContacts, futureDate);
        int futureMeetingsNumberAfter = contactManagerFuture.getFutureMeetingList(testContactFuture).size();

        assertTrue((futureMeetingsNumberBefore + 1) == futureMeetingsNumberAfter);

        Calendar futureFutureDate = Calendar.getInstance();
        futureFutureDate.add(Calendar.DATE, 2);
        contactManagerFuture.addFutureMeeting(testContacts, futureFutureDate);
        List<Meeting> futureMeetings = contactManagerFuture.getFutureMeetingList(testContactFuture);

        assertEquals(-1, futureMeetings.get(0).getDate().compareTo(futureMeetings.get(1).getDate()));
    }

    @Test
    public void testGetFutureMeetingListWithDate() {
        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.DAY_OF_YEAR, 1);
        int futureMeetingsNumberBefore = contactManager.getFutureMeetingList(futureDate).size();
        contactManager.addFutureMeeting(testContacts, futureDate);
        int futureMeetingsNumberAfter = contactManager.getFutureMeetingList(futureDate).size();

        assertTrue((futureMeetingsNumberBefore + 1) == futureMeetingsNumberAfter);

    }

    @Test
    public void testGetPastMeetingList() {
        Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.DAY_OF_YEAR, -1);
        int pastMeetingsNumberBefore = contactManager.getPastMeetingList(this.testContact).size();
        contactManager.addNewPastMeeting(testContacts, pastDate, testNotes);
        int pastMeetingsNumberAfter = contactManager.getPastMeetingList(this.testContact).size();

        assertTrue((pastMeetingsNumberBefore + 1) == pastMeetingsNumberAfter);

        Calendar pastPastDate = Calendar.getInstance();
        pastPastDate.add(Calendar.DATE, -2);
        contactManager.addNewPastMeeting(testContacts, pastPastDate, testNotes);
        List<PastMeeting> pastMeetings = contactManager.getPastMeetingList(this.testContact);

        assertEquals(-1, pastMeetings.get(0).getDate().compareTo(pastMeetings.get(1).getDate()));
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
        Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.DAY_OF_YEAR, -1);
        contactManager.addNewPastMeeting(testContacts, pastDate, "");
        List<PastMeeting> pastMeetings = contactManager.getPastMeetingList(this.testContact);
        PastMeeting pastMeeting = pastMeetings.get(pastMeetings.size() - 1);
        contactManager.addMeetingNotes(pastMeeting.getId(), this.testNotes);
        assertEquals(this.testNotes, pastMeeting.getNotes());
    }

    @Test
    public void testAddNewContact() {
        String testingName = "testing name";
        String testingNotes = "testing notes";
        contactManager.addNewContact(testingName, testingNotes);
        Set<Contact> contacts = contactManager.getContacts(testingName);
        Contact contactWithName = null;
        for (Contact contact : contacts) {
            if (contact.getName().compareTo(testingName) == 0) {
                contactWithName = contact;
                break;
            }
        }
        assertNotEquals(null, contactWithName);
        assertEquals(testingNotes, contactWithName.getNotes());
    }

    @Test
    public void testGetContactsWithID() {
        String testingName = "testing name2";
        contactManager.addNewContact(testingName, "");
        Set<Contact> contactsWithName = contactManager.getContacts(testingName);
        Contact contactWithName = null;
        for (Contact contact : contactsWithName) {
            if (contact.getName().compareTo(testingName) == 0) {
                contactWithName = contact;
                break;
            }
        }
        assertNotEquals(null, contactWithName);

        Set<Contact> contactsWithId = contactManager.getContacts(contactWithName.getId());
        Contact contactWithId = null;
        for (Contact contact : contactsWithId) {
            if (contact.getId() == contactWithName.getId()) {
                contactWithId = contact;
                break;
            }
        }
        assertNotEquals(null, contactsWithId);
        assertEquals(contactWithName, contactWithId);
    }

    @Test
    public void testGetContactsWithName() {
        String testingName = "testing name3";
        contactManager.addNewContact(testingName, "");
        Set<Contact> contactsWithName = contactManager.getContacts(testingName);
        Contact contactWithName = null;
        for (Contact contact : contactsWithName) {
            if (contact.getName().compareTo(testingName) == 0) {
                contactWithName = contact;
                break;
            }
        }
        assertNotEquals(null, contactWithName);

        Set<Contact> contactsWithId = contactManager.getContacts(contactWithName.getId());
        Contact contactWithId = null;
        for (Contact contact : contactsWithId) {
            if (contact.getId() == contactWithName.getId()) {
                contactWithId = contact;
                break;
            }
        }
        assertNotEquals(null, contactsWithId);
        assertEquals(contactWithName, contactWithId);
    }

    @Test
    public void testFlush() {
        ContactManager flushedContactManager = new ContactManagerImpl();
        flushedContactManager.addNewContact("test name", "");
        testContacts = flushedContactManager.getContacts("test name");
        Contact flushedContact = null;
        for (Contact contact : testContacts) {
            flushedContact = contact;
            break;
        }

        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.DAY_OF_YEAR, 1);
        flushedContactManager.addFutureMeeting(testContacts, futureDate);

        Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.DAY_OF_YEAR, -1);
        flushedContactManager.addNewPastMeeting(testContacts, pastDate, "test notes");

        flushedContactManager.flush();

        ContactManagerImpl loadedContactManager = new ContactManagerImpl();
        loadedContactManager.loadData();

        Set<Contact> loadedContacts = loadedContactManager.getContacts(flushedContact.getName());

        Contact loadedContact = null;
        for (Contact contact : loadedContacts) {
            loadedContact = contact;
            break;
        }

        assertEquals(flushedContact.getId(), loadedContact.getId());


        List<Meeting> flushedFutureMeetings = flushedContactManager.getFutureMeetingList(futureDate);
        List<Meeting> loadedFutureMeetings = loadedContactManager.getFutureMeetingList(futureDate);

        assertEquals(flushedFutureMeetings.get(0).getId(), loadedFutureMeetings.get(0).getId());

        List<PastMeeting> flushedPastMeetings = flushedContactManager.getPastMeetingList(flushedContact);
        List<PastMeeting> loadedPastMeetings = loadedContactManager.getPastMeetingList(loadedContact);

        assertEquals(flushedPastMeetings.get(0).getId(), loadedPastMeetings.get(0).getId());
    }
}