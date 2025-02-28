package model;

import java.util.List;

import model.exception.UnsupportedWildcardImport;

// a java classpath, can represent package paths if the class name is null
public class ClassPath {
    private List<String> packagePath;
    private String className;

    // REQUIRES: path is not null
    // EFFECTS: builds a classpath from a list of identifiers, from major to minor
    //          will throw error on wildcard import, not supported
    public ClassPath(List<String> packagePath, String className) {
        this.packagePath = packagePath;
        if (className != null && className.equals("*")) {
            throw new UnsupportedWildcardImport();
        }
        this.className = className;
    }

    // EFFECTS: gets the path of the module (excluding the path name)
    public List<String> getPackagePath() {
        return packagePath;
    }

    // EFFECTS: get the name of the class in question
    public String getClassName() {
        return className;
    }

    // EFFECTS: generate a java-accurate string representation of the classpath
    public String stringify() {
        String pathString = String.join(".", packagePath);
        if (pathString.isEmpty() && className == null) {
            return "";
        }
        if (pathString.isEmpty()) {
            return className;
        }
        if (className == null) {
            return pathString;
        }
        return pathString + "." + className;
    }

    // EFFECTS: checks if classpath is equal to another classpath
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != ClassPath.class) {
            return false;
        }
        ClassPath other = (ClassPath) o;
        
        return packagePath.equals(other.packagePath) 
            && (className == other.className || className.equals(other.className));
    }
}
