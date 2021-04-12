package seedu.ta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ta.commons.core.Messages.MESSAGE_DUPLICATE_CONTACT;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.ta.logic.commands.exceptions.CommandException;
import seedu.ta.model.Model;
import seedu.ta.model.contact.Contact;

/**
 * Adds a contact to Teaching Assistant.
 */
public class AddContactCommand extends Command {

    public static final String COMMAND_WORD = "cadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a contact to Teaching Assistant. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Danny Tan "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "danny@email.com "
            + PREFIX_TAG + "student "
            + PREFIX_TAG + "english "
            + PREFIX_TAG + "consultation1";

    public static final String MESSAGE_SUCCESS = "New contact added: %1$s";

    private final Contact toAdd;

    /**
     * Creates an AddContactCommand to add the specified {@code Contact}.
     */
    public AddContactCommand(Contact contact) {
        requireNonNull(contact);
        toAdd = contact;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasContact(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        model.addContact(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddContactCommand // instanceof handles nulls
                && toAdd.equals(((AddContactCommand) other).toAdd));
    }
}
