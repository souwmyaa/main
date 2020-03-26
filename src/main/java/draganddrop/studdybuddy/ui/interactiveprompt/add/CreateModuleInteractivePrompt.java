package draganddrop.studdybuddy.ui.interactiveprompt.add;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * An interactive prompt for creating modules.
 */
public class CreateModuleInteractivePrompt extends draganddrop.studdybuddy.ui.interactiveprompt.InteractivePrompt {
    private draganddrop.studdybuddy.model.module.Module module;

    public CreateModuleInteractivePrompt() {
        super();
        this.interactivePromptType = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptType.CREATE_MODULE;
        this.reply = "";
        this.userInput = "";
        this.currentTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.INIT;
        this.lastTerm = null;
        this.terms = new ArrayList<>();
        this.module = new draganddrop.studdybuddy.model.module.Module();
    }

    @Override
    public String interact(String userInput) {
        if (userInput.equals("quit")) {
            // exit the command
            super.setQuit(true);
        } else if (userInput.equals("back")) {
            if (lastTerm != null) {
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
            this.reply = "Please key in the name of the module that you want to create";
            currentTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.MODULE_NAME;
            lastTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.INIT;
            terms.add(lastTerm);
            break;
        case MODULE_NAME:
            userInput = draganddrop.studdybuddy.logic.parser.interactivecommandparser.AddTaskCommandParser.parseName(userInput);
            this.reply = "The name of module is set to: " + userInput + ".\n"
                + "Now key in your module code";
            module.setModuleName(userInput);
            currentTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.MODULE_CODE;
            lastTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.MODULE_NAME;
            terms.add(lastTerm);
            break;
        case MODULE_CODE:
            module.setModuleCode(userInput);
            this.reply = "Module Code: " + module.toString() + "\n"
                + "Click 'Enter' again to confirm your changes";
            currentTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.READY_TO_EXECUTE;
            lastTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.MODULE_CODE;
            terms.add(lastTerm);
            break;
        case READY_TO_EXECUTE:
            try {
                draganddrop.studdybuddy.logic.commands.add.CreateModCommand createModCommand = new draganddrop.studdybuddy.logic.commands.add.CreateModCommand(module);
                logic.executeCommand(createModCommand);
                reply = "Module created! Key in your next command :)";
                endInteract(reply);
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
