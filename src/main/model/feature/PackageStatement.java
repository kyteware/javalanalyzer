package model.feature;

import java.util.ArrayList;
import java.util.List;

import model.ClassPath;
import model.NoMoreTokens;
import model.TokenTree;
import model.UnexpectedToken;
import model.Utils;

// representation of a java package statement
public class PackageStatement {

    // EFFECTS: build an package statement from a classpath
    public PackageStatement(ClassPath path) {
        //stub
    }
    
    // MODIFIES: trees
    // EFFECTS: attempt to build the feature from a slice of token trees
    public static ImportStatement tryBuilding(List<TokenTree> trees) throws NoMoreTokens, UnexpectedToken {
        return null; //stub
    }

    public ClassPath getPath() {
        return null; //sutb
    }
}
