// modelled after https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/

package persistence;

import java.util.List;

import model.JavaProject;

// a class to serielize our application's state (a list of JavaProjects) to a file
public class JsonWriter {
    // EFFECTS: build a jsonwriter with a specified path
    public JsonWriter(String path) {
        // stub
    }

    // EFFECTS: opens the json file, writes our application state and then closes the file
    public void write(List<JavaProject> projects) throws WriteError {
        // stub
    }
}
