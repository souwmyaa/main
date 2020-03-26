package draganddrop.studdybuddy.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import draganddrop.studdybuddy.commons.core.GuiSettings;
import draganddrop.studdybuddy.logic.commands.add.AddTaskCommand;
import draganddrop.studdybuddy.model.AddressBook;
import draganddrop.studdybuddy.model.Model;
import draganddrop.studdybuddy.model.ReadOnlyAddressBook;
import draganddrop.studdybuddy.model.ReadOnlyUserPrefs;
import draganddrop.studdybuddy.model.module.Module;
import draganddrop.studdybuddy.model.person.Person;
import draganddrop.studdybuddy.model.task.Task;
import draganddrop.studdybuddy.model.task.TaskType;

import javafx.collections.ObservableList;
//import seedu.address.logic.commands.exceptions.CommandException;


class AddTaskCommandTest {

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> new AddTaskCommand(null));
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Task validTask = new draganddrop.studdybuddy.testutil.TaskBuilder().build();

        CommandResult commandResult = new AddTaskCommand(validTask).execute(modelStub);

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    /*@Test
    public void execute_duplicateTask_throwsCommandException() {
        Task validTask = new TaskBuilder().build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(validTask);
        ModelStub modelStub = new ModelStubWithTask(validTask);

        assertThrows(CommandException.class, addTaskCommand.MESSAGE_DUPLICATE_TASK, ()
            -> addTaskCommand.execute(modelStub));
    }*/

    @Test
    public void equals() {
        Task task1 = new draganddrop.studdybuddy.testutil.TaskBuilder().withName("ass1").build();
        Task task2 = new draganddrop.studdybuddy.testutil.TaskBuilder().withName("ass2").build();

        AddTaskCommand add1command = new AddTaskCommand(task1);
        AddTaskCommand add2command = new AddTaskCommand(task2);

        //same object -> returns true
        assertTrue(add1command.equals(add1command));

        //same values -> returns true
        AddTaskCommand add1copycommand = new AddTaskCommand(task1);
        assertTrue(add1command.equals(add1copycommand));

        //different types -> reutrns false
        assertFalse(add1command.equals(1));

        //null -> returns false
        assertFalse(add1command.equals(null));

        //diff task -> returns false
        assertFalse(add1command.equals(add2command));

    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void archiveTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDueSoonTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        public void deleteTask(Task target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortDueSoonTasks(){}

        @Override
        public void setTask(Task target, Task editedTask) {

        }

        @Override
        public boolean hasMod(Module mod) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMod(Module mod) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void completeTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTaskName(Task target, String newTaskName) {
            // empty
        }

        @Override
        public void setTaskType(Task target, TaskType newTaskType) {
            // empty
        }

        @Override
        public void setTaskDateTime(Task target, LocalDateTime[] newDateTimes) {
            // empty
        }


        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredArchivedTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredDueSoonTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDueSoonTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortTasks(String keyword) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single task.
     */
    private class ModelStubWithTask extends AddTaskCommandTest.ModelStub {
        private final Task task;

        ModelStubWithTask(Task task) {
            requireNonNull(task);
            this.task = task;
        }

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return this.task.isSameTask(task);
        }
    }

    /**
     * A Model stub that always accept the person/task being added.
     */
    private class ModelStubAcceptingPersonAdded extends AddTaskCommandTest.ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public void addTask(Task task) {
            requireNonNull(task);
            tasksAdded.add(task);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return tasksAdded.stream().anyMatch(task::isSameTask);
        }
    }
}
