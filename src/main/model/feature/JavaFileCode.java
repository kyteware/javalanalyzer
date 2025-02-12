package model.feature;

import java.util.List;

import model.ClassPath;
import model.NoMoreTokens;
import model.TokenTree;
import model.UnexpectedToken;

// the code of an entire java file
public class JavaFileCode {
    // EFFECTS: initialize the code of an empty java file
    public JavaFileCode() {
        // stub
    }

    // EFFECTS: attempts to parse a list of token trees into a java file
    public static JavaFileCode tryBuilding(List<TokenTree> trees) throws UnexpectedToken, NoMoreTokens {
        return null; // stub
    }

    // EFFECTS: gets all the classpaths that the java file imports
    public List<ClassPath> getImports() {
        return null; //stub
    }

    // EFFECTS: get the package path the file declares, or null if it doesn't declare one
    public List<ClassPath> getPackage() {
        return null;
    }
}
