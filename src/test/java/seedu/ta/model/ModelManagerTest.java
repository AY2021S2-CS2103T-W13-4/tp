package seedu.ta.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ta.model.Model.PREDICATE_SHOW_ALL_CONTACTS;
import static seedu.ta.testutil.Assert.assertThrows;
import static seedu.ta.testutil.TypicalTeachingAssistant.AMY;
import static seedu.ta.testutil.TypicalTeachingAssistant.BEN;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.ta.commons.core.GuiSettings;
import seedu.ta.model.contact.ContactNameContainsKeywordsPredicate;
import seedu.ta.testutil.TeachingAssistantBuilder;

/**
 * Contains unit tests for {@code ModelManager}.
 */
public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new TeachingAssistant(), new TeachingAssistant(modelManager.getTeachingAssistant()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setTeachingAssistantFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setTeachingAssistantFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setTeachingAssistantFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setTeachingAssistantFilePath(null));
    }

    @Test
    public void setTeachingAssistantFilePath_validPath_setsTeachingAssistantFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setTeachingAssistantFilePath(path);
        assertEquals(path, modelManager.getTeachingAssistantFilePath());
    }

    @Test
    public void hasContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasContact(null));
    }

    @Test
    public void hasContact_contactNotInTeachingAssistant_returnsFalse() {
        assertFalse(modelManager.hasContact(AMY));
    }

    @Test
    public void hasContact_contactInTeachingAssistant_returnsTrue() {
        modelManager.addContact(AMY);
        assertTrue(modelManager.hasContact(AMY));
    }

    @Test
    public void getFilteredContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredContactList().remove(0));
    }

    @Test
    public void equals() {
        TeachingAssistant teachingAssistant = new TeachingAssistantBuilder().withContact(AMY).withContact(BEN)
                .build();
        TeachingAssistant differentTeachingAssistant = new TeachingAssistant();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(teachingAssistant, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(teachingAssistant, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different teachingAssistant -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentTeachingAssistant, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = AMY.getName().fullName.split("\\s+");
        modelManager.updateFilteredContactList(new ContactNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(teachingAssistant, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setTeachingAssistantFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(teachingAssistant, differentUserPrefs)));
    }
}
