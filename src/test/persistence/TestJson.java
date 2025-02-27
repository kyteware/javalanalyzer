package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.JavaProject;

public class TestJson {
    public static List<JavaProject> expectedEmptyProjects;
    public static List<JavaProject> expectedFullProjects;

    public static void saturateExpectedProjects() {
        expectedEmptyProjects = new ArrayList<>();
        expectedFullProjects = new ArrayList<>();
        
        JavaProject p1 = new JavaProject(Paths.get("/path/to/Project1"));
        try {
            p1.loadJavaFile(
                Paths.get("/path/to/Project1/src/model/Class1.java"), 
                "package model; import util.MyUtils;"
            );
        } catch (Exception e) {
            fail("this should NOT fail :(");
        }
        try {
            p1.loadJavaFile(
                Paths.get("/path/to/Project1/src/util/MyUtil.java"), 
                "package util;"
            );
        } catch (Exception e) {
            fail("this should NOT fail :(");
        }

        JavaProject p2 = new JavaProject(Paths.get("/path/to/Project2"));
        try {
            p2.loadJavaFile(
                Paths.get("/path/to/Project2/src/ui/Main.java"), 
                "package model;"
            );
        } catch (Exception e) {
            fail("this should NOT fail :(");
        }

        expectedFullProjects.add(p1);
        expectedFullProjects.add(p2);
    }

    public static void assertJavaProjectEquals(JavaProject expected, JavaProject actual) {
        assertEquals(expected.getMainPath(), actual.getMainPath());
        assertEquals(expected.getClasses(), actual.getClasses());
        assertEquals(expected.getImports(), actual.getImports());
    }

    public static void assertJavaProjectsEquals(List<JavaProject> expected, List<JavaProject> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i=0; i<expected.size(); i++) {
            assertJavaProjectEquals(expected.get(i), actual.get(i));
        }
    }
}
