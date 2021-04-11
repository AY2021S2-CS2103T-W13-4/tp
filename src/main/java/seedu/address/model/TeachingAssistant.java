package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.UniqueContactList;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.NonOverlappingEntryList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class TeachingAssistant implements ReadOnlyTeachingAssistant {

    private final UniqueContactList contacts;
    private final NonOverlappingEntryList entries;
    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        entries = new NonOverlappingEntryList();
        persons = new UniquePersonList();
        contacts = new UniqueContactList();
    }

    public TeachingAssistant() {}

    /**
     * Creates an TeachingAssistant using the Persons in the {@code toBeCopied}
     */
    public TeachingAssistant(ReadOnlyTeachingAssistant toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the contact list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts.setContacts(contacts);
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the entry list with {@code entries}.
     * {@code entries} must not contain overlapping entries.
     */
    public void setEntries(List<Entry> entries) {
        this.entries.setEntries(entries);
    }

    /**
     * Resets the existing data of this {@code TeachingAssistant} with {@code newData}.
     */
    public void resetData(ReadOnlyTeachingAssistant newData) {
        requireNonNull(newData);
        setContacts(newData.getContactList());
        setPersons(newData.getPersonList());
        setEntries(newData.getEntryList());
    }

    //// contact-level operations

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in Teaching Assistant.
     */
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return contacts.contains(contact);
    }

    /**
     * Adds a contact to Teaching Assistant.
     * The contact must not already exist in Teaching Assistant.
     */
    public void addContact(Contact c) {
        contacts.add(c);
    }

    /**
     * Replaces the given contact {@code target} in the list with {@code editedContact}.
     * {@code target} must exist in Teaching Assistant.
     * The contact identity of {@code editedContact} must not be the same as another existing
     * contact in Teaching Assistant.
     */
    public void setContact(Contact target, Contact editedContact) {
        requireNonNull(editedContact);

        contacts.setContact(target, editedContact);
    }

    /**
     * Removes {@code key} from this {@code Teaching Assistant}.
     * {@code key} must exist in Teaching Assistant.
     */
    public void removeContact(Contact key) {
        contacts.remove(key);
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code TeachingAssistant}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// entry methods

    /**
     * Returns true if the entry exists in the list.
     */
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        return entries.contains(entry);
    }

    /**
     * Adds an entry to the list.
     * The entry must not overlap with existing entries in the list.
     */
    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    /**
     * Removes an entry {@code key} from the list.
     * {@code key} must exist in the list.
     */
    public void removeEntry(Entry key) {
        requireNonNull(key);
        entries.remove(key);
    }

    /**
     * Replaces the given entry {@code target} in the list with {@code editedEntry}.
     * {@code target} must exist in the list.
     * {@code editedEntry} must not overlap with existing entries in the list.
     */
    public void setEntry(Entry target, Entry editedEntry) {
        requireNonNull(editedEntry);
        entries.setEntry(target, editedEntry);
    }

    /**
     * returns true if the give entry overlaps with existing entries in the list.
     */
    public boolean isOverlappingEntry(Entry entry) {
        requireNonNull(entry);
        return entries.overlapsWith(entry);
    }

    public void clearOverdueEntries() {
        entries.clearOverdueEntries();
    }
    //// schedule methods

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Contact> getContactList() {
        return contacts.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Entry> getEntryList() {
        return entries.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TeachingAssistant // instanceof handles nulls
                && persons.equals(((TeachingAssistant) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

}