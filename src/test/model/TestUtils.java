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
        assertFalse(Utils.isWord("3fred"));
        assertFalse(Utils.isWord(""));
        assertFalse(Utils.isWord("{}"));
        assertFalse(Utils.isWord("432"));
        assertFalse(Utils.isWord("sadfasdf.sadf"));
        assertFalse(Utils.isWord(" "));
    }
}
