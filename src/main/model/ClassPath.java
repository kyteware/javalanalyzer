package model;

import java.util.List;

// a java classpath, can represent package paths if the class name is null
public class ClassPath {
    // fields

    // REQUIRES: path is not null
    // EFFECTS: builds a classpath from a list of identifiers, from major to minor
    //          will throw error on wildcard import, not supported
    public ClassPath(List<String> packagePath, String className) {
        // stub
    }

    // EFFECTS: gets the path of the module (excluding the path name)
    public List<String> getPackagePath() {
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
