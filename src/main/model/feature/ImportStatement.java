package model.feature;

import java.util.ArrayList;
import java.util.List;

import model.ClassPath;
import model.CodeException;
import model.TokenTree;
import model.UnexpectedToken;
import model.UnsupportedWildcardImport;
import model.Utils;

// representation of a java import statement
public class ImportStatement {
    private ClassPath classPath;

    // EFFECTS: build an import statement from a classpath
    public ImportStatement(ClassPath classPath) {
        this.classPath = classPath;
    }
    
    // MODIFIES: trees
    // EFFECTS: attempt to build the feature from a slice of token trees
    public static ImportStatement tryBuilding(List<TokenTree> trees) throws CodeException {
        List<TokenTree> eaten = new ArrayList<>(trees);
        if (!Utils.takeToken(eaten).equals("import")) {
            throw new UnexpectedToken();
        }

        List<String> packages = new ArrayList<>();
        String className = null;

        while (true) {
            String maybeClassname = parseChunk(eaten, packages);
            if (maybeClassname != null) {
                className = maybeClassname;
                break;
            }
        }

        if (packages.size() == 0) {
            throw new UnexpectedToken();
        }

        trees.clear();
        trees.addAll(eaten);
        return new ImportStatement(new ClassPath(packages, className));
    }

    // EFFECTS: return the classpath being imported
    public ClassPath getClassPath() {
        return classPath;
    }

    // MODIFIES: eaten, packagesSoFar
    // EFFECTS: parse a chunk of an import statement and take it out of eaten
    private static String parseChunk(List<TokenTree> eaten, List<String> packagesSoFar) throws CodeException {
        String name = Utils.takeToken(eaten);
        String div = Utils.takeToken(eaten);

        if (name.equals("*") && div.equals(";")) {
            throw new UnsupportedWildcardImport();
        }

        if (!Utils.isWord(name)) {
            throw new UnexpectedToken();
        }
        
        if (div.equals(".")) {
            packagesSoFar.add(name);
            return null;
        } else if (div.equals(";")) {
            return name;
        } else {
            throw new UnexpectedToken();
        }
    }
}
