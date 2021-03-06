package seedu.ta.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.ta.commons.core.Messages;
import seedu.ta.model.Model;
import seedu.ta.model.contact.ContactNameContainsKeywordsPredicate;

/**
 * Finds and lists all contacts in Teaching Assistant whose name contains all of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindContactCommand extends Command {

    public static final String COMMAND_WORD = "cfind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all contacts whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Danny";

    private final ContactNameContainsKeywordsPredicate predicate;

    /**
     * Creates a FindContactCommand to find the relevant contacts according to the specified {@code predicate}.
     */
    public FindContactCommand(ContactNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW, model.getFilteredContactList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindContactCommand // instanceof handles nulls
                && predicate.equals(((FindContactCommand) other).predicate)); // state check
    }
}
