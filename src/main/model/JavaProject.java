package model;

import java.nio.file.Path;

// a representation of an entire java project (requires help loading java files from sys)
public class JavaProject {
    // EFFECTS: initialize a java project
    public JavaProject(Path path) {
        // stub
    }

    // EFFECTS: gets the path to the main code of the project
    public Path getMainPath() {
        return null; // stub
    }

    // EFFECTS: load a java file into the project
    public void loadJavaFile(Path path, String code) {
        // stub
    }

    public PackageDiagram genPackageDiagram() {
        return null; //stub
    }
}
