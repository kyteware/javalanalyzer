package model;

import java.nio.file.Path;
import java.util.AbstractMap.SimpleEntry;

import model.exception.CodeException;
import model.feature.JavaFileCode;

import java.util.ArrayList;
import java.util.List;

// a representation of an entire java project (requires help loading java files from sys)
public class JavaProject {
    Path projectPath;
    List<ClassPath> classes;
    List<SimpleEntry<ClassPath, ClassPath>> imports;

    // EFFECTS: initialize a java project
    public JavaProject(Path path) {
        projectPath = path;
        classes = new ArrayList<>();
        imports = new ArrayList<>();
    }

    // EFFECTS: initialize a java project with pre-loaded metadata
    public JavaProject(Path path, List<ClassPath> classes, List<SimpleEntry<ClassPath, ClassPath>> imports) {
        projectPath = path;
        this.classes = classes;
        this.imports = imports;
    }

    // EFFECTS: gets the path to the main code of the project
    public Path getMainPath() {
        return projectPath.resolve("src").resolve("main");
    }

    // EFFECTS: gets the name of the java project
    public String getName() {
        return projectPath.getFileName().toString();
    }

    // EFFECTS: gets the classes of the java project
    public List<ClassPath> getClasses() {
        return classes;
    }

    // EFFECTS: gets the imports of the java project
    public List<SimpleEntry<ClassPath, ClassPath>> getImports() {
        return imports;
    }

    // EFFECTS: load a java file into the project
    // MODIFIES: this
    // REQUIRES: is actually a java file and named appropriately
    public void loadJavaFile(Path path, String code) throws CodeException {
        JavaFileCode compiled = JavaFileCode.tryBuilding(
                TokenTree.parseJavaTokens(Tokenizer.tokenize(code)).getTrees()
        );
        String className = path.getFileName().toString().split("\\.")[0];
        ClassPath classPath = new ClassPath(compiled.getPackage().getPackagePath(), className);
        classes.add(classPath);
        for (ClassPath target : compiled.getImports()) {
            imports.add(new SimpleEntry<>(classPath, target));
        }
        EventLog.getInstance().logEvent(new Event(
                "Java file " + path.toString() + " loaded with " + imports.size() + " imports"
        ));
    }

    // EFFECTS: generate a package diagram for the java project
    public PackageDiagram genPackageDiagram() {
        return new PackageDiagram(classes, imports);
    }

    // EFFECTS: returns the number of classes loaded
    public int numClasses() {
        return classes.size();
    }

    // MODIFIES: this
    // EFFECTS: clears all the loaded classes, returning the numClasses to 0
    public void clearClasses() {
        imports.clear();
        classes.clear();
        EventLog.getInstance().logEvent(new Event(
                "Loaded classes of Java project " + projectPath.toString() + " cleared"
        ));
    }
}
