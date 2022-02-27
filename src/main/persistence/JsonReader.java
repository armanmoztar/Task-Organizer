package persistence;

import model.Category;
import model.Task;
import model.TaskList;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//Class is able to read the taskList file corresponding to Json
public class JsonReader {

    private String source;
    // code referenced from JsonSerializationDemo repo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads taskList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TaskList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTaskList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses taskList from JSON object and returns it
    private TaskList parseTaskList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        TaskList tl = new TaskList(name);
        parseAddTasks(tl, jsonObject);
        return tl;
    }

    // MODIFIES: tl
    // EFFECTS: parses tasks from JSON object and adds them to taskList
    private void parseAddTasks(TaskList tl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            parseAddTask(tl, nextTask);
        }
    }

    // MODIFIES: tl
    // EFFECTS: parses task from JSON object and adds it to the taskList
    private void parseAddTask(TaskList tl, JSONObject jsonObject) {
        String name = jsonObject.getString("text");
        Category category = Category.valueOf(jsonObject.getString("category"));
        int dueIn = jsonObject.getInt("dueIn");
        Task t = new Task(name, category, dueIn);
        tl.addTask(t);
    }
}