package persistence;

import static org.junit.jupiter.api.Assertions.*;

import model.JavaProject;

public class TestJson {
    public static void assertJavaProjectEquals(JavaProject expected, JavaProject actual) {
        assertEquals(expected.getMainPath(), actual.getMainPath());
        assertEquals(expected.getClasses(), actual.getClasses());
        assertEquals(expected.getImports(), actual.getImports());
    }
}
