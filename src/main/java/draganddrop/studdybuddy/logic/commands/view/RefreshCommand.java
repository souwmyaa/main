package draganddrop.studdybuddy.logic.commands.view;

import static java.util.Objects.requireNonNull;

import java.util.List;

/**
 * Archives an entry in the address book.
 */
public class RefreshCommand extends draganddrop.studdybuddy.logic.commands.Command {

    public static final String MESSAGE_USAGE = "Refreshes the tasks and update their status";
    public static final String MESSAGE_DUE_SOON_TASK_SUCCESS = "Tasks' status are updated and "
        + "tasks due soon are displayed";

    public RefreshCommand() {

    }

    @Override
    public draganddrop.studdybuddy.logic.commands.CommandResult execute(draganddrop.studdybuddy.model.Model model) {
        requireNonNull(model);
        List<draganddrop.studdybuddy.model.task.Task> lastShownList = model.getFilteredTaskList();

        for (int i = 0; i < lastShownList.size(); i++) {
            draganddrop.studdybuddy.model.task.Task task = lastShownList.get(i);
            boolean isStatusExpired = task.isStatusExpired();
            if (isStatusExpired) {
                draganddrop.studdybuddy.model.task.Task temp = task;
                model.deleteTask(task);
                temp.freshStatus();
                model.addTask(temp);
                model.sortTasks("Creation DateTime");
            }
            if (task.isDueSoon()) {
                if (model.getFilteredDueSoonTaskList().contains(task)) {
                    continue;
                } else {
                    model.addDueSoonTask(task);
                }
            } else {
                if (model.getFilteredDueSoonTaskList().contains(task)) {
                    model.deleteDueSoonTask(task);
                } else {
                    continue;
                }
            }
        }
        model.sortDueSoonTasks();
        return new draganddrop.studdybuddy.logic.commands.CommandResult(String.format(MESSAGE_DUE_SOON_TASK_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RefreshCommand); // state check
    }

}
