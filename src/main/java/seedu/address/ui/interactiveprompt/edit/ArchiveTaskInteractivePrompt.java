package seedu.address.ui.interactiveprompt.edit;

/*
 * Logic of implementation:
 * IP will handle all interaction btw user and the window to get the final version of command
 * - FSM
 * Parser will handle to parsing of the command and create a command
 * command will execute the action
 * server display the response if needed
 * */

import static seedu.address.ui.interactiveprompt.InteractivePromptType.ARCHIVE_TASK;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.edit.ArchiveTaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.interactivecommandparser.exceptions.ArchiveTaskCommandException;
import seedu.address.ui.interactiveprompt.InteractivePrompt;
import seedu.address.ui.interactiveprompt.InteractivePromptTerms;

/**
 * pending.
 */
public class ArchiveTaskInteractivePrompt extends InteractivePrompt {
    private int index;

    public ArchiveTaskInteractivePrompt() {
        super();
        this.interactivePromptType = ARCHIVE_TASK;
        this.reply = "";
        this.userInput = "";
        this.currentTerm = InteractivePromptTerms.INIT;
        this.lastTerm = null;
        this.terms = new ArrayList<>();
    }

    @Override
    public String interact(String userInput) {
        if (userInput.equals("quit")) {
            // exit the command
            super.setQuit(true);
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
            this.reply = "Please enter the index number of task you wish to archive.";
            currentTerm = InteractivePromptTerms.TASK_INDEX;
            lastTerm = InteractivePromptTerms.INIT;
            terms.add(lastTerm);
            break;

        case TASK_INDEX:
            try {
                index = Integer.parseInt(userInput);
                reply = "The task at index " + userInput + " will be archived. \n "
                        + " Please press enter again to make the desired changes.";
                currentTerm = InteractivePromptTerms.READY_TO_EXECUTE;
                lastTerm = InteractivePromptTerms.TASK_INDEX;
                terms.add(lastTerm);
            } catch (ArchiveTaskCommandException ex) {
                reply = ex.getErrorMessage();
            }
            break;

        case READY_TO_EXECUTE:
            try {
                ArchiveTaskCommand archiveTaskCommand = new ArchiveTaskCommand(Index.fromZeroBased(index - 1));
                logic.executeCommand(archiveTaskCommand);
                super.setEndOfCommand(true);
                reply = "Task archived!";
            } catch (CommandException ex) {
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
    public void endInteract() {

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
