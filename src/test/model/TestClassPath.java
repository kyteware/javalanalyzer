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
    }

    @Test
    public void testConstructor() {
        assertEquals(justClassDir, justClass.getPackagePath());
        assertEquals("MyClass", justClass.getClassName());
        assertEquals(justPackageDir, justPackage.getPackagePath());
        assertEquals(null, justPackage.getClassName());
        assertEquals(fullPathDir, fullPath.getPackagePath());
        assertEquals("C3", fullPath.getClassName());
    }

    @Test
    public void testConstructorThrowsOnWildcard() {
        assertThrows(UnsupportedOperationException.class, () -> {
            new ClassPath(null, "*");
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            new ClassPath(justClassDir, "*");
        });
    }

    @Test
    public void testStringify() {
        assertEquals("MyClass", justClass.stringify());
        assertEquals("outer.inner", justPackage.stringify());
        assertEquals("p1.p2.C3", fullPath.stringify());
    }
}