package model;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestJavaProject {
    private JavaProject project;

    @BeforeEach
    public void beforeEach() {
        project = new JavaProject(Path.of("etc", "MyProject"));
    }

    @Test
    public void mainPathTest() {
        assertEquals(Path.of("etc", "MyProject", "src", "main"), project.getMainPath());
    }

    @Test
    public void loadAndGenTest() throws CodeException {
        project.loadJavaFile(Path.of("etc", "MyProject", "src", "main", "model", "Class1.java"), "package model;");
        project.loadJavaFile(Path.of("etc", "MyProject", "src", "main", "model", "d", "Class2.java"), "package model.d; import model.Class1;");

        assertEquals(
            "package model:\n\tclass Class1\n\npackage model.d:\n\tclass Class2\n\t\t-> model.Class1\n", project.genPackageDiagram().stringify());
    }
}
