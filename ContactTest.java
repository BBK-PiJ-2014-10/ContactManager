import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContactTest {

    private String testName;
    private String testNotes;
    private Contact contact;

    @Before
    public void setUp() {
        testName = "test name";
        testNotes = "test notes";
        contact = new ContactImpl(testName, testNotes);
    }

    @Test
    public void testGetId() {
        assertEquals(2, contact.getId());
    }

    @Test
    public void testsGetName() {
        assertEquals(testName, contact.getName());
    }

    @Test
    public void testGetNotes() {
        assertEquals(testNotes, contact.getNotes());
    }
}
