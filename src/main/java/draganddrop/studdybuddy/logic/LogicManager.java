package draganddrop.studdybuddy.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Logger;

import draganddrop.studdybuddy.commons.core.GuiSettings;
import draganddrop.studdybuddy.commons.core.LogsCenter;
import draganddrop.studdybuddy.logic.commands.Command;
import draganddrop.studdybuddy.logic.commands.CommandResult;
import draganddrop.studdybuddy.logic.commands.exceptions.CommandException;
import draganddrop.studdybuddy.logic.parser.StudyBuddyParser;
import draganddrop.studdybuddy.model.Model;
import draganddrop.studdybuddy.model.ReadOnlyAddressBook;
import draganddrop.studdybuddy.model.task.Task;
import draganddrop.studdybuddy.storage.Storage;

import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final StudyBuddyParser studyBuddyParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        studyBuddyParser = new StudyBuddyParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, draganddrop.studdybuddy.logic.parser.exceptions.ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult = null;
        Command command = studyBuddyParser.parseCommand(commandText);
        try {
            commandResult = command.execute(model);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public CommandResult executeCommand(Command command) throws CommandException, java.text.ParseException {
        CommandResult commandResult;
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
            // keep track of task list
            Task.updateCurrentTaskList(new ArrayList<Task>(getAddressBook().getTaskList()));
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<draganddrop.studdybuddy.model.person.Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<Task> getFilteredArchivedTaskList() {
        return model.getFilteredArchivedTaskList();
    }

    @Override
    public ObservableList<draganddrop.studdybuddy.model.module.Module> getFilteredModuleList() {
        return model.getFilteredModuleList();
    }

    @Override
    public ObservableList<Task> getFilteredDueSoonTaskList() {
        return model.getFilteredDueSoonTaskList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
