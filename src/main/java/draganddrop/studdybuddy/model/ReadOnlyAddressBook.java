package draganddrop.studdybuddy.model;

import draganddrop.studdybuddy.model.task.Task;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<draganddrop.studdybuddy.model.person.Person> getPersonList();

    ObservableList<Task> getArchivedList();

    ObservableList<Task> getDueSoonList();

    ObservableList<draganddrop.studdybuddy.model.module.Module> getModuleList();

    /**
     * Returns an unmodifiable view of the task list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getTaskList();

}
