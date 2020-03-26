package draganddrop.studdybuddy.ui.interactiveprompt.edit;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * A interactive prompt for completing task.
 */
public class CompleteTaskInteractivePrompt extends draganddrop.studdybuddy.ui.interactiveprompt.InteractivePrompt {

    static final String END_OF_COMMAND_MSG = "Task marked as completed successfully!";
    static final String QUIT_COMMAND_MSG = "Successfully quited from complete task command.";

    private int index;
    private String reply;
    private String userInput;
    private draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms currentTerm;
    private draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms lastTerm;
    private ArrayList<draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms> terms;

    public CompleteTaskInteractivePrompt() {
        super();
        this.interactivePromptType = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptType.COMPLETE_TASK;
        this.reply = "";
        this.userInput = "";
        this.index = index;
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
            this.reply = "Please enter the index number of task you wish to mark as done.";
            currentTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.TASK_INDEX;
            lastTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.INIT;
            terms.add(lastTerm);
            break;

        case TASK_INDEX:
            try {
                index = Integer.parseInt(userInput);
                reply = "The task at index " + userInput + " will be mark as Done. \n "
                    + " Please click enter again to make the desired deletion.";
                currentTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.READY_TO_EXECUTE;
                lastTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.TASK_DATETIME;
                terms.add(lastTerm);
            } catch (draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.DeleteTaskCommandException ex) {
                reply = ex.getErrorMessage();
            }
            break;

        case READY_TO_EXECUTE:
            try {
                draganddrop.studdybuddy.logic.commands.edit.CompleteTaskCommand completeTaskCommand = new draganddrop.studdybuddy.logic.commands.edit.CompleteTaskCommand(draganddrop.studdybuddy.commons.core.index.Index.fromZeroBased(index - 1));
                logic.executeCommand(completeTaskCommand);
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

}
