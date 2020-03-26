package draganddrop.studdybuddy.logic.commands.view;

import static java.util.Objects.requireNonNull;

/**
 * Represent a ListTaskCommand that can List all persons in the task list to the user.
 */
public class ListTaskCommand extends draganddrop.studdybuddy.logic.commands.Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public draganddrop.studdybuddy.logic.commands.CommandResult execute(draganddrop.studdybuddy.model.Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(draganddrop.studdybuddy.model.Model.PREDICATE_SHOW_ALL_TASKS);
        return new draganddrop.studdybuddy.logic.commands.CommandResult(MESSAGE_SUCCESS);
    }

}
