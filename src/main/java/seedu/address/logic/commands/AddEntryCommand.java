package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entry.Entry;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class AddEntryCommand extends Command {

    public static final String COMMAND_WORD = "eadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an entry to the list."
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_START_DATE + "START DATE]"
            + PREFIX_END_DATE + "END DATE"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " ";

    public static final String MESSAGE_SUCCESS = "New entry added: %1$s";
    public static final String MESSAGE_DUPLICATED_ENTRY = "This entry already exists in the list.";

    private final Entry toAdd;

    public AddEntryCommand(Entry entry) {
        requireNonNull(entry);
        toAdd = entry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEntry(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATED_ENTRY);
        }

        model.addEntry(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddEntryCommand
                && toAdd.equals(((AddEntryCommand) other).toAdd));
    }
}