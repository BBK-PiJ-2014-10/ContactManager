import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;


public class MeetingImpl implements Meeting, Serializable {
    public static int count;
    private int id;
    private Calendar date;
    private Set<Contact> contacts = new HashSet();
    private String notes;

    public MeetingImpl() {
        count ++;
        this.id = count;
        this.date = null;
        this.notes = null;
    }

    /**
     * Returns the id of the meeting.
     *
     * @return the id of the meeting.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Return the date of the meeting.
     *
     * @return the date of the meeting.
     */
    @Override
    public Calendar getDate() {
        return this.date;
    }

    /**
     * Sets the date of the meeting.
     *
     * @param date the date of the meeting.
     */
    public void setDate(Calendar date) {
        this.date = date;

    }

    /**
     * Return the details of people that attended the meeting.
     *
     * The list contains a minimum of one contact (if there were
     * just two people: the user and the contact) and may contain an
     * arbitrary number of them. *
     *
     * @return the details of people that attended the meeting.
     */
    @Override
    public Set<Contact> getContacts() {
        return this.contacts;
    }

    /**
     * Adds the contact to the meeting.
     *
     * @param contact the contact to add to the meeting.
     */
    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    /**
     * Returns our notes about the meeting, if any.
     *
     * If we have not written anything about the meeting, the empty
     * string is returned.
     *
     * @return a string with notes about the meeting, maybe empty.
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * Add notes about the meeting.
     *
     * @param note the notes to be added
     */
    public void addNotes(String note) {
        this.notes = note;

    }
}
