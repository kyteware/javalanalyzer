package model;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestJavaProject {
    private JavaProject project;
    private JavaProject preLoaded;

    @BeforeEach
    public void beforeEach() {
        project = new JavaProject(Path.of("etc", "MyProject"));
        preLoaded = new JavaProject(Paths.get("/etc/Bob"), new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void mainPathTest() {
        assertEquals(Path.of("etc", "MyProject", "src", "main"), project.getMainPath());
        assertEquals(Path.of("/", "etc", "Bob", "src", "main"), preLoaded.getMainPath());
    }

    @Test
    public void getNameTest() {
        assertEquals("MyProject", project.getName());
    }

    @Test
    public void getClassesTest() {
        assertEquals(new ArrayList<>(), project.getClasses());
        assertEquals(new ArrayList<>(), preLoaded.getClasses());
    }

    @Test
    public void getImportsTest() {
        assertEquals(new ArrayList<>(), project.getImports());
        assertEquals(new ArrayList<>(), preLoaded.getImports());
    }

    @Test
    public void loadAndGenTest() throws CodeException {
        project.loadJavaFile(Path.of("etc", "MyProject", "src", "main", "model", "Class1.java"), "package model;");
        project.loadJavaFile(Path.of("etc", "MyProject", "src", "main", "model", "d", "Class2.java"), "package model.d; import model.Class1;");

        assertEquals(
            "package model:\n\tclass Class1\n\npackage model.d:\n\tclass Class2\n\t\t-> model.Class1\n", project.genPackageDiagram().stringify());
    }

    @Test
    public void numAndClearTest() throws CodeException {
        assertEquals(0, project.numClasses());
        project.loadJavaFile(Path.of("etc", "MyProject", "src", "main", "model", "Class1.java"), "package model;");
        assertEquals(1, project.numClasses());
        project.loadJavaFile(Path.of("etc", "MyProject", "src", "main", "model", "d", "Class2.java"), "package model.d; import model.Class1;");
        assertEquals(2, project.numClasses());
        project.clearClasses();
        assertEquals(0, project.numClasses());
    }
}
