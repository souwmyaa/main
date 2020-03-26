package draganddrop.studdybuddy.ui.interactiveprompt;

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

import draganddrop.studdybuddy.logic.commands.exceptions.CommandException;

/**
 * pending.
 */
public class ClearTasksInteractivePrompt extends InteractivePrompt {
    static final String END_OF_COMMAND_MSG = "Tasks cleared successfully!";
    static final String QUIT_COMMAND_MSG = "Successfully cleared all tasks.";

    private int index;

    public ClearTasksInteractivePrompt() {
        super();
        this.interactivePromptType = InteractivePromptType.CLEAR_TASK;
        this.reply = "";
        this.userInput = "";
        this.currentTerm = InteractivePromptTerms.INIT;
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
                if (lastTerm.equals(InteractivePromptTerms.INIT)) {
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
            this.reply = "Please press enter to clear all your tasks.\n"
                    + " Else enter quit to go back.";
            currentTerm = InteractivePromptTerms.READY_TO_EXECUTE;
            lastTerm = InteractivePromptTerms.INIT;
            terms.add(lastTerm);
            break;

        case READY_TO_EXECUTE:
            try {
                draganddrop.studdybuddy.logic.commands.ClearTasksCommand clearTaskCommand = new draganddrop.studdybuddy.logic.commands.ClearTasksCommand();
                logic.executeCommand(clearTaskCommand);
                endInteract(END_OF_COMMAND_MSG);
            } catch (CommandException | ParseException ex) {
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
