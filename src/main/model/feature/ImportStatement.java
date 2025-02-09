package model.feature;

import java.util.List;

import model.ClassPath;
import model.TokenTree;

// representation of a java import statement
public class ImportStatement {
    // EFFECTS: build an import statement from a classpath
    public ImportStatement(ClassPath classpath) {
        // stub
    }
    
    // MODIFIES: trees
    // EFFECTS: attempt to build the feature from a slice of token trees, return null on failure
    public static ImportStatement tryBuilding(List<TokenTree> trees) {
        return null;
    }

    public ClassPath getClassPath() {
        return null;
    }
}
