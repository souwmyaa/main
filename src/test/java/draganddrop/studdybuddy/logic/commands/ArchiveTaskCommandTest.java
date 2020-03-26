package draganddrop.studdybuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static draganddrop.studdybuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static draganddrop.studdybuddy.testutil.TypicalTasks.getTypicalTaskList;

import draganddrop.studdybuddy.commons.core.Messages;
import draganddrop.studdybuddy.commons.core.index.Index;
import draganddrop.studdybuddy.logic.commands.edit.ArchiveTaskCommand;
import draganddrop.studdybuddy.model.Model;
import draganddrop.studdybuddy.model.ModelManager;
import draganddrop.studdybuddy.model.UserPrefs;
import draganddrop.studdybuddy.model.task.Task;
import draganddrop.studdybuddy.testutil.TypicalIndexes;
import org.junit.jupiter.api.Test;

class ArchiveTaskCommandTest {

    private Model model = new ModelManager(getTypicalTaskList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToArchive = model.getFilteredTaskList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        ArchiveTaskCommand atCommand = new ArchiveTaskCommand(TypicalIndexes.INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ArchiveTaskCommand.MESSAGE_ARCHIVE_TASK_SUCCESS, taskToArchive);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToArchive);
        expectedModel.archiveTask(taskToArchive);

        CommandTestUtil.assertCommandSuccess(atCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ArchiveTaskCommand archiveCommand = new ArchiveTaskCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {

        Task taskToArchive = model.getFilteredTaskList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        ArchiveTaskCommand atCommand = new ArchiveTaskCommand(TypicalIndexes.INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ArchiveTaskCommand.MESSAGE_ARCHIVE_TASK_SUCCESS, taskToArchive);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToArchive);
        expectedModel.archiveTask(taskToArchive);

        CommandTestUtil.assertCommandSuccess(atCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ArchiveTaskCommand archiveFirstCommand = new ArchiveTaskCommand(TypicalIndexes.INDEX_FIRST_PERSON);
        ArchiveTaskCommand archiveSecondCommand = new ArchiveTaskCommand(TypicalIndexes.INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveTaskCommand archiveFirstCommandCopy = new ArchiveTaskCommand(TypicalIndexes.INDEX_FIRST_PERSON);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

}
