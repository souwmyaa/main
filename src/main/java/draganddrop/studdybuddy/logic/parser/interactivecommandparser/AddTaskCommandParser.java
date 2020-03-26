package draganddrop.studdybuddy.logic.parser.interactivecommandparser;

import java.time.LocalDateTime;

import draganddrop.studdybuddy.model.task.TaskType;

/**
 * pending.
 */
public class AddTaskCommandParser {

    /**
     * pending.
     */
    public static String parseName(String userInput) throws draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException {
        String result = "";
        if (userInput.isBlank()) {
            throw new draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException("emptyInputError");
        } else {
            result = userInput.trim();
        }
        return result;
    }

    /**
     * pending.
     */
    public static String parseDescription(String userInput) throws draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException {
        String result = "";
        return result;
    }

    /**
     * pending.
     */
    public static LocalDateTime[] parseDateTime(String userInput, TaskType taskType) throws draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException {
        LocalDateTime[] result = null;
        switch (taskType) {
        case Assignment:
            try {
                result = new LocalDateTime[1];
                result[0] = draganddrop.studdybuddy.logic.parser.TimeParser.parseDateTime(userInput);
            } catch (draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.InteractiveCommandException e) {
                throw new draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException("dataTimeFormatError");
            }
            // filter out the input with correct format but invalid date time
            if (result[0].isBefore(LocalDateTime.now())) {
                throw new draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException("pastDateTime");
            }
            break;

        case Quiz:
        case Exam:
        case Meeting:
        case Presentation:
        case Others:
            result = new LocalDateTime[2];
            String[] tempInputDateTimes;

            if (!userInput.contains("-")) {
                throw new draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException("dataTimeFormatError");
            }

            // filter out the invalid input with wrong "start-end" format
            tempInputDateTimes = userInput.trim().split("-");
            if (tempInputDateTimes.length != 2 || tempInputDateTimes[0].isBlank()) {
                throw new draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException("dataTimeFormatError");
            }

            try {
                result[0] = draganddrop.studdybuddy.logic.parser.TimeParser.parseDateTime(tempInputDateTimes[0]);
                result[1] = draganddrop.studdybuddy.logic.parser.TimeParser.parseDateTime(tempInputDateTimes[1]);
            } catch (draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.InteractiveCommandException e) {
                throw new draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException("dataTimeFormatError");
            }

            if (result[0].isBefore(LocalDateTime.now()) || result[1].isBefore(LocalDateTime.now())) {
                throw new draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException("pastDateTime");
            }
            if (result[1].isBefore(result[0])) {
                throw new draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException("eventEndBeforeStartError");
            }
            break;
        default:
        }
        return result;
    }

    /**
     * pending.
     */
    public static String parseModule(String userInput) throws draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException {
        String result = "";
        return result;
    }

    /**
     * pending.
     */
    public static TaskType parseType(String userInput, int size) throws draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException, NumberFormatException {
        TaskType result;
        int index = Integer.parseInt(userInput.trim());
        if (index <= 0 || index > size) {
            throw new draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException("invalidIndexRange");
        } else {
            result = TaskType.getTaskTypes()[index - 1];
        }
        return result;
    }

    /**
     * pending.
     */
    public static String parseWeight(String userInput) throws draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException {
        String result = "";
        return result;
    }

    /**
     * pending.
     */
    public static String parseTimeCost(String userInput) throws draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.AddTaskCommandException {
        String result = "";
        return result;
    }
}
