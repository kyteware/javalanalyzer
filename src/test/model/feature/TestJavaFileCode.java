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
import model.TooManyPackageDecls;
import model.UnexpectedToken;

public class TestJavaFileCode {
    @Test
    public void emptyCodeTest() {
        JavaFileCode emptyCode = new JavaFileCode();
        assertNull(emptyCode.getPackage());
        assertEquals(new ArrayList<>(), emptyCode.getImports());
    }

    @Test
    public void oneImportCodeTest() throws UnexpectedToken, NoMoreTokens {
        JavaFileCode oneImportCode = JavaFileCode.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import bob.Van;"
        )).getTrees());

        List<String> path = new ArrayList<>();
        path.add("bob");
        assert cpEquals(new ClassPath(path, "Van"), oneImportCode.getImports().get(0));
        assertEquals(1, oneImportCode.getImports().size());
        assertNull(oneImportCode.getPackage());
    }

    @Test
    public void manyImportsCodeTest() throws UnexpectedToken, NoMoreTokens {
        JavaFileCode manyImportsCode = JavaFileCode.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "import bob.Van;\nimport fred.joe.Car;"
        )).getTrees());

        List<String> path1 = new ArrayList<>();
        path1.add("bob");
        assert cpEquals(new ClassPath(path1, "Van"), manyImportsCode.getImports().get(0));

        List<String> path2 = new ArrayList<>();
        path2.add("fred");
        path2.add("joe");
        assert cpEquals(new ClassPath(path2, "Car"), manyImportsCode.getImports().get(1));

        assertEquals(2, manyImportsCode.getImports().size());
        assertNull(manyImportsCode.getPackage());
    }

    @Test
    public void onePackageCodeTest() throws UnexpectedToken, NoMoreTokens {
        JavaFileCode onePackageCode = JavaFileCode.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "package my.pack;"
        )).getTrees());

        List<String> path = new ArrayList<>();
        path.add("my");
        path.add("pack");

        assert cpEquals(new ClassPath(path, null), onePackageCode.getPackage());
        assertEquals(0, onePackageCode.getImports().size());
    }

    @Test
    public void fullJavaCodeFileTest() throws UnexpectedToken, NoMoreTokens {
        JavaFileCode javaFileCode = JavaFileCode.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
            "package my.pack;\nclass MyClass {DOESN'T MATTER WHAT HAPPENS HERE YET :}\n import my.Thing;"
        )).getTrees());

        List<String> path1 = new ArrayList<>();
        path1.add("my");
        path1.add("patk");
        assert cpEquals(new ClassPath(path1, null), javaFileCode.getPackage());

        List<String> path2 = new ArrayList<>();
        path2.add("my");
        assert cpEquals(new ClassPath(path2, "Thing"), javaFileCode.getImports().get(0));

        assertEquals(1, javaFileCode.getImports().size());
    }

    @Test
    public void tooManyPackagesCodeTest() {
        assertThrows(
            TooManyPackageDecls.class,
            () -> {
                JavaFileCode.tryBuilding(TokenTree.parseJavaTokens(Tokenizer.tokenize(
                    "package my.pack; package another.pack;"
                )).getTrees());
            }
        );
    }

    private static boolean cpEquals(ClassPath cp1, ClassPath cp2) {
        return cp1.getClassName().equals(cp2.getClassName()) & cp1.getPackagePath().equals(cp2.getPackagePath());
    }
}
