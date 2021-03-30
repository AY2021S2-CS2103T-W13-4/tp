package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.contact.Contact;
import seedu.address.model.entry.Entry;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Entry> PREDICATE_SHOW_ALL_CONTACTS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Entry> PREDICATE_SHOW_ALL_ENTRIES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Schedule> PREDICATE_SHOW_ALL_SCHEDULES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    // ====== Person ======

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    // ====== Contact ======

    /**
     * Returns true if the contact exists in the list.
     */
    boolean hasContact(Contact contact);

    /**
     * Deletes the given contact.
     * The contact must exist in the list.
     */
    void deleteContact(Contact contact);

    /**
     * Adds the given contact.
     * {@code contact} must not exist in the list.
     */
    void addContact(Contact contact);

    // ====== Entry ======

    /**
     * Returns true if the entry exists in the list.
     */
    boolean hasEntry(Entry entry);

    /**
     * Deletes the given entry.
     * The entry must exist in the list.
     */
    void deleteEntry(Entry entry);

    /**
     * Adds the given entry.
     * {@code entry} must not overlap with existing entries in the list.
     */
    void addEntry(Entry entry);

    /**
     * Replaces the given entry {@code target} with {@code editedEntry}.
     * {@code target} must exist in the list.
     * {@code editedEntry} must not overlap with existing entries in the list.
     */
    void setEntry(Entry target, Entry editedEntry);

    /** Returns an unmodifiable view of the filtered entry list. */
    ObservableList<Entry> getFilteredEntryList();

    /**
     * Updates the filter of the filtered entry list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEntryList(Predicate<Entry> predicate);

    // ====== The methods declared below are deprecated ======

    /**
     * Returns true if a task with the same identity as {@code task} exists in the task list.
     */
    boolean hasTask(Task task);

    /**
     * Adds the given task.
     * {@code task} must not already exist in the task list.
     */
    void addTask(Task task);

    /**
     * Deletes the given task.
     * The task must exist in the address book.
     */
    void deleteTask(Task target);

    /**
     * Adds the given schedule.
     * {@code schedule} must not already exist in the schedule list.
     */
    void addSchedule(Schedule schedule);

    /**
     * Returns true if a schedule with the same identity as {@code schedule} exists in the schedule list.
     */
    boolean hasSchedule(Schedule schedule);

    /**
     * Deletes the given schedule.
     * The schedule must exist in the schedule list.
     */
    void deleteSchedule(Schedule schedule);

    /** Returns an unmodifiable view of the filtered schedule list */
    ObservableList<Schedule> getFilteredScheduleList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredScheduleList(Predicate<Schedule> predicate);

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);
}
