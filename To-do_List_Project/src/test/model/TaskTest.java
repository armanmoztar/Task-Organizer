package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Class tests the Task class and its methods
public class TaskTest {
    private Task t1;
    private Task t2;
    private Task t3;
    private Task t4;

    @BeforeEach
    public void runBefore() {
        t1 = new Task("Finish P1 for 210", Category.SCHOOL, 3);
        t2 = new Task("Buy milk",Category.PERSONAL, 5);
        t3 = new Task("Tell Leo to take shift", Category.WORK, 4);
        t4 = new Task("Meeting with Oliver", Category.PERSONAL, 9);
    }

    @Test
    public void testGetters() {
        assertEquals(t1.getText(), "Finish P1 for 210");
        assertEquals(t1.getCategory(), Category.SCHOOL);
        assertEquals(t1.getDueIn(), 3);
        assertEquals(t2.getText(), "Buy milk");
        assertEquals(t2.getCategory(), Category.PERSONAL);
        assertEquals(t2.getDueIn(), 5);
        assertEquals(t3.getText(), "Tell Leo to take shift");
        assertEquals(t3.getCategory(), Category.WORK);
        assertEquals(t3.getDueIn(), 4);
        assertEquals(t4.getText(), "Meeting with Oliver");
        assertEquals(t4.getCategory(), Category.PERSONAL);
        assertEquals(t4.getDueIn(), 9);
    }
}