package draganddrop.studdybuddy.model;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import draganddrop.studdybuddy.commons.core.GuiSettings;

import draganddrop.studdybuddy.model.task.Task;
import draganddrop.studdybuddy.model.task.TaskType;

import javafx.collections.ObservableList;


/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<draganddrop.studdybuddy.model.person.Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(draganddrop.studdybuddy.model.person.Person person);

    /**
     * Returns true if a task with the same identity as {@code task} exists in the task list.
     */
    boolean hasTask(Task task);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(draganddrop.studdybuddy.model.person.Person target);

    public void deleteDueSoonTask(Task target);
    /**
     * completes the given task.
     * The task must exist in the address book.
     */
    void completeTask(Task target);

    /**
     * Set the task name
     * @param target a task
     * @param newTaskName the new name of the task
     */
    void setTaskName(Task target, String newTaskName);

    /**
     * Set the task type
     * @param target a task
     * @param newTaskType the new task type
     */
    void setTaskType(Task target, TaskType newTaskType);

    /**
     * Set the task date time
     * @param target a task
     * @param newDateTimes the new date and time
     */
    void setTaskDateTime(Task target, LocalDateTime[] newDateTimes);

    /**
     * Deletes the given task.
     * The task must exist in the list.
     */
    void deleteTask(Task task);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(draganddrop.studdybuddy.model.person.Person person);

    /**
     * Archives the given person.
     *
     * @param task must not already exist in the address book.
     */
    void archiveTask(Task task);

    /**
     * Adds the given task.
     *
     * @param task must not already exist in the address book.
     */
    void addDueSoonTask(Task task);

    /**
     * Adds the given task.
     * {@code task} must not already exist in the task list.
     */
    void addTask(Task task);

    void sortDueSoonTasks();

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(draganddrop.studdybuddy.model.person.Person target, draganddrop.studdybuddy.model.person.Person editedPerson);


    void setTask(Task target, Task editedTask);
    boolean hasMod(draganddrop.studdybuddy.model.module.Module mod);

    void addMod(draganddrop.studdybuddy.model.module.Module mod);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<draganddrop.studdybuddy.model.person.Person> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the filtered task list
     */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Returns an unmodifiable view of the filtered archived task list.
     */
    ObservableList<Task> getFilteredArchivedTaskList();

    /**
     * Returns a List of modules
     * @return
     */
    ObservableList<draganddrop.studdybuddy.model.module.Module> getFilteredModuleList();

    /**
     * Returns an unmodifiable view of the filtered archived task list.
     */
    ObservableList<Task> getFilteredDueSoonTaskList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<draganddrop.studdybuddy.model.person.Person> predicate);

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Sort tasks by the given {@code keyword}.
     */
    void sortTasks(String keyword);
}
