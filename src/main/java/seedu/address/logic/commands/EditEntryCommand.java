package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATED_ENTRY;
import static seedu.address.commons.core.Messages.MESSAGE_EDIT_ENTRY_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_NO_SUCH_ENTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ENTRIES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.EditEntryCommandParser;
import seedu.address.model.Model;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.EntryDate;
import seedu.address.model.entry.EntryName;
import seedu.address.model.entry.EntryNameContainsKeywordsPredicate;
import seedu.address.model.entry.TemporaryEntry;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing entry in the address book.
 */
public class EditEntryCommand extends Command {

    public static final String COMMAND_WORD = "eedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the entry identified "
            + "by the entry name used in the displayed entries list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: ENTRY_NAME "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_START_DATE + "START_DATE] "
            + "[" + PREFIX_END_DATE + "END_DATE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " Meeting "
            + PREFIX_NAME + "Meeting with group ";

    private final EntryNameContainsKeywordsPredicate predicate;
    private final TemporaryEntry tempEntry;

    /**
     * @param predicate to check for an entry in the filtered entry list to edit
     * @param tempEntry containing the details to edit the entry with
     */
    public EditEntryCommand(EntryNameContainsKeywordsPredicate predicate, TemporaryEntry tempEntry) {
        requireNonNull(predicate);
        requireNonNull(tempEntry);

        this.predicate = predicate;
        this.tempEntry = tempEntry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Entry> lastShownList = model.getFilteredEntryList();

        if (!lastShownList.stream().anyMatch(predicate)) {
            throw new CommandException(Messages.MESSAGE_NO_SUCH_ENTRY);
        }

        Entry target = lastShownList.stream().filter(predicate).findFirst().get();

        EntryName updatedEntryName = tempEntry.entryName.orElse(target.getEntryName());
        EntryDate updatedEntryStartDate = tempEntry.startDate.orElse(target.getOriginalStartDate());
        EntryDate updatedEntryEndDate = tempEntry.endDate.orElse(target.getOriginalEndDate());
        Set<Tag> updatedTags = tempEntry.tags.orElse(target.getTags());
        Entry updatedEntry = new Entry(updatedEntryName, updatedEntryStartDate, updatedEntryEndDate, updatedTags);

        model.deleteEntry(target);
        if (model.isOverlappingEntry(updatedEntry)) {
            throw new CommandException(Messages.MESSAGE_OVERLAPPING_ENTRY);
        }

        model.addEntry(updatedEntry);
        model.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, updatedEntry));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEntryCommand)) {
            return false;
        }

        // state check
        EditEntryCommand e = (EditEntryCommand) other;
        return predicate.equals(e.predicate)
                && tempEntry.equals(e.tempEntry);
    }
}
