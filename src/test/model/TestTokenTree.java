package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestTokenTree {
    TokenTree leafSingle;
    TokenTree leafMultiple;
    TokenTree branchSemiSeperated;
    TokenTree branchCommaSeperated;
    TokenTree branchEmptyBraces;
    TokenTree branchEmptyBracesDeep;
    TokenTree arrayDecl;
    TokenTree fnDecl;
    TokenTree classDecl;
    TokenTree javaFile;

    @BeforeEach
    public void beforeEach() {
        leafSingle = TokenTree.parseFlatTokens(Tokenizer.tokenize(
            "return"
        ));
        leafMultiple = TokenTree.parseFlatTokens(Tokenizer.tokenize(
            "return \"bob\""
        ));
        branchSemiSeperated = TokenTree.parseFlatTokens(Tokenizer.tokenize(
            "int bob; bob=1; return \"bob\";"
        ));
        branchCommaSeperated = TokenTree.parseFlatTokens(Tokenizer.tokenize(
            "1, 2, \"hi\", bob"
        ));
        branchEmptyBraces = TokenTree.parseFlatTokens(Tokenizer.tokenize(
            "[]"
        ));
        branchEmptyBracesDeep = TokenTree.parseFlatTokens(Tokenizer.tokenize(
            "({})"
        ));
        arrayDecl = TokenTree.parseFlatTokens(Tokenizer.tokenize(
            "int[] = {1, 2, 3}"
        ));
        javaFile = TokenTree.parseJavaFileTokens(Tokenizer.tokenize(
            "import java.util.ArrayList;"
            + "public class Main {"
            + " public static void main(String[] args) {"
            + "  System.out.println(\"hiii\");"
            + " }"
            + "}"
        ));
    }
    
    @Test
    public void parseLeavesTest() {
        assertTrue(leafSingle.isLeaf());
        assertEquals("return", leafSingle.getTokens().get(0));

        assertTrue(leafMultiple.isLeaf());
        assertEquals("return", leafSingle.getTokens().get(0));
        assertEquals("bob", leafSingle.getTokens().get(1));
    }

    @Test
    public void parseBranchesTest() {
        assertTrue(branchSemiSeperated.isBranch());
        assertEquals(TokenTree.SEPERATED_SEMICOLON, branchSemiSeperated.getSeperators());
        TokenTree statement1 = branchSemiSeperated.getTrees().get(0);
        assertTrue(statement1.isLeaf());
        assertEquals("int", statement1.getTokens().get(0));
        assertEquals("bob", statement1.getTokens().get(1));
        assertEquals(2, statement1.getTokens().size());
        assertTrue(branchSemiSeperated.getTrees().get(1).isLeaf());
        assertTrue(branchSemiSeperated.getTrees().get(2).isLeaf());

        assertTrue(branchCommaSeperated.isBranch());
        assertEquals(TokenTree.SEPERATED_COMMA, branchCommaSeperated.getSeperators());
    }

    @Test
    public void parseEmptiesTest() {
        assertTrue(branchEmptyBraces.isBranch());
        assertEquals(TokenTree.DELIMITED_SQUARY, branchEmptyBraces.getDelimiters());
        assertEquals(TokenTree.SEPERATED_NA, branchEmptyBraces.getSeperators());
        assertEquals(0, branchEmptyBraces.getTrees().size());

        assertTrue(branchEmptyBracesDeep.isBranch());
        assertEquals(TokenTree.DELIMITED_ROUND, branchEmptyBracesDeep.getDelimiters());
        assertEquals(TokenTree.SEPERATED_NA, branchEmptyBracesDeep.getSeperators());
        assertEquals(1, branchEmptyBracesDeep.getTrees().size());
        TokenTree inside = branchEmptyBracesDeep.getTrees().get(0);
        assertEquals(TokenTree.DELIMITED_CURLY, inside.getDelimiters());
        assertEquals(TokenTree.SEPERATED_NA, inside.getSeperators());
        assertEquals(0, inside.getTrees().size());
    }

    public void parseArrayDeclTest() {
        assertTrue(arrayDecl.isBranch());
        assertEquals(TokenTree.SEPERATED_NA, arrayDecl.getSeperators());
        TokenTree declType = arrayDecl.getTrees().get(0);
        TokenTree arrayDesg = arrayDecl.getTrees().get(1);
        TokenTree eq = arrayDecl.getTrees().get(2);
        TokenTree array = arrayDecl.getTrees().get(3);

        assertTrue(declType.isLeaf());
        String[] declTypeTokens = { "int" };
        assertEquals(Arrays.asList(declTypeTokens), declType.getTokens());

        assertTrue(arrayDesg.isBranch());
        assertEquals(TokenTree.DELIMITED_SQUARY, arrayDesg.getDelimiters());
        assertEquals(TokenTree.SEPERATED_NA, arrayDesg.getSeperators());
        assertEquals(0, arrayDesg.getTokens().size());

        assertTrue(eq.isLeaf());
        String[] eqTokens = { "=" };
        assertEquals(Arrays.asList(eqTokens), eq.getTokens());

        assertTrue(array.isBranch());
        assertEquals(TokenTree.SEPERATED_COMMA, array.getSeperators());
        assertEquals(TokenTree.DELIMITED_CURLY, array.getDelimiters());
        assertEquals(3, array.getTrees().size());
        String[] two = { "2" };
        assertEquals(Arrays.asList(two), array.getTrees().get(0).getTokens());
    }

    public void parseJavaFileTest() {
        assertTrue(javaFile.isBranch());
        assertEquals(TokenTree.DELIMITED_ROOT, javaFile.getDelimiters());
        assertEquals(TokenTree.SEPERATED_SEMICOLON, javaFile.getSeperators());
        TokenTree importStatement = javaFile.getTrees().get(0);
        TokenTree classDecl = javaFile.getTrees().get(1);

        assertTrue(importStatement.isLeaf());
        assertEquals(6, importStatement.getTokens().size());

        assertTrue(classDecl.isBranch());
        assertEquals(4, classDecl.getTrees().size());
    }
}
