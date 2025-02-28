// modelled after https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/

package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.ClassPath;
import model.JavaProject;

// a class to serielize our application's state (a list of JavaProjects) to a file
public class JsonWriter {
    private static int IDENT_FACTOR = 4;
    Path path;

    // EFFECTS: build a jsonwriter with a specified path
    public JsonWriter(String path) {
        this.path = Paths.get(path);
    }

    // EFFECTS: opens the json file, writes our application state and then closes the file
    public void write(List<JavaProject> projects) throws WriteError {
        JSONArray json = new JSONArray();

        for (JavaProject project : projects) {
            json.put(writeProject(project));
        }

        try {
            PrintWriter writer = new PrintWriter(new File(path.toUri()));
            writer.print(json.toString(IDENT_FACTOR));
            writer.close();
        } catch (FileNotFoundException e) {
            throw new WriteError();
        }     
    }

    // EFFECTS: write a java project to json
    private JSONObject writeProject(JavaProject project) {
        JSONObject projectJson = new JSONObject();

        projectJson.put("projectPath", project.getMainPath().getParent().getParent().toString());
        projectJson.put("classes", writeClasses(project.getClasses()));
        projectJson.put("imports", writeImports(project.getImports()));

        return projectJson;
    }

    // EFFECTS: write a list of classes to json
    private JSONArray writeClasses(List<ClassPath> classes) {
        JSONArray classesJson = new JSONArray();

        for (ClassPath classPath : classes) {
            classesJson.put(writeClassPath(classPath));
        }

        return classesJson;
    }

    // EFFECTS: write a list of imports to json
    private JSONArray writeImports(List<SimpleEntry<ClassPath, ClassPath>> imports) {
        JSONArray importJson = new JSONArray();

        for (SimpleEntry<ClassPath, ClassPath> entry : imports) {
            JSONObject entryJson = new JSONObject();
            entryJson.put("importer", writeClassPath(entry.getKey()));
            entryJson.put("imported", writeClassPath(entry.getValue()));
            importJson.put(entryJson);
        }

        return importJson;
    }

    // EFFECTS: write a class path to json
    private JSONObject writeClassPath(ClassPath cp) {
        JSONObject cpJson = new JSONObject();

        cpJson.put("className", cp.getClassName());

        JSONArray packagePath = new JSONArray();
        for (String packageSeg: cp.getPackagePath()) {
            packagePath.put(packageSeg);
        }

        cpJson.put("packagePath", packagePath);

        return cpJson;
    }
}
