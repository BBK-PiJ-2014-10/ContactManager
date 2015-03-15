public class ContactImpl implements Contact {
    public static int count = 0;
    private int id;
    private String name;
    private String notes;

    public ContactImpl(String nameToSet, String notesToSet) {
        this.count ++;
        this.id = this.count;
        this.name = nameToSet;
        this.notes = notesToSet;
    }

    /**
     * Returns the ID of the contact.
     *
     * @return the ID of the contact.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Returns the name of the contact. *
     *
     * @return the name of the contact.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns our notes about the contact, if any.
     *
     * If we have not written anything about the contact, the empty
     * string is returned.
     *
     * @return a string with notes about the contact, maybe empty.
     */
    @Override
    public String getNotes() {
        return this.notes;
    }

    /**
     * Add notes about the contact.
     *
     * @param note the notes to be added
     */
    @Override
    public void addNotes(String note) {
        this.notes = note;
    }
}
