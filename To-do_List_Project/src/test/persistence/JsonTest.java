package persistence;

import model.Category;
import model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

// code referenced from JsonSerializationDemo repo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//Class tests to make sure the Json corresponds with the tasks
// and checks if it has the right parameters
public class JsonTest {
    protected void checkTask(String text, int dueIn, Category category, Task task) {
        assertEquals(text, task.getText());
        assertEquals(dueIn, task.getDueIn());
        assertEquals(category, task.getCategory());
    }
}