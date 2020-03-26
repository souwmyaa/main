package draganddrop.studdybuddy.ui.interactiveprompt.view;

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
public class RefreshTaskInteractivePrompt extends draganddrop.studdybuddy.ui.interactiveprompt.InteractivePrompt {

    static final String END_OF_COMMAND_MSG = "Refreshed tasks' status and tasks due soon list is updated!";
    static final String QUIT_COMMAND_MSG = "Successfully quited from refresh command.";

    private String reply;
    private String userInput;
    private draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms currentTerm;
    private draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms lastTerm;
    private ArrayList<draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms> terms;

    public RefreshTaskInteractivePrompt() {
        super();
        this.interactivePromptType = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptType.DUE_SOON_TASK;
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
        }

        switch (currentTerm) {
        case INIT:
            try {
                reply = "The tasks list will be refreshed.\n "
                    + " Please press enter again to make the desired changes.";
                currentTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.READY_TO_EXECUTE;
                lastTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.INIT;
                terms.add(lastTerm);
            } catch (draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.DueSoonRefreshCommandException ex) {
                reply = ex.getErrorMessage();
            }
            break;

        case READY_TO_EXECUTE:
            try {
                draganddrop.studdybuddy.logic.commands.view.RefreshCommand dueSoonRefreshCommand = new draganddrop.studdybuddy.logic.commands.view.RefreshCommand();
                logic.executeCommand(dueSoonRefreshCommand);
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
    public void endInteract(String msg) {
        this.reply = msg;
        super.setEndOfCommand(true);
    }

    @Override
    public void interruptInteract() {

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
