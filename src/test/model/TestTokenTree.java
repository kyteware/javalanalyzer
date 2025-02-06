package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestTokenTree {
    TokenTree flatSingle;
    TokenTree flatMultiple;
    TokenTree flatWithParens;
    List<TokenTree> flatSeveralStatements;
    TokenTree emptyClass;
    TokenTree filledClass;
    List<TokenTree> manyClasses;

    @BeforeEach
    public void beforeEach() {
        String[] flatSingleTokens = { "return" };
        flatSingle = new TokenTree(Arrays.asList(flatSingleTokens), null);
        String[] flatMultipleTokens = { "private", "MyClass", "bob"};
        flatMultiple = new TokenTree(Arrays.asList(flatMultipleTokens), null);
        List<String> flatWithParensTokens = Tokenizer.tokenize(
            "jimmy = new Person(jimmySoul)"
        );
        flatWithParens = new TokenTree(flatWithParensTokens, null);
        List<String> emptyClassTokens = Tokenizer.tokenize(
            "class MyClass"
        );
        emptyClass = new TokenTree(emptyClassTokens, new ArrayList<>());
        List<String> filledClassTokens = Tokenizer.tokenize(
            "public class Glass { private String aString; }"
        );
        filledClass = new TokenTree(filledClassTokens);
        List<String> manyClassesTokens = Tokenizer.tokenize(
            "public class C1 { public void myFn() { print(2) } }"
            + "class C2 { static void OtherFn() {} }"
        );
        manyClasses = new TokenTree(manyClassesTokens);
    }
    
    @Test
    public void testConstructingFlat() {
        String[] flatSingleContent = { "return" };
        assertEquals(Arrays.asList(flatSingleContent), flatSingle.getContent());
        String[] flatMultipleContent = 
    }
}
