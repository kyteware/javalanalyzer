package model.feature;

import java.util.ArrayList;
import java.util.List;

import model.ClassPath;
import model.CodeException;
import model.TokenTree;
import model.UnexpectedToken;
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
            String name = Utils.takeToken(eaten);
            if (!Utils.isWord(name)) {
                throw new UnexpectedToken();
            }
            String div = Utils.takeToken(eaten);
            
            if (div.equals(".")) {
                packages.add(name);
            } else if (div.equals(";")) {
                className = name;
                break;
            } else {
                throw new UnexpectedToken();
            }
        }

        if (packages.size() == 0) {
            throw new UnexpectedToken();
        }

        trees.clear();
        trees.addAll(eaten);
        return new ImportStatement(new ClassPath(packages, className));
    }

    public ClassPath getClassPath() {
        return classPath;
    }
}
