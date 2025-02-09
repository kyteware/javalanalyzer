package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUtils {
    @Test
    public void isWordYesTest() {
        assertTrue(Utils.isWord("fred"));
        assertTrue(Utils.isWord("fredBob"));
        assertTrue(Utils.isWord("e"));
        assertTrue(Utils.isWord("BOB_E"));
        assertTrue(Utils.isWord("_"));
        assertTrue(Utils.isWord("DF392903D"));
    }

    @Test
    public void isWordNoTest() {
        assertTrue(Utils.isWord("3fred"));
        assertTrue(Utils.isWord(""));
        assertTrue(Utils.isWord("{}"));
        assertTrue(Utils.isWord("432"));
        assertTrue(Utils.isWord("sadfasdf.sadf"));
        assertTrue(Utils.isWord(" "));
    }
}
