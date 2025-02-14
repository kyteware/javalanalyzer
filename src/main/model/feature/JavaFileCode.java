package model.feature;

import java.util.ArrayList;
import java.util.List;

import model.ClassPath;
import model.CodeException;
import model.NoMoreTokens;
import model.TokenTree;
import model.TooManyPackageDecls;
import model.UnexpectedToken;
import model.UnsupportedWildcardImport;

// the code of an entire java file
public class JavaFileCode {
    List<ClassPath> imports;
    ClassPath pkg;

    // EFFECTS: initialize the code of an empty java file
    public JavaFileCode() {
        imports = new ArrayList<>();
        pkg = null;
    }

    // EFFECTS: attempts to parse a list of token trees into a java file
    public static JavaFileCode tryBuilding(List<TokenTree> trees) throws CodeException {
        JavaFileCode code = new JavaFileCode();
        List<TokenTree> eaten = new ArrayList<>(trees);

        while (eaten.size() > 0) {
            try {
                ImportStatement imported = ImportStatement.tryBuilding(eaten);
                code.imports.add(imported.getClassPath());
                continue;
            } catch (NoMoreTokens | UnexpectedToken | UnsupportedWildcardImport e) {
                // it wasn't an import statement or it had a wildcard import
            }

            try {
                PackageStatement pkg = PackageStatement.tryBuilding(eaten);
                if (code.pkg == null) {
                    code.pkg = pkg.getPath();
                } else {
                    throw new TooManyPackageDecls();
                }
                continue;
            } catch (NoMoreTokens | UnexpectedToken e) {
                // it wasn't a package statement
            }

            eaten.remove(0);
        }

        return code;
    }

    // EFFECTS: gets all the classpaths that the java file imports
    public List<ClassPath> getImports() {
        return imports;
    }

    // EFFECTS: get the package path the file declares, or null if it doesn't declare one
    public ClassPath getPackage() {
        return pkg;
    }
}
