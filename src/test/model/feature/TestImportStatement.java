package model.feature;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.ClassPath;
import model.TokenTree;
import model.Tokenizer;

import org.junit.jupiter.api.BeforeEach;

public class TestImportStatement {
    ImportStatement oneLevel;
    ClassPath oneLevelClassPath;
    ImportStatement multiLevel;
    ClassPath multiLevelClassPath;

    @BeforeEach
    public void beforeEach() {
        ArrayList<String> path = new ArrayList<>();
        path.add("myPackage");
        oneLevelClassPath = new ClassPath(new ArrayList<>(), "MyClass");
        oneLevel = new ImportStatement(oneLevelClassPath);
        ArrayList<String> multiPath = new ArrayList<>();
        multiPath.add("d1");
        multiPath.add("d2");
        multiLevelClassPath = new ClassPath(multiPath, "DeepClass");
        multiLevel = new ImportStatement(multiLevelClassPath);
    }

    @Test
    public void constructorTest() {
        assertEquals(oneLevelClassPath, oneLevel.getClassPath());
        assertEquals(multiLevelClassPath, multiLevel.getClassPath());
    }

    @Test
    public void tryBuildingNormalTest() {
        TokenTree singleTree = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import myPackage.MyClass;"
        ));
        List<TokenTree> singleSlice = singleTree.getTrees();
        assertEquals(oneLevel, ImportStatement.tryBuilding(singleSlice));
        assertEquals(0, singleSlice.size());

        TokenTree multiTree = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import d1.d2.DeepClass;"
        ));
        List<TokenTree> multiSlice = multiTree.getTrees();
        assertEquals(multiLevel, ImportStatement.tryBuilding(multiSlice));
        assertEquals(0, multiSlice.size());
    }

    @Test
    public void tryBuildingWrongTest() {
        assertNull(ImportStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import x.y.z"
        )).getTrees()));

        assertNull(ImportStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import A"
        )).getTrees()));

        assertNull(ImportStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "package x.y.z;"
        )).getTrees()));

        assertNull(ImportStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import x,y,z;"
        )).getTrees()));

        assertNull(ImportStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import {} x.y.z;"
        )).getTrees()));

        assertNull(ImportStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import bleh x.y.z;"
        )).getTrees()));
    }

    @Test
    public void tryBuildingExtraTest() {
        TokenTree singleTree = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import myPackage.MyClass;;;;;"
        ));
        List<TokenTree> singleSlice = singleTree.getTrees();
        assertEquals(oneLevel, ImportStatement.tryBuilding(singleSlice));
        assertEquals(4, singleSlice.size());

        TokenTree multiTree = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import d1.d2.DeepClass; class Bob"
        ));
        List<TokenTree> multiSlice = multiTree.getTrees();
        assertEquals(multiLevel, ImportStatement.tryBuilding(multiSlice));
        assertEquals(2, multiSlice.size());
    }
}
