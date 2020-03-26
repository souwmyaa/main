package draganddrop.studdybuddy.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import draganddrop.studdybuddy.model.task.Task;

/**
 * pending.
 */
public class TimeParser {

    /**
     * Parses valid dateTime string to LocalDateTime variable.
     *
     * @param userInput
     * @return
     * @throws draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.InteractiveCommandException
     */
    public static LocalDateTime parseDateTime(String userInput) throws draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.InteractiveCommandException {
        LocalDateTime inputTime = null;
        try {
            inputTime = LocalDateTime.parse(userInput.trim(), Task.DATETIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.InteractiveCommandException("dataTimeFormatError");
        }
        return inputTime;
    }


    /**
     * Converts LocalDateTime variable to a String as HH:mm dd/MM/yyyy format.
     *
     * @param dateTime
     * @return
     * @throws draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.InteractiveCommandException
     */
    public static String getDateTimeString(LocalDateTime dateTime) throws draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.InteractiveCommandException {
        String min = dateTime.getMinute() < 10 ? "0" + dateTime.getMinute()
            : "" + dateTime.getMinute();
        String hour = dateTime.getHour() < 10 ? "0" + dateTime.getHour()
            : "" + dateTime.getHour();
        String day = dateTime.getDayOfMonth() < 10 ? "0" + dateTime.getDayOfMonth()
            : "" + dateTime.getDayOfMonth();
        String month = dateTime.getMonthValue() < 10 ? "0" + dateTime.getMonthValue()
            : "" + dateTime.getMonthValue();
        return hour + ":" + min
            + " " + day + "/" + month + "/" + dateTime.getYear();
    }

    /**
     * Converts LocalDateTime variable to a String as dd/MM/yyyy format.
     *
     * @param date
     * @return
     * @throws draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.InteractiveCommandException
     */
    public static String getDateString(LocalDate date) throws draganddrop.studdybuddy.logic.parser.interactivecommandparser.exceptions.InteractiveCommandException {
        String day = date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth()
            : "" + date.getDayOfMonth();
        String month = date.getMonthValue() < 10 ? "0" + date.getMonthValue()
            : "" + date.getMonthValue();
        return day + "/" + month + "/" + date.getYear();
    }
}
