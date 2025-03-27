// code based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/

package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.ClassPath;
import model.Event;
import model.EventLog;
import model.JavaProject;

// a class for deserializing json into our project state (a list of JavaProjects)
public class JsonReader {
    Path path;
    
    // EFFECTS: builds a jsonreader from the specified path
    public JsonReader(String path) {
        this.path = Paths.get(path);
    }

    // EFFECTS: opens the json file, reads the state and then closes it
    public List<JavaProject> read() throws ReadError {
        String raw = readFile();

        try {
            JSONArray json = new JSONArray(raw);

            EventLog.getInstance().logEvent(new Event(
                    "Read the program state from JSON at path " + path.toString()
            ));

            return readProjectList(json);
        } catch (JSONException e) {
            throw new ReadError();
        }
    }

    // EFFECTS: read a file and return contents
    private String readFile() throws ReadError {
        StringBuilder contentBuilder = new StringBuilder();

        try {
            Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8);
            stream.forEach(s -> contentBuilder.append(s));
            stream.close();
        } catch (IOException e) {
            throw new ReadError();
        }

        return contentBuilder.toString();
    }

    // EFFECTS: reads json into a list of java projects
    private List<JavaProject> readProjectList(JSONArray json) {
        List<JavaProject> projects = new ArrayList<>();

        for (Object obj : json) {
            JSONObject rawObj = (JSONObject)obj;
            projects.add(readProject(rawObj));
        }

        return projects;
    }

    // EFFECTS: reads json into a java project
    private JavaProject readProject(JSONObject json) {
        String rawPath = json.getString("projectPath");
        Path path = Paths.get(rawPath);
        List<ClassPath> classes = new ArrayList<>();
        List<SimpleEntry<ClassPath, ClassPath>> imports = new ArrayList<>();

        JSONArray classObjs = json.getJSONArray("classes");
        for (Object rawClassObj : classObjs) {
            JSONObject classObj = (JSONObject)rawClassObj;
            ClassPath classPath = readClassPath(classObj);
            classes.add(classPath);
        }

        JSONArray importObjs = json.getJSONArray("imports");
        for (Object rawImportObj : importObjs) {
            JSONObject importObj = (JSONObject)rawImportObj;
            ClassPath from = readClassPath(importObj.getJSONObject("importer"));
            ClassPath to = readClassPath(importObj.getJSONObject("imported"));
            imports.add(new SimpleEntry<>(from, to));
        }

        return new JavaProject(path, classes, imports);
    }

    // EFFECTS: reads json into a classpath
    private ClassPath readClassPath(JSONObject json) {
        String className = json.getString("className");
        List<String> packagePath = new ArrayList<>();

        for (Object rawPathSeg : json.getJSONArray("packagePath")) {
            packagePath.add((String)rawPathSeg);
        }

        return new ClassPath(packagePath, className);
    }
}