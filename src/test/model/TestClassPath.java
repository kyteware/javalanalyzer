package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestClassPath {
    private List<String> justClassDir;
    private ClassPath justClass;
    private List<String> justPackageDir;
    private ClassPath justPackage;
    private List<String> fullPathDir;
    private ClassPath fullPath;
    private ClassPath noPath;

    @BeforeEach
    public void beforeEach() {
        String[] rawJustClass = { };
        justClassDir = Arrays.asList(rawJustClass);
        justClass = new ClassPath(justClassDir, "MyClass");

        String[] rawJustPackage = { "outer", "inner" };
        justPackageDir = Arrays.asList(rawJustPackage);
        justPackage = new ClassPath(justPackageDir, null);

        String[] rawFullPath = { "p1", "p2" };
        fullPathDir = Arrays.asList(rawFullPath);
        fullPath = new ClassPath(fullPathDir, "C3");

        noPath = new ClassPath(new ArrayList<>(), null);
    }

    @Test
    public void constructorTest() {
        assertEquals(justClassDir, justClass.getPackagePath());
        assertEquals("MyClass", justClass.getClassName());
        assertEquals(justPackageDir, justPackage.getPackagePath());
        assertEquals(null, justPackage.getClassName());
        assertEquals(fullPathDir, fullPath.getPackagePath());
        assertEquals("C3", fullPath.getClassName());
    }

    @Test
    public void constructorThrowsOnWildcardTest() {
        assertThrows(UnsupportedOperationException.class, () -> {
            new ClassPath(null, "*");
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            new ClassPath(justClassDir, "*");
        });
    }

    @Test
    public void stringifyTest() {
        assertEquals("MyClass", justClass.stringify());
        assertEquals("outer.inner", justPackage.stringify());
        assertEquals("p1.p2.C3", fullPath.stringify());
        assertEquals("", noPath.stringify());
    }

    @Test
    public void equalsTest() {
        assertEquals(new ClassPath(new ArrayList<>(), "MyClass"), justClass);
        assertNotEquals(new ClassPath(new ArrayList<>(), "MyClasss"), justClass);
        assertNotEquals(justClass, null);
        assertNotEquals(justClass, "hi");
        assertEquals(new ClassPath(fullPathDir, "C3"), fullPath);
        assertEquals(new ClassPath(justPackageDir, null), justPackage);
    }
}