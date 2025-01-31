package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTokenizer {
    @Test
    void tokenizerJustWordsTest() {
        String justWords = "public static camelCase \nALLCAPS     \n\n ClassName";
        List<String> expected = new ArrayList<String>();
        expected.add("public");
        expected.add("static");
        expected.add("camelCase");
        expected.add("ALLCAPS");
        expected.add("ClassName");
        assertEquals(expected, Tokenizer.tokenize(justWords));
    }

    @Test
    void tokenizerJustSymbolsTest() {
        String justSymbols = "+-   {\n\n |         \n\r }} () *";
        List<String> expected = new ArrayList<String>();
        expected.add("+");
        expected.add("-");
        expected.add("{");
        expected.add("|");
        expected.add("}");
        expected.add("}");
        expected.add("(");
        expected.add(")");
        expected.add("*");
        assertEquals(expected, Tokenizer.tokenize(justSymbols));
    }

    @Test
    void tokenizerJustStringsTest() {
        String justStrings = "\"first string\" \"anotherstring\" \"ALLCAPS STRING\" \n\n\n   \"big          space\"  \"sstring with escaped \\\"\"";
        List<String> expected = new ArrayList<String>();
        expected.add("\"first string\"");
        expected.add("\"anotherstring\"");
        expected.add("\"ALLCAPS STRING\"");
        expected.add("\"big          space\"");
        expected.add("\"sstring with escaped \"");
        assertEquals(expected, Tokenizer.tokenize(justStrings));
    }

    @Test
    void tokenizerJustNumbersTest() {
        String justNumbers = "2134 2.34 2134. 12345678900987654321";
        List<String> expected = new ArrayList<String>();
        expected.add("2134");
        expected.add("2.34");
        expected.add("2134.");
        expected.add("12345678900987654321");
        assertEquals(expected, Tokenizer.tokenize(justNumbers));
    }

    @Test
    void tokenizerAllKindsTest() {
        String allKinds = "class 1234.3443 \n\n\"a string\" {}";
        List<String> expected = new ArrayList<String>();
        expected.add("class");
        expected.add("1234.3443");
        expected.add("\"a string\"");
        expected.add("{");
        expected.add("}");
        assertEquals(expected, Tokenizer.tokenize(allKinds));
    }
}
