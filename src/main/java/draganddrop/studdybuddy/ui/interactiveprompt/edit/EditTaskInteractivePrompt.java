package draganddrop.studdybuddy.ui.interactiveprompt.edit;

import java.time.LocalDateTime;

import draganddrop.studdybuddy.logic.commands.exceptions.CommandException;
import draganddrop.studdybuddy.logic.parser.interactivecommandparser.EditTaskCommandParser;
import draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.EditTaskCommandException;
import draganddrop.studdybuddy.model.task.Task;
import draganddrop.studdybuddy.model.task.TaskType;
import draganddrop.studdybuddy.ui.interactiveprompt.InteractivePrompt;
import draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms;
import draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptType;


/**
 * Interactive prompt for editing tasks
 */
public class EditTaskInteractivePrompt extends InteractivePrompt {
    private static final String END_OF_COMMAND_MSG = "Task edited successfully";
    private static final String QUIT_COMMAND_MSG = "Successfully quit from the edit task command";
    private int taskNumber;
    private draganddrop.studdybuddy.model.task.TaskField taskField;

    public EditTaskInteractivePrompt() {
        super();
        this.interactivePromptType = InteractivePromptType.EDIT_TASK;
    }

    @Override
    public String interact(String userInput) {
        if (isQuitOrBack(userInput)) {
            this.reply = handleQuitAndBack(userInput, QUIT_COMMAND_MSG);
        } else {
            this.reply = handleEdit(userInput);
        }
        return this.reply;
    }

    /**
     * handles the sequence of commands for edit
     * @param userInput input from user
     * @return reply to user
     */
    public String handleEdit(String userInput) {
        switch (currentTerm) {
        case INIT:
            this.reply = "Please enter the index of the task that you wish to edit.";
            this.currentTerm = InteractivePromptTerms.TASK_NUMBER;
            break;
        case TASK_NUMBER:
            this.taskNumber = parseTaskNumber(userInput);
            break;
        case TASK_FIELD:
            this.taskField = parseTaskFieldNumber(userInput);
            break;
        case NEW_VALUE:
            this.reply = handleNewValue(userInput);
            break;
        default:
            break;
        }
        return this.reply;
    }

    /**
     * Creates and executes an edit command, with the new values provided by the user
     * @param userInput input from user
     * @return reply to user
     */
    public String handleNewValue(String userInput) {
        draganddrop.studdybuddy.commons.core.index.Index taskIndex = draganddrop.studdybuddy.commons.core.index.Index.fromZeroBased(taskNumber - 1);
        draganddrop.studdybuddy.logic.commands.EditTaskCommand editTaskCommand = new draganddrop.studdybuddy.logic.commands.EditTaskCommand(taskIndex, taskField);
        boolean parseSuccess = true;
        String successMessage = END_OF_COMMAND_MSG;
        try {
            switch (taskField) {
            case TASK_NAME:
                String newName = EditTaskCommandParser.parseName(userInput);
                editTaskCommand.provideNewTaskName(newName);
                break;
            case TASK_TYPE:
                TaskType newTaskType = EditTaskCommandParser.parseType(userInput, TaskType.getTaskTypes().length);
                editTaskCommand.provideNewTaskType(newTaskType);
                successMessage = "The type of task is successfully changed to: " + newTaskType + ".\n";
                break;
            case TASK_DATETIME:
                LocalDateTime[] newDateTimes = EditTaskCommandParser.parseDateTime(userInput);
                editTaskCommand.provideNewDateTime(newDateTimes);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + taskField);
            }
        } catch (EditTaskCommandException ex) {
            parseSuccess = false;
            reply = ex.getErrorMessage();
        }

        if (parseSuccess) {
            try {
                logic.executeCommand(editTaskCommand);
                endInteract(successMessage);
            } catch (java.text.ParseException | CommandException ex) {
                reply = ex.getMessage();
            }
        }
        return reply;
    }

    /**
     * parses task number
     * @param userInput user input for task number
     * @return an int of task number
     */
    public int parseTaskNumber(String userInput) {
        boolean isParseSuccessful = true;
        int taskNum = -1;

        try {
            taskNum = Integer.parseInt(userInput);
            if (taskNum > Task.getCurrentTasks().size() || taskNum < 1) {
                throw new draganddrop.studdybuddy.logic.parser.exceptions.ParseException("task number not in range");
            }
        } catch (NumberFormatException | draganddrop.studdybuddy.logic.parser.exceptions.ParseException ex) {
            isParseSuccessful = false;
        }

        if (isParseSuccessful) {
            this.reply = "Please choose the field that you wish to edit in task number " + taskNum + ".\n"
                        + draganddrop.studdybuddy.model.task.TaskField.getFieldString();
            this.currentTerm = InteractivePromptTerms.TASK_FIELD;
        } else {
            // prompt for a new value
            this.reply = "Please choose a valid task number.";
            this.currentTerm = InteractivePromptTerms.TASK_NUMBER;
        }
        return taskNum;
    }

    /**
     * parses the task field
     * @param userInput userInput for task number
     * @return a TaskField
     */
    public draganddrop.studdybuddy.model.task.TaskField parseTaskFieldNumber(String userInput) {
        boolean isParseSuccessful = true;
        draganddrop.studdybuddy.model.task.TaskField taskField = null;

        try {
            int taskFieldNumber = Integer.parseInt(userInput);
            if (taskFieldNumber > 3 || taskFieldNumber < 1) {
                throw new draganddrop.studdybuddy.logic.parser.exceptions.ParseException("task field number not in range");
            }
            taskField = draganddrop.studdybuddy.model.task.TaskField.getTaskFieldFromNumber(taskFieldNumber);
        } catch (NumberFormatException | draganddrop.studdybuddy.logic.parser.exceptions.ParseException ex) {
            isParseSuccessful = false;
        }

        if (isParseSuccessful) {
            assert(taskField != null);
            this.reply = getTaskFieldMessage(taskField);
            this.currentTerm = InteractivePromptTerms.NEW_VALUE;
        } else {
            // prompt for a new value
            this.reply = "Please choose a valid task field index";
            this.currentTerm = InteractivePromptTerms.TASK_FIELD;
        }
        return taskField;
    }

    private String getTaskFieldMessage(draganddrop.studdybuddy.model.task.TaskField taskField) {
        String result = "You are now editing the " + taskField.getLabel() + "field\n";
        switch (taskField) {
        case TASK_NAME:
            result += "Please enter the new task name.";
            break;
        case TASK_TYPE:
            result += "Please choose the task type:\n"
                + TaskType.getTypeString();
            break;
        case TASK_DATETIME:
            result += "Please enter the deadline with format: "
                + "HH:mm dd/MM/yyyy-HH:mm dd/MM/yyyy";;
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + taskField);
        }
        return result;
    }


}

