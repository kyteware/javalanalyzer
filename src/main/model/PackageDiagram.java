package model;

import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

// representation of a package diagram
public class PackageDiagram {
    // EFFECTS: build a package diagram from a list of classes in classpath form and all of their imports
    public PackageDiagram(List<ClassPath> classes, Map<ClassPath, ClassPath> imports) {
        // stub
    }

    // EFFECTS: get the classpaths contained in the diagram
    public List<ClassPath> getClasses() {
        return null; //stub
    }

    // EFFECTS: get all the imports made in the diagram
    public List<SimpleEntry<ClassPath, ClassPath>> getImports() {
        return null; //stub
    }

    // EFFECTS: create a string representation of the package diagram
    public String stringify() {
        return null; //stub
    }
}
