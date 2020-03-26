package draganddrop.studdybuddy.model.task;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class TaskNameContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public TaskNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
            .anyMatch(keyword -> draganddrop.studdybuddy.commons.util.StringUtil.containsWordIgnoreCase(task.getTaskName(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TaskNameContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((TaskNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
