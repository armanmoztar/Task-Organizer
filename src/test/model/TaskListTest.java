package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Class tests the TaskList class and its methods
public class TaskListTest {
    private TaskList testTaskList;
    private EventLog testEventLog;

    @BeforeEach
    public void runBefore() {
        testTaskList = new TaskList("name");
    }

    @Test
    public void testConstructor() {
        assertTrue(testTaskList.isEmpty());
        assertEquals(0, testTaskList.getNumTasks());
    }

    @Test
    public void testGetTaskListName() {
        TaskList tl = new TaskList("My Todo List");
        assertEquals("My Todo List", tl.getTaskListName());
        assertEquals(0, tl.getNumTasks());
    }

    @Test
    public void testGetUnmodifiedTasks() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTaskList.json");
        try {
            TaskList tl = reader.read();
            assertEquals("My Todo List", tl.getTaskListName());
            List<Task> tasks = tl.getUnmodifiedTasks();
            assertEquals(2, tasks.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testAddTask() {
        Task t = new Task("quiz", Category.SCHOOL, 3);
        testTaskList.addTask(t);
        assertTrue(testTaskList.containsTask(t));
        assertEquals(testTaskList.getNumTasks(), 1);
        assertEquals(testTaskList.getEventLog(), EventLog.getInstance());
    }

    @Test
    public void testRemoveTask() {
        Task t = new Task("quiz", Category.SCHOOL, 3);
        testTaskList.addTask(t);
        testTaskList.removeTask(0);
        assertTrue(testTaskList.isEmpty());
        assertEquals(testTaskList.getNumTasks(), 0);
        assertEquals(testTaskList.getEventLog(), EventLog.getInstance());
    }

    @Test
    public void testMultipleAddTasks() {
        Task t1 = new Task("quiz", Category.SCHOOL, 3);
        testTaskList.addTask(t1);
        Task t2 = new Task("go to coffee shop with girl", Category.PERSONAL, 2);
        testTaskList.addTask(t2);
        assertEquals(testTaskList.getNumTasks(), 2);
    }

    @Test
    public void testContainsTask() {
        Task t = new Task("test", Category.SCHOOL, 1);
        testTaskList.addTask(t);
        assertEquals(testTaskList.getNumTasks(), 1);
        Task tk = new Task("test", Category.SCHOOL, 1);
        assertTrue(testTaskList.containsTask(t));
        assertFalse(testTaskList.containsTask(tk));
    }

    @Test
    public void testPrintArrayList() {
        ArrayList<Task> t = new ArrayList<>();
        Task tk = new Task("Watch Squid games", Category.PERSONAL, 1);
        t.add(tk);
        testTaskList.addTask(tk);
        assertEquals(testTaskList.printArrayTaskList(), t);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(testTaskList.isEmpty());
        assertEquals(testTaskList.getNumTasks(), 0);
        Task t = new Task("Submit resume for job", Category.WORK, 3);
        testTaskList.addTask(t);
        assertTrue(testTaskList.containsTask(t));
        assertEquals(testTaskList.getNumTasks(), 1);
        assertFalse((testTaskList.isEmpty()));
    }
}