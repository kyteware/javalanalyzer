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


public class TestPackageStatement {
    PackageStatement oneLevel;
    ClassPath oneLevelPath;
    PackageStatement multiLevel;
    ClassPath multiLevelPath;

    @BeforeEach
    public void beforeEach() {
        ArrayList<String> path = new ArrayList<>();
        path.add("myPackage");
        oneLevelPath = new ClassPath(path, null);
        oneLevel = new PackageStatement(oneLevelPath);
        ArrayList<String> multiPath = new ArrayList<>();
        multiPath.add("d1");
        multiPath.add("d2");
        multiLevelPath = new ClassPath(multiPath, null);
        multiLevel = new PackageStatement(multiLevelPath);
    }

    @Test
    public void constructorTest() {
        assertEquals(oneLevelPath, oneLevel.getPath());
        assertEquals(multiLevelPath, multiLevel.getPath());
    }

    @Test
    public void tryBuildingNormalTest() throws NoMoreTokens, UnexpectedToken {
        TokenTree singleTree = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "package myPackage;"
        ));
        List<TokenTree> singleSlice = singleTree.getTrees();
        PackageStatement singleBuilt = PackageStatement.tryBuilding(singleSlice);
        assertEquals(null, singleBuilt.getPath().getClassName());
        assertEquals(oneLevel.getPath().getPackagePath(), singleBuilt.getPath().getPackagePath());
        assertEquals(0, singleSlice.size());

        TokenTree multiTree = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "package d1.d2;"
        ));
        List<TokenTree> multiSlice = multiTree.getTrees();
        PackageStatement multiBuilt = PackageStatement.tryBuilding(multiSlice);
        assertEquals(multiLevel.getPath().getPackagePath(), multiBuilt.getPath().getPackagePath());
        assertEquals(0, multiSlice.size());
    }

    @Test
    public void tryBuildingWrongTest() {
        assertThrows(
            NoMoreTokens.class, 
            () -> {PackageStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
        "package x.y.z"
            )).getTrees());}
        );

        assertThrows(
            NoMoreTokens.class, 
            () -> {PackageStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
        "package A"
            )).getTrees());}
        );

        assertThrows(
            UnexpectedToken.class, 
            () -> {PackageStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
        "import x.y.z;"
            )).getTrees());}
        );

        assertThrows(
            UnexpectedToken.class, 
            () -> {PackageStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
        "package x,y,z;"
            )).getTrees());}
        );

        assertThrows(
            UnexpectedToken.class, 
            () -> {PackageStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
        "package {} x.y.z;"
            )).getTrees());}
        );

        assertThrows(
            UnexpectedToken.class, 
            () -> {PackageStatement.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
        "package bleh x.y.z;"
            )).getTrees());}
        );
    }

    @Test
    public void tryBuildingExtraTest() throws UnexpectedToken, NoMoreTokens {
        TokenTree singleTree = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "package myPackage;;;;;"
        ));
        List<TokenTree> singleSlice = singleTree.getTrees();
        PackageStatement singleBuilt = PackageStatement.tryBuilding(singleSlice);
        assertEquals(oneLevel.getPath().getPackagePath(), singleBuilt.getPath().getPackagePath());
        assertEquals(4, singleSlice.size());

        TokenTree multiTree = TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "package d1.d2; class Bob"
        ));
        List<TokenTree> multiSlice = multiTree.getTrees();
        PackageStatement multiBuilt = PackageStatement.tryBuilding(multiSlice);
        assertEquals(multiLevel.getPath().getPackagePath(), multiBuilt.getPath().getPackagePath());
        assertEquals(2, multiSlice.size());
    }
}
