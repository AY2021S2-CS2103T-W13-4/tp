package seedu.ta.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.ta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ta.commons.core.Messages.MESSAGE_NOT_EDITED;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ta.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.ta.commons.core.index.Index;
import seedu.ta.logic.commands.EditContactCommand;
import seedu.ta.logic.commands.EditContactCommand.EditContactDescriptor;
import seedu.ta.logic.parser.exceptions.ParseException;
import seedu.ta.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditContactCommand object.
 */
public class EditContactCommandParser implements Parser<EditContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditContactCommand
     * and returns an EditContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditContactCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String
                    .format(MESSAGE_INVALID_COMMAND_FORMAT, EditContactCommand.MESSAGE_USAGE), pe);
        }

        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editContactDescriptor.setName(ParserUtil.parseContactName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editContactDescriptor.setPhone(ParserUtil.parseContactPhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editContactDescriptor.setEmail(ParserUtil.parseContactEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editContactDescriptor::setTags);

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_NOT_EDITED);
        }

        return new EditContactCommand(index, editContactDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }

        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
