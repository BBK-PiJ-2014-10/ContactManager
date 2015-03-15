import org.junit.Test;

import static org.junit.Assert.*;

public class ContactImplTest {

    @Test
    public void testsContactName() {
        String testName = "test name";
        String testNotes = "test notes";
        Contact contact = new ContactImpl(testName, testNotes);

        assertEquals(testName, contact.getName());
    }

    @Test
    public void testContactNotes() {
        String testName = "test name";
        String testNotes = "test notes";
        Contact contact = new ContactImpl(testName, testNotes);

        assertEquals(testNotes, contact.getNotes());
    }

    @Test
    public void testContactId() {
        String testName = "test name";
        String testNotes = "test notes";
        Contact contact = new ContactImpl(testName, testNotes);

        assertEquals(3, contact.getId());
    }
}
