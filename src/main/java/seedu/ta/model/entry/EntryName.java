package seedu.ta.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.ta.commons.util.AppUtil.checkArgument;

/**
 * Represents a Entry's name.
 */
public class EntryName {

    public static final String NAME_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank.";

    /**
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String name;

    /**
     * Creates an EntryName with a vaild name.
     */
    public EntryName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), NAME_CONSTRAINTS);
        this.name = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryName // instanceof handles nulls
                && name.equals(((EntryName) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
