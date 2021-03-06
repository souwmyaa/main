package draganddrop.studybuddy.logic.interactiveprompt.add;

import java.text.ParseException;

import draganddrop.studybuddy.logic.commands.add.CreateModCommand;
import draganddrop.studybuddy.logic.commands.exceptions.CommandException;
import draganddrop.studybuddy.logic.interactiveprompt.InteractivePrompt;
import draganddrop.studybuddy.logic.interactiveprompt.InteractivePromptTerms;
import draganddrop.studybuddy.logic.interactiveprompt.InteractivePromptType;
import draganddrop.studybuddy.model.module.Module;
import draganddrop.studybuddy.model.module.exceptions.ModuleException;


/**
 * An interactive prompt for creating modules.
 */
public class CreateModuleInteractivePrompt extends InteractivePrompt {
    public static final String MODULE_CODE_FORMAT = "Please key in your module code to include a 2-3 letter prefix,"
        + " a 4-digit number,"
        + "then a postfix (Optional).\n"
        + "E.g. BA1001\n       CS2030J     \n       LSM2040C";
    public static final String QUIT_COMMAND_MSG = "Successfully quited from create mod command.";
    public static final String SUCCESS_COMMAND_MSG = "Module created!\n\nKey in your next command :)";
    public static final String REQUIRED_MODULE_NAME_MSG = "Please key in the name of "
        + "the module that you want to create";
    public static final String REQUIRED_MODULE_CODE_MSG = "Please key in the module code of the module "
        + "that you want to create";

    private Module module;

    public CreateModuleInteractivePrompt() {
        super();
        this.interactivePromptType = InteractivePromptType.CREATE_MODULE;
        this.module = new Module();
    }

    @Override
    public String interact(String userInput) {
        if ("quit".equalsIgnoreCase(userInput)) {
            endInteract(QUIT_COMMAND_MSG);
            return reply;
        }

        switch (currentTerm) {

        case INIT:
            this.reply = REQUIRED_MODULE_NAME_MSG;
            currentTerm = InteractivePromptTerms.MODULE_NAME;
            break;
        case MODULE_NAME:
            try {
                if (userInput.isBlank()) {
                    throw new ModuleException("emptyInputError");
                } else {
                    if (!logic.getFilteredModuleList()
                        .filtered(x -> x.getModuleName().equalsIgnoreCase(userInput)).isEmpty()) {
                        throw new ModuleException("duplicateModuleNameError");
                    } else {
                        this.reply = "The name of new module is set to: " + userInput + ".\n\n"
                            + REQUIRED_MODULE_CODE_MSG;
                        module.setModuleName(userInput);
                        currentTerm = InteractivePromptTerms.MODULE_CODE;
                    }
                }
            } catch (ModuleException ex) {
                reply = ex.getErrorMessage() + "\n\n" + REQUIRED_MODULE_NAME_MSG;
            }
            break;
        case MODULE_CODE:
            try {
                if (userInput.isBlank()) {
                    throw new ModuleException("emptyInputError");
                } else {
                    if (!logic.getFilteredModuleList().filtered(x -> x.toString()
                        .equalsIgnoreCase(userInput)).isEmpty()) {
                        throw new ModuleException("duplicateModuleCodeError");
                    } else {
                        module.setModuleCode(userInput);
                        this.reply = "The module code of new module is set to: " + module.toString() + "\n\n"
                            + "Please press enter to confirm your changes";
                        currentTerm = InteractivePromptTerms.READY_TO_EXECUTE;
                    }
                }
            } catch (ModuleException ex) {
                reply = ex.getErrorMessage() + "\n\n" + REQUIRED_MODULE_CODE_MSG;
            }
            break;
        case READY_TO_EXECUTE:
            try {
                CreateModCommand createModCommand = new CreateModCommand(module);
                logic.executeCommand(createModCommand);
                reply = SUCCESS_COMMAND_MSG;
                endInteract(reply);
            } catch (ParseException ex) {
                reply = ex.getMessage();
            } catch (CommandException ex) {
                reply = ex.getLocalizedMessage();
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
}
