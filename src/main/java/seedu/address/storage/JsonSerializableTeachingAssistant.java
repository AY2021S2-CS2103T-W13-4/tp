package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTeachingAssistant;
import seedu.address.model.TeachingAssistant;
import seedu.address.model.contact.Contact;
import seedu.address.model.entry.Entry;

/**
 * An Immutable TeachingAssistant that is serializable to JSON format.
 */
@JsonRootName(value = "teachingassistant")
class JsonSerializableTeachingAssistant {

    public static final String MESSAGE_DUPLICATE_CONTACT = "Contact list contains duplicate contacts(s).";
    public static final String MESSAGE_DUPLICATE_ENTRY = "Entry List contains duplicate entry(s)";
    public static final String MESSAGE_OVERLAPPING_ENTRY = "Entry List contains entries with overlapping dates";

    private final List<JsonAdaptedContact> contacts = new ArrayList<>();
    private final List<JsonAdaptedEntry> entries = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTeachingAssistant} with the given contacts.
     */
    @JsonCreator
    public JsonSerializableTeachingAssistant(@JsonProperty("contacts") List<JsonAdaptedContact> contacts,
                                             @JsonProperty("entries") List<JsonAdaptedEntry> entries) {
        this.contacts.addAll(contacts);
        this.entries.addAll(entries);
    }

    /**
     * Converts a given {@code ReadOnlyTeachingAssistant} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTeachingAssistant}.
     */
    public JsonSerializableTeachingAssistant(ReadOnlyTeachingAssistant source) {
        contacts.addAll(source.getContactList().stream().map(JsonAdaptedContact::new).collect(Collectors.toList()));
        entries.addAll(source.getEntryList().stream().map(JsonAdaptedEntry::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code TeachingAssistant} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TeachingAssistant toModelType() throws IllegalValueException {
        TeachingAssistant addressBook = new TeachingAssistant();
        for (JsonAdaptedContact jsonAdaptedContact : contacts) {
            Contact contact = jsonAdaptedContact.toModelType();
            if (addressBook.hasContact(contact)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CONTACT);
            }
            addressBook.addContact(contact);
        }

        for (JsonAdaptedEntry jsonAdaptedEntry : entries) {
            Entry entry = jsonAdaptedEntry.toModelType();
            if (addressBook.hasEntry(entry)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ENTRY);
            }

            if (addressBook.isOverlappingEntry(entry)) {
                throw new IllegalValueException(MESSAGE_OVERLAPPING_ENTRY);
            }
            addressBook.addEntry(entry);
        }
        return addressBook;
    }

}