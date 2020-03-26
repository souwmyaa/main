package draganddrop.studdybuddy.ui;

import draganddrop.studdybuddy.logic.commands.CommandResult;
import draganddrop.studdybuddy.logic.commands.exceptions.CommandException;
import draganddrop.studdybuddy.ui.interactiveprompt.InteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.InvalidInputInteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.add.AddTaskInteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.add.CreateModuleInteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.delete.DeleteDuplicateTaskInteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.delete.DeleteTaskInteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.edit.CompleteTaskInteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.sort.SortTaskInteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.view.FindTaskInteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.view.HelpInteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.view.ListTaskInteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.view.RefreshTaskInteractivePrompt;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final String[] interactiveCommandTypes =
        {"add", "edit", "delete", "archive", "done", "delete duplicates",
            "clear", "bye", "sort", "refresh", "help", "create mods", "find", "list"};
    private final CommandExecutor commandExecutor;
    private InteractivePrompt currentInteractivePrompt;
    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        currentInteractivePrompt = null;
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            // allow InteractivePrompt type with different case and space in front or behind
            String userInput = commandTextField.getText().toLowerCase().trim();
            if (currentInteractivePrompt == null) {
                switch (userInput) {
                case "add":
                    currentInteractivePrompt = new AddTaskInteractivePrompt();
                    break;
                case "edit":
                    currentInteractivePrompt = new draganddrop.studdybuddy.ui.interactiveprompt.edit.EditTaskInteractivePrompt();
                    break;
                case "delete":
                    currentInteractivePrompt = new DeleteTaskInteractivePrompt();
                    break;
                case "archive":
                    currentInteractivePrompt = new draganddrop.studdybuddy.ui.interactiveprompt.edit.ArchiveTaskInteractivePrompt();
                    break;
                case "delete duplicates":
                    currentInteractivePrompt = new DeleteDuplicateTaskInteractivePrompt();
                    break;
                case "done":
                    currentInteractivePrompt = new CompleteTaskInteractivePrompt();
                    break;
                case "sort":
                    currentInteractivePrompt = new SortTaskInteractivePrompt();
                    break;
                case "help":
                    currentInteractivePrompt = new HelpInteractivePrompt();
                    break;
                case "create mods":
                    currentInteractivePrompt = new CreateModuleInteractivePrompt();
                    break;
                case "bye":
                    currentInteractivePrompt = new draganddrop.studdybuddy.ui.interactiveprompt.ExitTaskInteractivePrompt();
                    break;
                case "find":
                    currentInteractivePrompt = new FindTaskInteractivePrompt();
                    break;
                case "refresh":
                    currentInteractivePrompt = new RefreshTaskInteractivePrompt();
                    break;
                case "list":
                    currentInteractivePrompt = new ListTaskInteractivePrompt();
                    break;
                case "clear":
                    currentInteractivePrompt = new draganddrop.studdybuddy.ui.interactiveprompt.ClearTasksInteractivePrompt();
                    break;
                default:
                    currentInteractivePrompt = new InvalidInputInteractivePrompt();
                    break;
                }
            }

            //currentInteractivePrompt could be null. Might need to create an ErrorInteractivePrompt to handle this.
            //inserted NullPointerException e at the catch section
            CommandResult commandResult = commandExecutor.execute(currentInteractivePrompt, commandTextField.getText());
            if (commandResult != null) {
                currentInteractivePrompt = null;
            }
            commandTextField.setText("");
        } catch (CommandException | draganddrop.studdybuddy.logic.parser.exceptions.ParseException | NullPointerException e) {
            commandTextField.setText(e.getMessage());
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see draganddrop.studdybuddy.logic.Logic#execute(String)
         */
        CommandResult execute(InteractivePrompt currentInteractivePrompt, String commandText)
            throws CommandException, draganddrop.studdybuddy.logic.parser.exceptions.ParseException;
    }
}
