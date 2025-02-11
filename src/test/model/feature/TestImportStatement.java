package model.feature;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import model.ClassPath;
import model.NoMoreTokens;
import model.TokenTree;
import model.Tokenizer;
import model.UnexpectedToken;


public class TestImportStatement {
    ImportStatement oneLevel;
    ClassPath oneLevelClassPath;
    ImportStatement multiLevel;
    ClassPath multiLevelClassPath;

    @BeforeEach
    public void beforeEach() {
        ArrayList<String> path = new ArrayList<>();
        path.add("myPackage");
        oneLevelClassPath = new ClassPath(path, "MyClass");
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
    public void tryBuildingNormalTest() throws NoMoreTokens, UnexpectedToken {
        TokenTree singleTree = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import myPackage.MyClass;"
        ));
        List<TokenTree> singleSlice = singleTree.getTrees();
        ImportStatement singleBuilt = ImportStatement.tryBuilding(singleSlice);
        assertEquals(oneLevel.getClassPath().getClassName(), singleBuilt.getClassPath().getClassName());
        assertEquals(oneLevel.getClassPath().getPackagePath(), singleBuilt.getClassPath().getPackagePath());
        assertEquals(0, singleSlice.size());

        TokenTree multiTree = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import d1.d2.DeepClass;"
        ));
        List<TokenTree> multiSlice = multiTree.getTrees();
        ImportStatement multiBuilt = ImportStatement.tryBuilding(multiSlice);
        assertEquals(multiLevel.getClassPath().getClassName(), multiBuilt.getClassPath().getClassName());
        assertEquals(multiLevel.getClassPath().getPackagePath(), multiBuilt.getClassPath().getPackagePath());
        assertEquals(0, multiSlice.size());
    }

    @Test
    public void tryBuildingWrongTest() {
        assertThrows(
            NoMoreTokens.class, 
            () -> {ImportStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
        "import x.y.z"
            )).getTrees());}
        );

        assertThrows(
            NoMoreTokens.class, 
            () -> {ImportStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
        "import A"
            )).getTrees());}
        );

        assertThrows(
            UnexpectedToken.class, 
            () -> {ImportStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
        "package x.y.z;"
            )).getTrees());}
        );

        assertThrows(
            UnexpectedToken.class, 
            () -> {ImportStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
        "import x,y,z;"
            )).getTrees());}
        );

        assertThrows(
            UnexpectedToken.class, 
            () -> {ImportStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
        "import {} x.y.z;"
            )).getTrees());}
        );

        assertThrows(
            UnexpectedToken.class, 
            () -> {ImportStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
        "import bleh x.y.z;"
            )).getTrees());}
        );
    }

    @Test
    public void tryBuildingExtraTest() throws UnexpectedToken, NoMoreTokens {
        TokenTree singleTree = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import myPackage.MyClass;;;;;"
        ));
        List<TokenTree> singleSlice = singleTree.getTrees();
        ImportStatement singleBuilt = ImportStatement.tryBuilding(singleSlice);
        assertEquals(oneLevel.getClassPath().getClassName(), singleBuilt.getClassPath().getClassName());
        assertEquals(oneLevel.getClassPath().getPackagePath(), singleBuilt.getClassPath().getPackagePath());
        assertEquals(4, singleSlice.size());

        TokenTree multiTree = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import d1.d2.DeepClass; class Bob"
        ));
        List<TokenTree> multiSlice = multiTree.getTrees();
        ImportStatement multiBuilt = ImportStatement.tryBuilding(multiSlice);
        assertEquals(multiLevel.getClassPath().getClassName(), multiBuilt.getClassPath().getClassName());
        assertEquals(multiLevel.getClassPath().getPackagePath(), multiBuilt.getClassPath().getPackagePath());
        assertEquals(2, multiSlice.size());
    }
}
