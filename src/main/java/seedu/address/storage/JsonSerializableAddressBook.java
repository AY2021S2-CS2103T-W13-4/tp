package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.entry.Entry;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.task.Task;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_TASK = "Task list contains duplicate task(s)";
    private static final String MESSAGE_DUPLICATE_SCHEDULE = "Schedule list contains duplicate schedule(s)";
    private static final String MESSAGE_DUPLICATE_ENTRY = "Entry List contains duplicate entry(s)";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();
    private final List<JsonAdaptedSchedule> schedules = new ArrayList<>();
    private final List<JsonAdaptedEntry> entries = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("tasks") List<JsonAdaptedTask> tasks,
                                       @JsonProperty("schedules") List<JsonAdaptedSchedule> schedules,
                                       @JsonProperty("entries") List<JsonAdaptedEntry> entries) {
        this.persons.addAll(persons);
        this.tasks.addAll(tasks);
        this.schedules.addAll(schedules);
        this.entries.addAll(entries);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
        schedules.addAll(source.getScheduleList().stream().map(JsonAdaptedSchedule::new).collect(Collectors.toList()));
        entries.addAll(source.getEntryList().stream().map(JsonAdaptedEntry::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            if (addressBook.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            addressBook.addTask(task);
        }

        for (JsonAdaptedSchedule jsonAdaptedSchedule : schedules) {
            Schedule schedule = jsonAdaptedSchedule.toModelType();
            if (addressBook.hasSchedule(schedule)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SCHEDULE);
            }
            addressBook.addSchedule(schedule);
        }

        for (JsonAdaptedEntry jsonAdaptedEntry : entries) {
            Entry entry = jsonAdaptedEntry.toModelType();
            if (addressBook.hasEntry(entry)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ENTRY);
            }
            addressBook.addEntry(entry);
        }
        return addressBook;
    }

}
