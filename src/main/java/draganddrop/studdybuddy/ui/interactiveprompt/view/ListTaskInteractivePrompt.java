package draganddrop.studdybuddy.ui.interactiveprompt.view;

import java.text.ParseException;

/**
 * InteractivePrompt to list the tasks. This is useful after a Find command.
 */
public class ListTaskInteractivePrompt extends draganddrop.studdybuddy.ui.interactiveprompt.InteractivePrompt {
    private static final String END_OF_COMMAND_MSG = "Here is the complete list of tasks:";

    public ListTaskInteractivePrompt() {
        super();
        this.interactivePromptType = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptType.LIST_TASK;
    }

    @Override
    public String interact(String userInput) {
        displayList();
        endInteract(END_OF_COMMAND_MSG);
        return reply;
    }

    /**
     * displays the complete list of tasks
     */
    public void displayList() {
        try {
            draganddrop.studdybuddy.logic.commands.view.ListTaskCommand listTaskCommand = new draganddrop.studdybuddy.logic.commands.view.ListTaskCommand();
            logic.executeCommand(listTaskCommand);
        } catch (draganddrop.studdybuddy.logic.commands.exceptions.CommandException | ParseException ex) {
            // do nothing, this block should not be reached
        }
    }

}
