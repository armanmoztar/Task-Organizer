package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Class represents the actual Task list (to-do list) where the
// individual tasks lie

public class TaskList implements Writable {
    private String name; //
    private ArrayList<Task> todoList;
    private EventLog eventLog;

    //Constructor
    //EFFECTS: constructs a todoList with a name and an empty list of tasks
    public TaskList(String name) {
        this.name = name;
        this.todoList = new ArrayList<>();
        this.eventLog = EventLog.getInstance();
    }

    //EFFECTS: returns the eventLog
    public EventLog getEventLog() {
        return eventLog;
    }

    //MODIFIES: this
    //EFFECTS: returns true if the todoList is empty, false otherwise
    public boolean isEmpty() {
        if (todoList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    //MODIFIES: this
    //EFFECTS: returns number of tasks in todoList
    public int getNumTasks() {
        return todoList.size();
    }

    // EFFECTS: returns an unmodifiable list of tasks in this todoList
    public List<Task> getUnmodifiedTasks() {
        return Collections.unmodifiableList(todoList);
    }

    //EFFECTS: returns the name of the TaskList
    public String getTaskListName() {
        return name;
    }

    //MODIFIES: this
    //EFFECTS: returns true if the task is in the list, false otherwise
    public boolean containsTask(Task t) {
        if (todoList.contains(t)) {
            return true;
        } else {
            return false;
        }
    }

    //REQUIRES: task must be string
    //MODIFIES: this
    //EFFECTS: adds a task to the list
    public void addTask(Task t) {
        todoList.add(t);
        EventLog.getInstance().logEvent(new Event("Task Item added to List."));
    }

    //MODIFIES: this
    //EFFECTS: removes a task from the list
    public void removeTask(int index) {
        todoList.remove(index);
        EventLog.getInstance().logEvent(new Event("Task Item removed from List."));
    }

    //EFFECTS: returns the tasks in the todoList
    // as an ordered Array List
    public ArrayList<Task> printArrayTaskList() {
        ArrayList<Task> list = todoList;
        return list;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("tasks", tasksToJson());
        return json;
    }

    // EFFECTS: returns tasks in this taskList as a JSON array
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : todoList) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }
}