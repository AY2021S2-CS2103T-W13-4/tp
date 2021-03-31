package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.NameContainsKeywordsPredicate;

public class DeleteContactCommand extends Command {

    public static final String COMMAND_WORD = "cdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the contact identified by the given name in the list.\n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " Amy Toh";

    private final NameContainsKeywordsPredicate predicate;

    public DeleteContactCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();

        if (!lastShownList.stream().anyMatch(predicate)) {
            throw new CommandException(Messages.MESSAGE_NO_SUCH_CONTACT);
        }

        Contact contactToDelete = lastShownList.stream().filter(predicate).findFirst().get();
        model.deleteContact(contactToDelete);
        return new CommandResult(String.format(Messages.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete));
    }
}
