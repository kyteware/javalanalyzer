package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestTokenTree {
    TokenTree oneToken = new TokenTree(":");
    TokenTree manyTokens;
    TokenTree emptyBraces;
    TokenTree emptyBracesDeep;
    TokenTree branchMany;
    TokenTree arrayDecl;
    TokenTree fnDecl;
    TokenTree classDecl;
    TokenTree javaFile;
    
    @BeforeEach
    public void beforeEach() {
        oneToken = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "package"
        ));
        manyTokens = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "1, 2, \"hi\", bob"
        ));
        emptyBraces = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "[]"
        ));
        emptyBracesDeep = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "({})"
        ));
        branchMany = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "{ 1, 2, 3 } [()]"
        ));
    }
    
    @Test
    public void parseFlatTest() {
        assertTrue(oneToken.isBranch());
        assertTrue(!oneToken.isLeaf());
        assertTrue(oneToken.getTrees().get(0).isLeaf());
        assertTrue(!oneToken.getTrees().get(0).isBranch());
        assertEquals("package", oneToken.getTrees().get(0).getToken());

        assertTrue(manyTokens.getTrees().get(3).isLeaf());
        assertEquals(",", manyTokens.getTrees().get(3).getToken());
    }

    @Test
    public void parseEmptiesTest() {
        emptyBraces = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "[]"
        ));
        assertEquals(1, emptyBraces.getTrees().size());
        assertEquals(0, emptyBraces.getTrees().get(0).getTrees().size());
        assertEquals(TokenTree.DELIMITED_SQUARY, emptyBraces.getTrees().get(0).getDelimiters());
        
        TokenTree outer = emptyBracesDeep.getTrees().get(0);
        assertEquals(TokenTree.DELIMITED_ROUND, outer.getDelimiters());
        assertEquals(1, outer.getTrees().size());
        TokenTree inner = outer.getTrees().get(0);
        assertEquals(TokenTree.DELIMITED_CURLY, inner.getDelimiters());
        assertEquals(0, inner.getTrees().size());
    }

    @Test
    public void parseBranchManyTest() {
        TokenTree array = branchMany.getTrees().get(0);
        assertEquals(TokenTree.DELIMITED_CURLY, array.getDelimiters());
        assertTrue(array.isBranch());
        TokenTree expected = TokenTree.parseJavaTokens(Tokenizer.tokenize("1, 2, 3"));
        for (int i=0; i<expected.getTrees().size(); i++) {
            String wanted = expected.getTrees().get(i).getToken();
            String actual = array.getTrees().get(i).getToken();
            assertEquals(wanted, actual);
        }

        TokenTree extras = branchMany.getTrees().get(1);
        assertTrue(extras.isBranch());
        assertEquals(TokenTree.DELIMITED_SQUARY, extras.getDelimiters());
    }
}
