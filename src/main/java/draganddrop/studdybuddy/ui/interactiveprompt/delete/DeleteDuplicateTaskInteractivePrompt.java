package draganddrop.studdybuddy.ui.interactiveprompt.delete;

/*
 * Logic of implementation:
 * IP will handle all interaction btw user and the window to get the final version of command
 * - FSM
 * Parser will handle to parsing of the command and create a command
 * command will execute the action
 * server display the response if needed
 * */

import java.text.ParseException;
import java.util.ArrayList;

/**
 * pending.
 */
public class DeleteDuplicateTaskInteractivePrompt extends draganddrop.studdybuddy.ui.interactiveprompt.InteractivePrompt {
    static final String END_OF_COMMAND_MSG = "Duplicated task deleted successfully!";
    static final String QUIT_COMMAND_MSG = "Successfully quited from delete duplication command.";

    public DeleteDuplicateTaskInteractivePrompt() {
        super();
        this.interactivePromptType = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptType.DELETE_DUPLICATE_TASK;
        this.reply = "";
        this.userInput = "";
        this.currentTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.INIT;
        this.lastTerm = null;
        this.terms = new ArrayList<>();
    }

    @Override
    public String interact(String userInput) {
        if (userInput.equals("quit")) {
            endInteract(QUIT_COMMAND_MSG);
            return reply;
        } else if (userInput.equals("back")) {
            if (lastTerm != null) { //in the beginning it is null
                terms.remove(terms.size() - 1);
                currentTerm = lastTerm;
                if (lastTerm.equals(draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.INIT)) {
                    lastTerm = null;
                } else {
                    lastTerm = terms.get(terms.size() - 1);
                }
                userInput = "";
            } else {
                this.reply = "Please type quit to exit from this command.";
            }
        }

        switch (currentTerm) {

        case INIT:
            try {
                reply = "The duplicate tasks will be deleted\n "
                    + " Please press enter again to make the desired changes.";
                currentTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.READY_TO_EXECUTE;
                lastTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.INIT;
                terms.add(lastTerm);
            } catch (draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.DeleteDuplicateTaskCommandException ex) {
                reply = ex.getErrorMessage();
            }
            break;

        case READY_TO_EXECUTE:
            try {
                draganddrop.studdybuddy.logic.commands.delete.DeleteDuplicateTaskCommand deleteDuplicateTaskCommand = new draganddrop.studdybuddy.logic.commands.delete.DeleteDuplicateTaskCommand();
                logic.executeCommand(deleteDuplicateTaskCommand);
                super.setEndOfCommand(true);
                endInteract(END_OF_COMMAND_MSG);
            } catch (draganddrop.studdybuddy.logic.commands.exceptions.CommandException | ParseException ex) {
                reply = ex.getMessage();
            }
            break;

        default:
        }
        return reply;
    }

    @Override
    public void interruptInteract() {

    }

    @Override
    public void endInteract(String msg) {
        this.reply = msg;
        super.setEndOfCommand(true);
    }

    @Override
    public void back() {

    }

    @Override
    public void next() {

    }

    /**
     * pending.
     */
    private String dateTime() {
        String result = "";


        return result;
    }
}
