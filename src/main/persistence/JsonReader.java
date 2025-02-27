// code based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/

package persistence;

import java.util.List;

import model.JavaProject;

// a class for deserializing json into our project state (a list of JavaProjects)
public class JsonReader {
    // EFFECTS: builds a jsonreader from the specified path
    public JsonReader(String path) {
        // stub
    }

    // EFFECTS: opens the json file, reads the state and then closes it
    public List<JavaProject> read() throws ReadError {
        return null; //stub
    }
}