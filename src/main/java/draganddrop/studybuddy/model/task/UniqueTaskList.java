package draganddrop.studybuddy.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import draganddrop.studybuddy.commons.util.CollectionUtil;
import draganddrop.studybuddy.logic.parser.TimeParser;
import draganddrop.studybuddy.model.module.Module;
import draganddrop.studybuddy.model.statistics.Statistics;
import draganddrop.studybuddy.model.task.exceptions.DuplicateTaskException;
import draganddrop.studybuddy.model.task.exceptions.TaskNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a UniqueTaskList.
 */
public class UniqueTaskList implements Iterable<Task> {

    private static Statistics statistics;
    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ObservableList<Task> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Sets the statistics for unique task list
     * @param statistics completionStats used to record tasks completed
     */
    public static void setStatistics(Statistics statistics) {
        UniqueTaskList.statistics = statistics;
    }

    public int getSize() {
        return internalList.size();
    }
    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean isContains(Task toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTask);
    }

    /**
     * Adds a task to the list.
     * The task must not already exist in the list.
     */
    public void add(Task toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        statistics.recordAddedTask(toAdd);
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the list.
     */
    public void setTask(Task target, Task editedTask) {
        CollectionUtil.requireAllNonNull(target, editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        /*
        if (target.isSameTask(editedTask) && contains(editedTask)) {
            throw new DuplicateTaskException("duplicateTask");
        }*/

        internalList.set(index, editedTask);
    }

    /**
     * Removes the equivalent task from the list.
     * The task must exist in the list.
     */
    public void remove(Task toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TaskNotFoundException();
        }
    }

    public void setTasks(UniqueTaskList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        CollectionUtil.requireAllNonNull(tasks);

        internalList.setAll(tasks);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueTaskList // instanceof handles nulls
            && internalList.equals(((UniqueTaskList) other).internalList));
    }

    /**
     * Completes a task.
     *
     * @param target a task
     */
    public void completeTask(Task target) {
        requireNonNull(target);
        if (!internalList.contains(target)) {
            throw new TaskNotFoundException();
        } else {
            target.setStatus("Finished");
            String finishDateTimeString = TimeParser.getDateTimeString(LocalDateTime.now());
            target.setFinishDateTime(TimeParser.parseDateTime(finishDateTimeString));
            int index = internalList.indexOf(target);
            internalList.set(index, target);

            // log statistics
            statistics.recordCompletedTask(target);
        }
    }

    /**
     * Set the task name.
     *
     * @param target a task
     * @param newTaskName the new name of the task
     */
    public void setTaskName(Task target, String newTaskName) throws DuplicateTaskException {
        requireNonNull(target);
        requireNonNull(newTaskName);
        target.setTaskName(newTaskName);
        /*if (internalList.contains(target)) {
            throw new DuplicateTaskException("duplicateTask");
        }*/
        int index = internalList.indexOf(target);
        internalList.set(index, target);
    }

    /**
     * Set the task estimated time cost.
     *
     * @param target a task
     * @param newTaskTimeCost the new estimated time cost of the task
     */
    public void setTaskTimeCost(Task target, double newTaskTimeCost) {
        requireNonNull(target);
        requireNonNull(newTaskTimeCost);
        target.setEstimatedTimeCost(newTaskTimeCost);
        int index = internalList.indexOf(target);
        internalList.set(index, target);
    }

    /**
     * Set the task weight.
     *
     * @param target a task
     * @param newTaskWeight the new weight of the task
     */
    public void setTaskWeight(Task target, double newTaskWeight) {
        requireNonNull(target);
        requireNonNull(newTaskWeight);
        target.setWeight(newTaskWeight);
        int index = internalList.indexOf(target);
        internalList.set(index, target);
    }

    /**
     * Set the task description.
     *
     * @param target a task
     * @param newTaskDescription the new name of the task
     */
    public void setTaskDescription(Task target, String newTaskDescription) {
        requireNonNull(target);
        requireNonNull(newTaskDescription);
        target.setTaskDescription(newTaskDescription);
        int index = internalList.indexOf(target);
        internalList.set(index, target);
    }

    /**
     * Set the task type
     * @param target a task
     * @param newTaskType the new task type
     */
    public void setTaskType(Task target, TaskType newTaskType) {
        requireNonNull(target);
        requireNonNull(newTaskType);
        target.setTaskType(newTaskType);
        int index = internalList.indexOf(target);
        internalList.set(index, target);
    }

    /**
     * Set the task date time
     * @param target a task
     * @param newDateTimes the new date and time
     */
    public void setTaskDateTime(Task target, LocalDateTime[] newDateTimes) {
        requireNonNull(target);
        requireNonNull(newDateTimes);
        target.setDateTimes(newDateTimes);
        int index = internalList.indexOf(target);
        internalList.set(index, target);
    }

    /**
     * Set module in task. Module will be checked for its existence outside this class
     * before this function is performed.
     * @param target a task
     * @param module a module for the task to be assigned to
     */
    public void setTaskMod(Task target, Module module) {
        requireNonNull(target);
        requireNonNull(module);
        target.setModule(module);
        int index = internalList.indexOf(target);
        internalList.set(index, target);

    }

    /**
     * Sort tasks by the given {@code keyword}.
     */
    public void sortTasks(String keyword) {

        if (keyword.equalsIgnoreCase("deadline / task start date")) {
            FXCollections.sort(internalList, Task::compareTo);
        } else if (keyword.equalsIgnoreCase("task name")) {
            FXCollections.sort(internalList, Comparator.comparing(Task::getTaskName));
        } else {
            FXCollections.sort(internalList, Comparator.comparing(Task::getCreationDateTime));
        }
    }

}
