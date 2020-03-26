package draganddrop.studdybuddy.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import draganddrop.studdybuddy.commons.exceptions.IllegalValueException;
import draganddrop.studdybuddy.model.ReadOnlyAddressBook;
import draganddrop.studdybuddy.model.task.Task;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressBook")
class JsonSerializableAddressBook {

    //private static final String MESSAGE_DUPLICATE_TASK = "Task list contains duplicate task(s).";
    //private static final String MESSAGE_DUPLICATE_ARCHIVED_TASK = "Archived contains duplicate task(s).";
    private static final String MESSAGE_DUPLICATE_MODULES = "Module List contains duplicate module(s).";
    //private static final String MESSAGE_DUPLICATE_DUE_SOON_TASK = "Due soon list contains duplicate task(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedTask> archivedTasks = new ArrayList<>();
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();
    private final List<JsonAdaptedTask> dueSoonTasks = new ArrayList<>();
    private final List<JsonAdaptedModule> modules = new ArrayList<>();


    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
        archivedTasks.addAll(source.getArchivedList().stream()
            .map(JsonAdaptedTask::new).collect(Collectors.toList()));
        dueSoonTasks.addAll(source.getTaskList().stream()
                .map(JsonAdaptedTask::new).collect(Collectors.toList()));
        modules.addAll(source.getModuleList().stream().map(JsonAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public draganddrop.studdybuddy.model.AddressBook toModelType() throws IllegalValueException {
        draganddrop.studdybuddy.model.AddressBook addressBook = new draganddrop.studdybuddy.model.AddressBook();
        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            if (task.isStatusExpired()) {
                task.freshStatus();
            }
            addressBook.addTask(task);
        }
        for (JsonAdaptedTask jsonAdaptedTask : archivedTasks) {
            Task task = jsonAdaptedTask.toModelType();
            addressBook.addArchivedTask(task);
        }
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            draganddrop.studdybuddy.model.module.Module module = jsonAdaptedModule.toModelType();
            if (addressBook.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULES);
            }
            addressBook.addModule(module);
        }

        Task.updateCurrentTaskList(new ArrayList<>(addressBook.getTaskList()));
        return addressBook;
    }

}
