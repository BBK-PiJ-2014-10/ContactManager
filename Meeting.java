import java.util.Calendar;
import java.util.Set;

/**
 * A class to represent meetings
 *
 * Meetings have unique IDs, scheduled date and a list of participating contacts
 */
public interface Meeting {
    /**
     * Returns the id of the meeting.
     *
     * @return the id of the meeting.
     */
    int getId();

    /**
     * Return the date of the meeting.
     *
     * @return the date of the meeting.
     */
    Calendar getDate();

    /**
     * Sets the date of the meeting.
     *
     * @param date  the date of the meeting.
     */
    void setDate(Calendar date);

    /**
     * Return the details of people that attended the meeting.
     *
     * The list contains a minimum of one contact (if there were
     * just two people: the user and the contact) and may contain an
     * arbitrary number of them. *
     * @return the details of people that attended the meeting.
     */
    Set<Contact> getContacts();


    /**
     * Adds the set of contacts to the meeting.
     *
     * @param contacts set of contacts to add to the meeting.
     */
    void setContacts(Set<Contact> contacts);

    /**
     * Adds the contact to the meeting.
     *
     * @param contact the contact to add to the meeting.
     */
    void addContact(Contact contact);

    /**
     * Returns our notes about the meeting, if any.
     *
     * If we have not written anything about the meeting, the empty
     * string is returned.
     *
     * @return a string with notes about the meeting, maybe empty.
     */
    String getNotes();

    /**
     * Add notes about the meeting.
     *
     * @param note the notes to be added
     */
    void addNotes(String note);
}
