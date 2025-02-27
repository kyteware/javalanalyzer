package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.JavaProject;

public class TestJsonReader {
    JsonReader goodFilled;
    JsonReader goodEmpty;
    JsonReader badMissingField;
    JsonReader badCurly;

    @BeforeEach
    public void beforeEach() {
        goodFilled = new JsonReader("./data/testGoodFilled.json");
        goodEmpty = new JsonReader("./data/testGoodEmpty.json");
        badMissingField = new JsonReader("./data/testBadMissingField.json");
        badCurly = new JsonReader("./data/testBadCurly.json");
    }

    @Test
    public void readEmptyTest() {
        try {
            assertEquals(0, goodEmpty.read().size());
        } catch (ReadError e) {
            fail("this should definitly work");
        }
    }

    @Test
    public void readFilledTest() {
        List<JavaProject> res;
        try {
            res = goodFilled.read();
            TestJson.saturateExpectedProjects();
            TestJson.assertJavaProjectsEquals(TestJson.expectedFullProjects, res);
        } catch (ReadError e) {
            fail("this should definitly work");
        }
    }

    @Test
    public void readBadFails() {
        try {
            badCurly.read();
            fail("this should break");
        } catch (ReadError e) {
            // worked as expected
        }

        try {
            badMissingField.read();
            fail("this should break");
        } catch (ReadError e) {
            // worked as expected
        }
    }
}
