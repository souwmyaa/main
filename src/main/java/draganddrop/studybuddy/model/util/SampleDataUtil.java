package draganddrop.studybuddy.model.util;

import java.time.LocalDateTime;
import java.util.ArrayList;

import draganddrop.studybuddy.model.ReadOnlyStudyBuddy;
import draganddrop.studybuddy.model.StudyBuddy;
import draganddrop.studybuddy.model.module.EmptyModule;
import draganddrop.studybuddy.model.module.Module;
import draganddrop.studybuddy.model.task.Task;
import draganddrop.studybuddy.model.task.TaskStatus;
import draganddrop.studybuddy.model.task.TaskType;

/**
 * Contains utility methods for populating {@code StudyBuddy} with sample data.
 */
public class SampleDataUtil {
    private static final String LONG_DESC_WITH_300_CHAR =
        "This is a long task description with 300 characters. 12345678901234567890123456789012345678901234567"
            + "This is a long task description with 300 characters. 12345678901234567890123456789012345678901234567"
            + "This is a long task description with 300 characters. 12345678901234567890123456789012345678901234567";
    private static final String SHORT_DESC = "This is a short description.";

    public static Task[] getSampleTasks() {
        Module emptyModule = new EmptyModule();
        Module cs2101 = new Module("Effective Communication for Computing Professionals",
            "CS2101");

        LocalDateTime[] dueSoonDuration = {LocalDateTime.now().plusDays(3),
            LocalDateTime.now().plusDays(7)};
        LocalDateTime[] pendingDuration = {LocalDateTime.now().plusDays(10),
            LocalDateTime.now().plusDays(17)};

        LocalDateTime[] dueSoonDateTime = {LocalDateTime.now().plusDays(3)};
        LocalDateTime[] pendingDateTime = {LocalDateTime.now().plusDays(10)};

        LocalDateTime creationDateTime = LocalDateTime.now();


        return new Task[]{

            new Task(emptyModule, TaskType.Assignment, "Ass 1", SHORT_DESC, 20.0,
                TaskStatus.PENDING, dueSoonDateTime, 5.0, creationDateTime),
            new Task(cs2101, TaskType.Presentation, "Presentation 1",
                LONG_DESC_WITH_300_CHAR, 20.0, TaskStatus.FINISHED, dueSoonDuration,
                3.0, creationDateTime),
            new Task(emptyModule, TaskType.Assignment, "Quiz 1", SHORT_DESC,
                2.0, TaskStatus.PENDING, pendingDateTime, 1.0, creationDateTime),
            new Task(cs2101, TaskType.Meeting, "Meeting 1", LONG_DESC_WITH_300_CHAR, 20.0,
                TaskStatus.PENDING, pendingDuration, 15.0, creationDateTime)
        };
    }

    public static Module[] getSampleModule() {
        Module emptyModule = new EmptyModule();
        Module cs2101 = new Module("Effective Communication for Computing Professionals",
                "CS2101");
        return new Module[]{emptyModule, cs2101};
    }

    public static ReadOnlyStudyBuddy getSampleStudyBuddy() {
        StudyBuddy sampleSb = new StudyBuddy();
        ArrayList<Task> sampleTasks = new ArrayList<>();
        for (Task sampleTask : getSampleTasks()) {
            sampleSb.addTask(sampleTask);
            sampleTasks.add(sampleTask);
        }

        for (Module mod: getSampleModule()) {
            sampleSb.addModule(mod);
        }
        Task.updateCurrentTaskList(sampleTasks);
        return sampleSb;
    }
}
