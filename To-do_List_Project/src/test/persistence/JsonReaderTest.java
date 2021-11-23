package persistence;

import model.Category;
import model.Task;
import model.TaskList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Class tests the JsonReader class and its methods
public class JsonReaderTest extends JsonTest {
    // code referenced from JsonSerializationDemo repo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TaskList tl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTaskList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTaskList.json");
        try {
            TaskList tl = reader.read();
            assertEquals("My Todo List", tl.getTaskListName());
            assertEquals(0, tl.getNumTasks());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTaskList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTaskList.json");
        try {
            TaskList tl = reader.read();
            assertEquals("My Todo List", tl.getTaskListName());
            List<Task> tasks = tl.getUnmodifiedTasks();
            assertEquals(2, tasks.size());
            checkTask("Finish project and study so i don't fail 210", 3, Category.SCHOOL, tasks.get(0));
            checkTask("Get some bread", 2, Category.PERSONAL, tasks.get(1));
        } catch (IOException e) {
            fail("Unable to read from file");
        }
    }
}