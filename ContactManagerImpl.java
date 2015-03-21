import java.io.*;
import java.util.*;


public class ContactManagerImpl implements ContactManager {

    private HashMap<Integer, MeetingImpl> meetings = new HashMap();
    private HashMap<Integer, Contact> contacts = new HashMap();

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
            FutureMeetingImpl futureMeeting = new FutureMeetingImpl();
            futureMeeting.setDate(date);

            for (Contact contact : contacts) {
                if (this.contacts.get(contact.getId()) != null) {
                    futureMeeting.addContact(contact);
                } else {
                    throw new IllegalArgumentException("Some of the contacts are unknown or non-existent.");
                }
            }
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

        if (this.contacts.get(contact.getId()) == null) {
            throw new IllegalArgumentException("Contact is unknown or non-existent.");
        }

        List<Meeting> futureMeetings = new ArrayList<Meeting>();

        for(Map.Entry<Integer, MeetingImpl> meetingEntry: this.meetings.entrySet()){
            Meeting meeting = meetingEntry.getValue();

            Set<Contact> meetingContacts = meeting.getContacts();

            if (meetingContacts.contains(contact)) {
                if (dateIsInTheFuture(meeting.getDate())) {
                    futureMeetings.add(meeting);
                }
            }
        }

        if (futureMeetings.size() > 0) {
            Collections.sort(futureMeetings, new Comparator<Meeting>() {
                public int compare(Meeting meeting1, Meeting meeting2) {
                    return meeting1.getDate().compareTo(meeting2.getDate());
                }
            });
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

        List<Meeting> meetingsOnADate = new ArrayList<Meeting>();

        for(Map.Entry<Integer, MeetingImpl> meetingEntry: this.meetings.entrySet()){
            Meeting meeting = meetingEntry.getValue();

            if (date.compareTo(meeting.getDate()) == 0) {
                meetingsOnADate.add(meeting);
            }
        }

        if (meetingsOnADate.size() > 0) {
            Collections.sort(meetingsOnADate, new Comparator<Meeting>() {
                public int compare(Meeting meeting1, Meeting meeting2) {
                    return meeting1.getDate().compareTo(meeting2.getDate());
                }
            });
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

        if (this.contacts.get(contact.getId()) == null) {
            throw new IllegalArgumentException("Contact is unknown or non-existent.");
        }

        List<PastMeeting> pastMeetings = new ArrayList<PastMeeting>();

        for(Map.Entry<Integer, MeetingImpl> meetingEntry: this.meetings.entrySet()){
            Meeting meeting = meetingEntry.getValue();

            Set<Contact> meetingContacts = meeting.getContacts();

            if (meetingContacts.contains(contact)) {
                if (!dateIsInTheFuture(meeting.getDate())) {
                    pastMeetings.add((PastMeeting)meeting);
                }
            }
         }

        if (pastMeetings.size() > 0) {
            Collections.sort(pastMeetings, new Comparator<PastMeeting>() {
                public int compare(PastMeeting meeting1, PastMeeting meeting2) {
                    return meeting1.getDate().compareTo(meeting2.getDate());
                }
            });
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
            PastMeetingImpl pastMeeting = new PastMeetingImpl();
            pastMeeting.setDate(date);

            for (Contact contact : contacts) {
                if (this.contacts.get(contact.getId()) != null) {
                    pastMeeting.addContact(contact);
                } else {
                    throw new IllegalArgumentException("Some of the contacts are unknown or non-existent.");
                }
            }

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
        MeetingImpl meeting = meetings.get(id);

        if (meeting == null) {
            throw new IllegalArgumentException("Meeting does not exist.");
        } else if (text == null) {
            throw new NullPointerException("No notes provided.");
        } else if (dateIsInTheFuture(meeting.getDate())) {
            throw new IllegalStateException("Cannot add notes to a future meeting.");
        } else {
            meeting.addNotes(text);
        }
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
        if ((contacts == null) || (notes == null)) {
            throw new NullPointerException("Null argument provided.");
        } else {
            Contact newContact = new ContactImpl(name, notes);
            this.contacts.put(newContact.getId(), newContact);
        }
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
        Set<Contact> contacts = new HashSet<Contact>();
        for (int i = 0; i < ids.length; i++) {
            Contact contact = this.contacts.get(ids[i]);
            if (contact == null) {
                throw new IllegalArgumentException("Some IDs do not correspond to a real contact.");
            } else {
                contacts.add(contact);
            }
        }
        return contacts;
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
        Set<Contact> contacts = new HashSet<Contact>();

        for(Map.Entry<Integer, Contact> contactEntry: this.contacts.entrySet()){
            Contact contact = contactEntry.getValue();

            if (contact.getName().contains(name)) {
                contacts.add(contact);
            }
        }
        return contacts;
    }

    /**
     * Save all data to disk.
     *
     * This method must be executed when the program is
     * closed and when/if the user requests it.
     */
    @Override
    public void flush() {
        HashMap<String, HashMap> data = new HashMap();
        data.put("meetings", this.meetings);
        data.put("contacts", this.contacts);

        try {
            FileOutputStream fileStream = new FileOutputStream("contacts.txt");
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(data);
            objectStream.close();
            fileStream.close();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Load all data from the disk disk.
     *
     * This method must be executed when the program is
     * loaded and when/if the user requests it.
     */
    public void loadData() {

        try {
            FileInputStream fileStream = new FileInputStream("contacts.txt");
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            HashMap<String, HashMap> data = (HashMap) objectStream.readObject();
            objectStream.close();
            fileStream.close();

            this.contacts = data.get("contacts");
            this.meetings = data.get("meetings");

        } catch(IOException exception) {
            exception.printStackTrace();
        } catch(ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
