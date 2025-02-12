package model.feature;

import java.util.ArrayList;
import java.util.List;

import model.ClassPath;
import model.CodeException;
import model.NoMoreTokens;
import model.TokenTree;
import model.UnexpectedToken;
import model.Utils;

// representation of a java package statement
public class PackageStatement {
    private ClassPath path;

    // EFFECTS: build an package statement from a classpath
    public PackageStatement(ClassPath path) {
        this.path = path;
    }
    
    // MODIFIES: trees
    // EFFECTS: attempt to build the feature from a slice of token trees
    public static PackageStatement tryBuilding(List<TokenTree> trees) throws CodeException {
        List<TokenTree> eaten = new ArrayList<>(trees);
        if (!Utils.takeToken(eaten).equals("package")) {
            throw new UnexpectedToken();
        }

        List<String> levels = new ArrayList<>();

        while (true) {
            String name = Utils.takeToken(eaten);
            if (!Utils.isWord(name)) {
                throw new UnexpectedToken();
            }
            String div = Utils.takeToken(eaten);
            
            levels.add(name);
            if (div.equals(";")) {
                break;
            }
            if (!div.equals(".")) {
                throw new UnexpectedToken();
            }
        }
        
        trees.clear();
        trees.addAll(eaten);
        return new PackageStatement(new ClassPath(levels, null));
    }

    public ClassPath getPath() {
        return path;
    }
}
