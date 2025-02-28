package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.JavaProject;

public class TestJsonWriter {
    JsonWriter writer;
    JsonReader reader;

    @BeforeEach
    public void beforeEach() {
        writer = new JsonWriter("./data/testWriter.json");
        reader = new JsonReader("./data/testWriter.json");
        TestJson.saturateExpectedProjects();
    }

    @Test
    public void emptyWrite() {
        try {
            writer.write(TestJson.expectedEmptyProjects);
        } catch (WriteError e) {
            fail("that should have worked");
        }
        
        try {
            List<JavaProject> res = reader.read();
            TestJson.assertJavaProjectsEquals(TestJson.expectedEmptyProjects, res);
        } catch (ReadError e) {
            fail("that should have worked");
        }
    }

    @Test
    public void completeWrite() {
        try {
            writer.write(TestJson.expectedFullProjects);
        } catch (WriteError e) {
            fail("that should have worked");
        }
        
        try {
            List<JavaProject> res = reader.read();
            TestJson.assertJavaProjectsEquals(TestJson.expectedFullProjects, res);
        } catch (ReadError e) {
            fail("that should have worked");
        }
    }

    @Test
    public void writeNotExistsTest() {
        try {
            new JsonWriter("../../../../../../../../../../../../..").write(new ArrayList<>());
            fail();
        } catch (WriteError e) {
            // wokred as expected
        }
    }
}