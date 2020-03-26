package draganddrop.studdybuddy.ui.interactiveprompt.add;

/**
 * Logic of implementation:
 * IP will handle all interaction btw user and the window to get the final version of command
 * - FSM
 * Parser will handle to parsing of the command and create a command
 * command will execute the action
 * server display the response if needed
 */

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A interactive prompt for adding new task.
 */
public class AddTaskInteractivePrompt extends draganddrop.studdybuddy.ui.interactiveprompt.InteractivePrompt {
    static final String END_OF_COMMAND_MSG = "Task added successfully!";
    static final String END_OF_COMMAND_DUPLICATE_MSG = "Task will not be added! Key in your next command :)";
    static final String QUIT_COMMAND_MSG = "Successfully quited from add task command.";

    protected draganddrop.studdybuddy.model.task.Task task;

    public AddTaskInteractivePrompt() {
        super();
        this.interactivePromptType = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptType.ADD_TASK;
        this.task = new draganddrop.studdybuddy.model.task.Task();
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
            this.reply = "Please enter the task name.";
            currentTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.TASK_NAME;
            lastTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.INIT;
            terms.add(lastTerm);
            /**
             * TEMPORARY PLACEHOLDER TO ENABLE FILE SAVE.
             * REMOVE task.setAttribute once you've create methods to handle these....
             * By default, Task will go to Module code AA0000. To add to a specific module, use other commands.
             */

            task.setModule(new draganddrop.studdybuddy.model.module.EmptyModule());
            task.setStatus("pending");

            task.setTaskDescription("No Description Available");
            task.setWeight(0.0);
            task.setEstimatedTimeCost(0);
            break;

        case TASK_NAME:
            try {
                userInput = draganddrop.studdybuddy.logic.parser.interactivecommandparser.AddTaskCommandParser.parseName(userInput);
                this.reply = "The name of task is set to: " + userInput + ".\n"
                        + "Please choose the task type:\n"
                        + draganddrop.studdybuddy.model.task.TaskType.getTypeString();

                task.setTaskName(userInput);
                currentTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.TASK_TYPE;
                lastTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.TASK_NAME;
                terms.add(lastTerm);
            } catch (draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException ex) {
                reply = ex.getErrorMessage();
            }
            break;

        case TASK_TYPE:
            try {
                draganddrop.studdybuddy.model.task.TaskType taskType = draganddrop.studdybuddy.logic.parser.interactivecommandparser.AddTaskCommandParser.parseType(userInput, draganddrop.studdybuddy.model.task.TaskType.getTaskTypes().length);
                task.setTaskType(taskType);

                userInput = taskType.toString();

                this.reply = "The type of task is set to: " + userInput + ".\n"
                        + "Please enter the deadline with format: ";
                if (taskType.equals(draganddrop.studdybuddy.model.task.TaskType.Assignment)) {
                    this.reply += "HH:mm dd/MM/yyyy";
                } else {
                    this.reply += "HH:mm dd/MM/yyyy-HH:mm dd/MM/yyyy";
                }

                currentTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.TASK_DATETIME;
                lastTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.TASK_TYPE;
                terms.add(lastTerm);
            } catch (NumberFormatException ex) {
                reply = (new draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException("wrongIndexFormat")).getErrorMessage();
            } catch (draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException ex) {
                reply = ex.getErrorMessage();
            }
            break;

        case TASK_DATETIME:
            try {
                LocalDateTime[] dateTimes = draganddrop.studdybuddy.logic.parser.interactivecommandparser.AddTaskCommandParser.parseDateTime(userInput, task.getTaskType());
                task.setDateTimes(dateTimes);

                if (dateTimes.length == 1) {
                    userInput = draganddrop.studdybuddy.logic.parser.TimeParser.getDateTimeString(dateTimes[0]);
                } else {
                    userInput = draganddrop.studdybuddy.logic.parser.TimeParser.getDateTimeString(dateTimes[0])
                            + "-" + draganddrop.studdybuddy.logic.parser.TimeParser.getDateTimeString(dateTimes[1]);
                }

                reply = "The date and time is set to: " + userInput + "\n"
                        + "Press enter again to add the task:\n"
                        + task.getTaskName() + " " + task.getTaskType().toString() + " " + task.getTimeString();

                currentTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.READY_TO_EXECUTE;
                lastTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.TASK_DATETIME;
                terms.add(lastTerm);
            } catch (draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException ex) {
                reply = ex.getErrorMessage();
            }
            break;

        case READY_TO_EXECUTE:
            try {
                task.setCreationDateTime(LocalDateTime.now());
                draganddrop.studdybuddy.logic.commands.add.AddTaskCommand addTaskCommand = new draganddrop.studdybuddy.logic.commands.add.AddTaskCommand(task);
                logic.executeCommand(addTaskCommand);
                System.out.println(task.isDuplicate());
                if (task.isDuplicate()) {
                    reply = "This is a duplicate task. Are you sure you would like to proceed?\n"
                            + "Please enter yes or no.";
                    currentTerm = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptTerms.ADD_DUPLICATE;
                } else {
                    endInteract(END_OF_COMMAND_MSG);
                }
            } catch (ParseException | draganddrop.studdybuddy.logic.commands.exceptions.CommandException e) {
                e.printStackTrace();
            }

            break;

        case ADD_DUPLICATE:
            if (userInput.equalsIgnoreCase("yes")) {
                draganddrop.studdybuddy.logic.commands.add.AddTaskCommand addDuplicateTaskCommand = new draganddrop.studdybuddy.logic.commands.add.AddTaskCommand(task);
                try {
                    logic.executeCommand(addDuplicateTaskCommand);
                    endInteract(END_OF_COMMAND_MSG);
                } catch (draganddrop.studdybuddy.logic.commands.exceptions.CommandException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (userInput.equalsIgnoreCase("no")) {
                endInteract(END_OF_COMMAND_DUPLICATE_MSG);
            } else {
                reply = (new draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException("wrongDuplicateFormat")).getErrorMessage();
            }
            break;

        case TASK_MODULE:
            //TASK_MODULE under construction
            break;

        case TASK_WEIGHT:
            //TASK_WEIGHT under construction
            break;

        case TASK_DESCRIPTION:
            //TASK_DESCRIPTION under construction
            break;

        case TASK_ESTIMATED_TIME_COST:
            //TASK_ESTIMATED_TIME_COST under construction
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
        return "";
    }
}
