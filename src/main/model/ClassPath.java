package model;

import java.util.List;

// a java classpath
public class ClassPath {
    // fields

    // EFFECTS: builds a classpath from a list of identifiers, from major to minor
    public ClassPath(List<String> fullPath) {
        // stub
    }

    // EFFECTS: gets the path of the module (excluding the path name)
    public List<String> getMod() {
        // stub
        return null;
    }

    // EFFECTS: get the name of the class in question
    public String getClassName() {
        // stub
        return null;
    }

    // EFFECTS: generate a java-accurate string representation of the classpath
    public String stringify() {
        // stub
        return null;
    }
}
