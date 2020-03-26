package draganddrop.studdybuddy.model.util;

import java.time.LocalDateTime;
import java.util.ArrayList;

import draganddrop.studdybuddy.logic.parser.TimeParser;

import draganddrop.studdybuddy.model.ReadOnlyAddressBook;
import draganddrop.studdybuddy.model.task.Task;
import draganddrop.studdybuddy.model.task.TaskStatus;
import draganddrop.studdybuddy.model.task.TaskType;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        draganddrop.studdybuddy.model.module.Module cs2103T = new draganddrop.studdybuddy.model.module.Module("Software Engineering", "CS2103T");
        draganddrop.studdybuddy.model.module.Module cs2101 = new draganddrop.studdybuddy.model.module.Module("Effective Communication for Computing Professionals",
            "CS2101");
        LocalDateTime[] dateTimesOne = {TimeParser.parseDateTime("23:59 12/12/2020"),
            TimeParser.parseDateTime("23:59 21/12/2020")};
        LocalDateTime[] dateTimesTwo = {TimeParser.parseDateTime("23:59 12/04/2020"),
            TimeParser.parseDateTime("23:59 21/04/2020")};
        LocalDateTime creationDateTime = LocalDateTime.now();


        return new Task[]{
            new Task(cs2103T, TaskType.Assignment, "Ass 1", "taskDescription", 20.0,
                TaskStatus.PENDING, dateTimesOne, 5.0, creationDateTime),
            new Task(cs2101, TaskType.Presentation, "Presentation 1",
                "Presentation taskDescription", 20.0, TaskStatus.FINISHED, dateTimesOne,
                3.0, creationDateTime),
            new Task(cs2103T, TaskType.Assignment, "Quiz 1", "Quiz taskDescription",
                2.0, TaskStatus.PENDING, dateTimesTwo, 1.0, creationDateTime),
            new Task(cs2101, TaskType.Meeting, "Meeting 1", "Meeting desc", 20.0,
                TaskStatus.PENDING, dateTimesTwo, 15.0, creationDateTime)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        draganddrop.studdybuddy.model.AddressBook sampleAb = new draganddrop.studdybuddy.model.AddressBook();
        ArrayList<Task> sampleTasks = new ArrayList<>();
        for (Task sampleTask : getSampleTasks()) {
            sampleAb.addTask(sampleTask);
            sampleTasks.add(sampleTask);
        }
        Task.updateCurrentTaskList(sampleTasks);
        return sampleAb;
    }
}
