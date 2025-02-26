// modelled after https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/

package persistence;

import java.util.List;

import model.JavaProject;

// a class to serielize our application's state (a list of JavaProjects) to a file
public class JsonWriter {
    // EFFECTS: build a jsonwriter with a specified path and opens it
    public JsonWriter(String path) {
        // stub
    }

    // EFFECTS: writes our application state to json and saves it
    public void write_and_save(List<JavaProject> projects) {
        // stub
    }

    // EFFECTS: close our json writer safely
    public void close() {
        // stub
    }
}
