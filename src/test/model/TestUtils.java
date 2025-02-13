package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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

    @Test
    public void takeTokenTest() throws Exception {
        List<TokenTree> sample = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "package ; {} "
        )).getTrees();

        assertEquals("package", Utils.takeToken(sample));
        assertEquals(2, sample.size());

        assertEquals(";", Utils.takeToken(sample));
        assertEquals(1, sample.size());

        try {
            Utils.takeToken(sample);
            fail();
        } catch (UnexpectedToken e) {
            // worked properly;
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, sample.size());

        sample.remove(0);
        try {
            Utils.takeToken(sample);
            fail();
        } catch (NoMoreTokens e) {
            // worked properly;
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void takeTreeTest() throws Exception {
        List<TokenTree> sample = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "[] e"
        )).getTrees();

        TokenTree first = Utils.takeTree(sample);

        assertEquals(TokenTree.parseJavaTokens(Tokenizer.tokenize("[]")).getTrees().get(0).getTrees(), first.getTrees());
        assertEquals(TokenTree.parseJavaTokens(Tokenizer.tokenize("[]")).getTrees().get(0).getDelimiters(), first.getDelimiters());
        assertEquals(1, sample.size());

        try {
            Utils.takeTree(sample);
            fail();
        } catch (UnexpectedToken e) {
            // worked properly;
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, sample.size());

        sample.remove(0);
        try {
            Utils.takeTree(sample);
            fail();
        } catch (NoMoreTokens e) {
            // worked properly;
        } catch (Exception e) {
            fail();
        }
        assertEquals(0, sample.size());
    }
}
