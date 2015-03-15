import java.util.*;


public class ContactManagerImpl implements ContactManager {

    private HashMap<Integer, Meeting> meetings = new HashMap();

    /**
     * Check if date is in the future.
     *
     * @param date date to check.
     * @return true if date is in the future.
     */
    private boolean dateIsInTheFuture(Calendar date) {
        Calendar currentDate = Calendar.getInstance();
        if (currentDate.compareTo(date) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add a new meeting to be held in the future.
     *
     * @param contacts a list of contacts that will participate in the meeting
     * @param date     the date on which the meeting will take place
     * @return the ID for the meeting
     * @throws IllegalArgumentException if the meeting is set for a time in the past,
     * of if any contact is unknown / non-existent
     * @throws NullPointerException if any of the arguments is null
     */
    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        if ((contacts == null) || (date == null)) {
            throw new NullPointerException("Null argument provided.");
        }

        if (dateIsInTheFuture(date)) {
            FutureMeeting futureMeeting = new FutureMeetingImpl();
            futureMeeting.setDate(date);
            futureMeeting.setContacts(contacts);

            int futureMeetingId = futureMeeting.getId();
            this.meetings.put(futureMeetingId, futureMeeting);

            return futureMeetingId;
        } else {
            throw new IllegalArgumentException("Past date was provided.");
        }
    }

    /**
     * Returns the PAST meeting with the requested ID, or null if it there is none.
     *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     * @throws IllegalArgumentException if there is a meeting with that ID happening in the future
     */
    @Override
    public PastMeeting getPastMeeting(int id) {
        Meeting meeting = meetings.get(id);

        if (meeting == null) {
            return null;
        } else {
            if (!dateIsInTheFuture(meeting.getDate())) {
                return (PastMeeting)meeting;
            } else {
                throw new IllegalArgumentException("There is a meeting with that ID happening in the future.");
            }

        }
    }

    /**
     * Returns the FUTURE meeting with the requested ID, or null if there is none.
     *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     * @throws IllegalArgumentException if there is a meeting with that ID happening in the past
     */
    @Override
    public FutureMeeting getFutureMeeting(int id) {
        Meeting meeting = meetings.get(id);

        if (meeting == null) {
            return null;
        } else {
            if (dateIsInTheFuture(meeting.getDate())) {
                return (FutureMeeting)meeting;
            } else {
                throw new IllegalArgumentException("There is a meeting with that ID happening in the past.");
            }

        }
    }

    /**
     * Returns the meeting with the requested ID, or null if it there is none.
     *
     * @param id the ID for the meeting.
     * @return the meeting with the requested ID, or null if it there is none.
     */
    @Override
    public Meeting getMeeting(int id) {
        return meetings.get(id);
    }

    /**
     * Returns the list of future meetings scheduled with this contact
     *
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any
     * duplicates.
     *
     * @param contact one of the user’s contacts
     * @return the list of future meeting(s) scheduled with this contact
     * @throws IllegalArgumentException if the contact does not exist
     */
    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {

        // TODO: Check that contact exists
        List<Meeting> futureMeetings = new ArrayList<Meeting>();

        for(Map.Entry<Integer, Meeting> meetingEntry: this.meetings.entrySet()){
            Meeting meeting = meetingEntry.getValue();

            Set<Contact> meetingContacts = meeting.getContacts();

            if (meetingContacts.contains(contact)) {
                if (dateIsInTheFuture(meeting.getDate())) {
                    futureMeetings.add(meeting);
                }
            }
        }

        return futureMeetings;
    }

    /**
     * Returns the list of meetings that are scheduled for, or that took
     * place on, the specified date. Could use a better name, but it
     * implements an interface.
     *
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any
     * duplicates.
     *
     * @param date the date
     * @return the list of meetings
     */
    @Override
    public List<Meeting> getFutureMeetingList(Calendar date) {

        // TODO: Add sorting
        List<Meeting> meetingsOnADate = new ArrayList<Meeting>();

        for(Map.Entry<Integer, Meeting> meetingEntry: this.meetings.entrySet()){
            Meeting meeting = meetingEntry.getValue();

            if (date.compareTo(meeting.getDate()) == 0) {
                meetingsOnADate.add(meeting);
            }
        }

        return meetingsOnADate;
    }

    /**
     * Returns the list of past meetings in which this contact has participated.
     *
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any
     * duplicates.
     *
     * @param contact one of the user’s contacts
     * @return the list of future meeting(s) scheduled with this contact (maybe empty).
     * @throws IllegalArgumentException if the contact does not exist
     */
    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {

        // TODO: Check that contact exists
        List<PastMeeting> pastMeetings = new ArrayList<PastMeeting>();

        for(Map.Entry<Integer, Meeting> meetingEntry: this.meetings.entrySet()){
            Meeting meeting = meetingEntry.getValue();

            Set<Contact> meetingContacts = meeting.getContacts();

            if (meetingContacts.contains(contact)) {
                if (!dateIsInTheFuture(meeting.getDate())) {
                    pastMeetings.add((PastMeeting)meeting);
                }
            }
         }

        return pastMeetings;
    }

    /**
     * Create a new record for a meeting that took place in
     *
     * @param contacts a list of participants
     * @param date     the date on which the meeting took place
     * @param text     notes to be added about the meeting.
     * @throws IllegalArgumentException if the list of contacts is
     *                                  empty, or any of the contacts does not exist
     * @throws NullPointerException     if any of the arguments is null
     */
    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        if ((contacts == null) || (date == null || (text == null))) {
            throw new NullPointerException("Null argument provided.");
        }

        if (!dateIsInTheFuture(date)) {
            PastMeeting pastMeeting = new PastMeetingImpl();
            pastMeeting.setDate(date);
            pastMeeting.setContacts(contacts);
            pastMeeting.addNotes(text);
            this.meetings.put(pastMeeting.getId(), pastMeeting);

        } else {
            throw new IllegalArgumentException("Future date was provided.");
        }
    }

    /**
     * Add notes to a meeting.
     *
     * This method is used when a future meeting takes place, and is
     * then converted to a past meeting (with notes).
     *
     * It can be also used to add notes to a past meeting at a later date.
     *
     * @param id   the ID of the meeting
     * @param text messages to be added about the meeting.
     * @throws IllegalArgumentException if the meeting does not exist
     * @throws IllegalStateException    if the meeting is set for a date in the future
     * @throws NullPointerException     if the notes are null
     */
    @Override
    public void addMeetingNotes(int id, String text) {

    }

    /**
     * Create a new contact with the specified name and notes.
     *
     * @param name  the name of the contact.
     * @param notes notes to be added about the contact.
     * @throws NullPointerException if the name or the notes are null
     */
    @Override
    public void addNewContact(String name, String notes) {

    }

    /**
     * Returns a list containing the contacts that correspond to the IDs.
     *
     * @param ids an arbitrary number of contact IDs
     * @return a list containing the contacts that correspond to the IDs.
     * @throws IllegalArgumentException if any of the IDs does not correspond to a real contact
     */
    @Override
    public Set<Contact> getContacts(int... ids) {
        return null;
    }

    /**
     * Returns a list with the contacts whose name contains that string.
     *
     * @param name the string to search for
     * @return a list with the contacts whose name contains that string.
     * @throws NullPointerException if the parameter is null
     */
    @Override
    public Set<Contact> getContacts(String name) {
        return null;
    }

    /**
     * Save all data to disk.
     *
     * This method must be executed when the program is
     * closed and when/if the user requests it.
     */
    @Override
    public void flush() {

    }
}
