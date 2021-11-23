package persistence;

import model.Category;
import model.Task;
import model.TaskList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Class tests the JsonWriter class and its methods
public class JsonWriterTest extends JsonTest {

    // code referenced from JsonSerializationDemo repo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    @Test
    public void testWriterInvalidFile() {
        try {
            TaskList tl = new TaskList("My Todo List");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTasklist() {
        try {
            TaskList tl = new TaskList("My Todo List");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTasklist.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTasklist.json");
            tl = reader.read();
            assertEquals("My Todo List", tl.getTaskListName());
            assertEquals(0, tl.getNumTasks());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralTasklist() {
        try {
            TaskList tl = new TaskList("My Todo List");
            tl.addTask(new Task("Buy notebook", Category.PERSONAL, 2));
            tl.addTask(new Task("Make changes to resume", Category.WORK, 3));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTasklist.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTasklist.json");
            tl = reader.read();
            assertEquals("My Todo List", tl.getTaskListName());
            List<Task> tasks = tl.getUnmodifiedTasks();
            assertEquals(2, tasks.size());
            checkTask("Buy notebook", 2, Category.PERSONAL, tasks.get(0));
            checkTask("Make changes to resume", 3, Category.WORK, tasks.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}