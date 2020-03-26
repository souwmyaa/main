package draganddrop.studdybuddy.logic.commands.view;

import static java.util.Objects.requireNonNull;

/**
 * Finds and lists all tasks in the tasks list whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindTaskCommand extends draganddrop.studdybuddy.logic.commands.Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
        + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final draganddrop.studdybuddy.model.task.TaskNameContainsKeywordsPredicate predicate;

    public FindTaskCommand(draganddrop.studdybuddy.model.task.TaskNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public draganddrop.studdybuddy.logic.commands.CommandResult execute(draganddrop.studdybuddy.model.Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);
        return new draganddrop.studdybuddy.logic.commands.CommandResult("Here is the list of matching tasks");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindTaskCommand // instanceof handles nulls
            && predicate.equals(((FindTaskCommand) other).predicate)); // state check
    }
}
