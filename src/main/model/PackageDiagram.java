package model;

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

// representation of a package diagram
public class PackageDiagram {
    List<ClassPath> classes;
    List<SimpleEntry<ClassPath, ClassPath>> imports;

    // EFFECTS: build a package diagram from a list of classes in classpath form and all of their imports, filtering out external imports
    public PackageDiagram(List<ClassPath> classes, List<SimpleEntry<ClassPath, ClassPath>> imports) {
        this.classes = classes;
        this.imports = new ArrayList<>(imports);
    }

    // EFFECTS: get the classpaths contained in the diagram
    public List<ClassPath> getClasses() {
        return classes;
    }

    // EFFECTS: get all the imports made in the diagram
    public List<SimpleEntry<ClassPath, ClassPath>> getImports() {
        return imports;
    }

    // EFFECTS: create a string representation of the package diagram
    public String stringify() {
        List<List<String>> foundPackages = new ArrayList<>();

        for (ClassPath classPath : classes) {
            if (!foundPackages.contains(classPath.getPackagePath())) {
                foundPackages.add(classPath.getPackagePath());
            }
        }

        List<String> parts = new ArrayList<>();
        for (List<String> pkg : foundPackages) {
            parts.add(stringifyPackage(pkg));
        }

        return String.join("\n", parts);
    }

    // EFFECTS: stringify a specific package
    private String stringifyPackage(List<String> pkg) {
        List<ClassPath> foundClasses = new ArrayList<>();

        for (ClassPath classPath : classes) {
            if (classPath.getPackagePath().equals(pkg) && !foundClasses.contains(classPath)) {
                foundClasses.add(classPath);
            }
        }

        List<String> parts = new ArrayList<>();
        parts.add("package " + new ClassPath(pkg, null).stringify() + ":\n");
        for (ClassPath classPath : foundClasses) {
            parts.add("\tclass " + classPath.getClassName() + "\n");
            parts.add(stringifyImports(classPath));
        }

        return String.join("", parts);
    }

    // EFFECTS: stringify the imports of a class
    private String stringifyImports(ClassPath classPath) {
        List<String> parts = new ArrayList<>();

        for (SimpleEntry<ClassPath, ClassPath> pair : imports) {
            if (pair.getKey().equals(classPath)) {
                ClassPath imported = pair.getValue();
                parts.add("\t\t-> " + imported.stringify() + "\n");
            }
        }

        return String.join("", parts);
    }
}