package seedu.ta.logic.parser;

import static seedu.ta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.ta.logic.commands.FilterContactCommand;
import seedu.ta.logic.parser.exceptions.ParseException;
import seedu.ta.model.contact.ContactTagsContainKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterContactCommand object.
 */
public class FilterContactCommandParser implements Parser<FilterContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterContactCommand
     * and returns a FilterContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FilterContactCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");
        return new FilterContactCommand(new ContactTagsContainKeywordsPredicate(Arrays.asList(tagKeywords)));
    }
}
