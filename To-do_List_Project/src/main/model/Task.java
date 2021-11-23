package model;

import org.json.JSONObject;
import persistence.Writable;
// Class represents a Task with a description, deadline, and category

public class Task implements Writable {
    private String text; // description of task
    private int dueIn; // number of days left before due date of task
    private Category category; // category of task


    //REQUIRES: dueIn must be >= 0
    //EFFECTS: Constructs a task with the description, an integer representing
    // how many days left before task is due, and the category
    public Task(String text, Category category,  int dueIn) {
        this.text = text;
        this.category = category;
        this.dueIn = dueIn;
    }

    //EFFECTS: returns the task text (description)
    public String getText() {
        return text;
    }

    //EFFECTS: returns the number of days left for deadline
    public int getDueIn() {
        return dueIn;
    }

    //EFFECTS: returns the type of category for task
    public Category getCategory() {
        return category;
    }

    //EFFECTS: creates JsonObject and assigns the parameters to a given task
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("text", text);
        json.put("category", category);
        json.put("dueIn", dueIn);
        return json;
    }
}
