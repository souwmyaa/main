package draganddrop.studdybuddy.logic.commands.sort;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Represent a SortTaskCommand that can handle the sorting of tasks according to
 * user choice.
 */
public class SortTaskCommand extends draganddrop.studdybuddy.logic.commands.Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Sort the tasks by keyword chosen by user. \n"
        + "Parameters: Sort Keyword (1 (deadline / task start date), 2 (task name) and 3 creation datetime)\n"
        + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SORT_TASK_SUCCESS = "Sort Task: %1$s";

    private String sortKeyword;

    public SortTaskCommand(String sortKeyword) {
        this.sortKeyword = sortKeyword;
    }

    @Override
    public draganddrop.studdybuddy.logic.commands.CommandResult execute(draganddrop.studdybuddy.model.Model model) throws draganddrop.studdybuddy.logic.commands.exceptions.CommandException {
        requireNonNull(model);
        List<draganddrop.studdybuddy.model.task.Task> lastShownList = model.getFilteredTaskList();
        ObservableList<draganddrop.studdybuddy.model.task.Task> temp = model.getAddressBook().getTaskList();
        model.sortTasks(sortKeyword);
        return new draganddrop.studdybuddy.logic.commands.CommandResult(String.format(MESSAGE_SORT_TASK_SUCCESS, sortKeyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SortTaskCommand // instanceof handles nulls
            && sortKeyword.equals(((SortTaskCommand) other).sortKeyword)); // state check
    }
}
