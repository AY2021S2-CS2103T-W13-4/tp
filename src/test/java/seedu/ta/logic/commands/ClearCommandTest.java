package seedu.ta.logic.commands;

import static seedu.ta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ta.testutil.TypicalTeachingAssistant.getEmptyTypicalTeachingAssistant;
import static seedu.ta.testutil.TypicalTeachingAssistant.getTypicalTeachingAssistant;

import org.junit.jupiter.api.Test;

import seedu.ta.model.Model;
import seedu.ta.model.ModelManager;
import seedu.ta.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ClearCommand}.
 */
public class ClearCommandTest {

    @Test
    public void execute_emptyTeachingAssistant_success() {
        Model expectedModel = new ModelManager(getEmptyTypicalTeachingAssistant(), new UserPrefs());
        Model model = new ModelManager(getEmptyTypicalTeachingAssistant(), new UserPrefs());
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTeachingAssistant_success() {
        Model expectedModel = new ModelManager(getEmptyTypicalTeachingAssistant(), new UserPrefs());
        Model model = new ModelManager(getTypicalTeachingAssistant(), new UserPrefs());
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
