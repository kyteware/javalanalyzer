package model;

import java.nio.file.Path;

// a representation of an entire java project (requires help loading java files from sys)
public class JavaProject {
    // EFFECTS: initialize a java project
    public JavaProject(Path path) {
        // stub
    }

    // EFFECTS: gets the path to the main code of the project (should append /src/main)
    public Path getMainPath() {
        return null; // stub
    }

    // EFFECTS: load a java file into the project
    // MODIFIES: this
    public void loadJavaFile(Path path, String code) {
        // stub
    }

    // EFFECTS: generate a package diagram for the java project
    public PackageDiagram genPackageDiagram() {
        return null; //stub
    }
}
