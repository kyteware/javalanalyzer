package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPackageDiagram {
    private PackageDiagram empty;
    private PackageDiagram justClasses;
    private PackageDiagram complete;
    ClassPath c1;
    ClassPath c2;
    ClassPath c3;
    ClassPath c4;
    ClassPath c5;
    List<ClassPath> classes1;
    List<ClassPath> classes2;
    List<SimpleEntry<ClassPath, ClassPath>> imports;

    @BeforeEach
    public void beforeEach() {
        empty = new PackageDiagram(new ArrayList<>(), new ArrayList<>());

        c1 = new ClassPath(buildLos("bob", null, null), "C1");
        c2 = new ClassPath(buildLos("my", "package", "place"), "C2");
        classes1 = new ArrayList<>();
        classes1.add(c1);
        classes1.add(c2);
        justClasses = new PackageDiagram(classes1, new ArrayList<>());
        c3 = new ClassPath(buildLos("d", null, null), "C3");
        c4 = new ClassPath(buildLos("d", null, null), "C4");
        c5 = new ClassPath(buildLos("d5", null, null), "C5");
        classes2 = new ArrayList<>();
        classes2.add(c3);
        classes2.add(c4);
        classes2.add(c5);
        imports = new ArrayList<>();
        imports.add(new SimpleEntry<>(c3, c4));
        imports.add(new SimpleEntry<>(c3, c5));
        imports.add(new SimpleEntry<>(c4, c5));

        complete = new PackageDiagram(classes2, imports);
    }

    @Test
    public void constructorTest() {
        assertEquals(0, empty.getClasses().size());
        assertEquals(0, empty.getImports().size());

        assertEquals(classes1, justClasses.getClasses());
        assertEquals(0, justClasses.getImports().size());

        assertEquals(classes2, complete.getClasses());
        assertEquals(imports, complete.getImports());
    }

    @Test
    public void stringifyTest() {
        assertEquals("", empty.stringify());
        assertEquals(
            "package bob:\n\tclass C1\n\npackage my.package.place:\n\tclass C2\n",
            justClasses.stringify()
        );
        assertEquals(
            "package d:\n\tclass C3\n\t\t-> d.C4\n\t\t-> d5.C5\n\tclass C4\n\t\t-> d5.C5\n\npackage d5:\n\tclass C5\n",
            complete.stringify()
        );
    }

    private List<String> buildLos(String s1, String s2, String s3) {
        List<String> los = new ArrayList<>();
        if (s1 != null) {
            los.add(s1);
        }
        if (s2 != null) {
            los.add(s2);
        }
        if (s3 != null) {
            los.add(s3);
        }

        return los;
    }
}
