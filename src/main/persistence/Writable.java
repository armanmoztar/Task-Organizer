package persistence;

import org.json.JSONObject;

public interface Writable {
    // Writable class for Json
    // code referenced from JsonSerializationDemo repo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}