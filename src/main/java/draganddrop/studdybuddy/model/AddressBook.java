package draganddrop.studdybuddy.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.List;

import draganddrop.studdybuddy.model.task.Task;
import draganddrop.studdybuddy.model.task.TaskType;
import draganddrop.studdybuddy.model.task.UniqueTaskList;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final draganddrop.studdybuddy.model.person.UniquePersonList persons;
    private final UniqueTaskList archivedTasks;
    private final UniqueTaskList dueSoonTasks;
    private final UniqueTaskList tasks;
    private final draganddrop.studdybuddy.model.module.ModuleList moduleList;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new draganddrop.studdybuddy.model.person.UniquePersonList();
        tasks = new UniqueTaskList();
        archivedTasks = new UniqueTaskList();
        dueSoonTasks = new UniqueTaskList();
        moduleList = new draganddrop.studdybuddy.model.module.ModuleList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<draganddrop.studdybuddy.model.person.Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the task list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the task list.
     */
    public void setTasks(Task target, Task editedTask) {
        requireNonNull(editedTask);
        tasks.setTask(target, editedTask);
    }

    /**
     * Sort tasks by the given {@code keyword}.
     */
    public void sortTasks(String keyword) {
        tasks.sortTasks(keyword);
    }

    /**
     * Sort tasks by the given {@code keyword}.
     */
    public void sortDueSoonTasks() {
        dueSoonTasks.sortTasks("deadline / task start date");
    }

    /**
     * Returns true if a task with the same identity as {@code task} exists in the task list.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }


    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the task list.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
        if (this.getDueSoonList().contains(key)) {
            removeDueSoonTask(key);
        }
        sortDueSoonTasks();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the task list.
     */
    public void removeDueSoonTask(Task key) {
        dueSoonTasks.remove(key);
    }

    public void setArchivedTasks(List<Task> aTasks) {
        this.archivedTasks.setTasks(aTasks);
    }

    public void setDueSoonTasks(List<Task> aTasks) {
        this.dueSoonTasks.setTasks(aTasks);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setArchivedTasks(newData.getArchivedList());
        setDueSoonTasks(newData.getDueSoonList());
        setTasks(newData.getTaskList());
        setModuleList(newData.getModuleList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(draganddrop.studdybuddy.model.person.Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(draganddrop.studdybuddy.model.person.Person p) {
        persons.add(p);
    }

    /**
     * Adds an archived person to the address book.
     *
     * @param p must not already exist in the address book.
     */
    public void addArchivedTask(Task p) {
        archivedTasks.add(p);
    }

    /**
     * Adds a due soon task to the dueSoonTasks list.
     *
     * @param p must not already exist in the address book.
     */
    public void addDueSoonTask(Task p) {
        if (p.isDueSoon()) {
            dueSoonTasks.add(p);
            sortDueSoonTasks();
        }
    }

    /**
     * Adds a module to the ModuleList.
     *
     * @param module
     */
    public void addModule(draganddrop.studdybuddy.model.module.Module module) {
        try {
            moduleList.add(module);
        } catch (draganddrop.studdybuddy.model.module.exceptions.ModuleCodeException ex) {
            System.out.println("AddModule Failed, from addressBook.addModule()");
        }
    }

    /**
     * Adds a task to the task list.
     * The task must not already exist in the task list.
     */
    public void addTask(Task t) {
        tasks.add(t);
        if (t.isDueSoon()) {
            addDueSoonTask(t);
        }
    }

    public void completeTask(Task target) {
        tasks.completeTask(target);
    }

    public void setTaskName(Task target, String newTaskName) {
        tasks.setTaskName(target, newTaskName);
    }

    public void setTaskType(Task target, TaskType newTaskType) {
        tasks.setTaskType(target, newTaskType);
    }

    public void setTaskDateTime(Task target, LocalDateTime[] newDateTimes) {
        tasks.setTaskDateTime(target, newDateTimes);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(draganddrop.studdybuddy.model.person.Person target, draganddrop.studdybuddy.model.person.Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(draganddrop.studdybuddy.model.person.Person key) {
        persons.remove(key);
    }

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    //// util methods

    @Override
    public ObservableList<draganddrop.studdybuddy.model.person.Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Task> getArchivedList() {
        return archivedTasks.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Task> getDueSoonList() {
        return dueSoonTasks.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<draganddrop.studdybuddy.model.module.Module> getModuleList() {
        return moduleList.getInternalList();
    }

    public void setModuleList(List<draganddrop.studdybuddy.model.module.Module> modules) {
        this.moduleList.setModuleList(modules);
    }

    public boolean hasModule(draganddrop.studdybuddy.model.module.Module module) {
        return moduleList.contains(module);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddressBook // instanceof handles nulls
            && persons.equals(((AddressBook) other).persons));
    }

}
