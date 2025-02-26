// code based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/

package persistence;

import java.util.List;

import model.JavaProject;

// a class for deserializing json into our project state (a list of JavaProjects)
public class JsonReader {
    // EFFECTS: builds a jsonreader from the specified path and opens it
    public JsonReader(String path) {
        // stub
    }

    // EFFECTS: reads the state from the json file and returns it
    public List<JavaProject> read() {
        return null; //stub
    }

    // EFFECTS: closes the json file
    public void close() {
        // stub
    }
}