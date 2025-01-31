package model;

import java.util.List;

// a tree of java language tokens
public class TokenTree {
    // fields

    // EFFECTS: creates a token tree from some content and children
    public TokenTree(List<String> content, List<TokenTree> children) {
        // stub
    }

    public static List<TokenTree> genTrees(List<String> tokens) {
        // stub
        return null;
    }

    // EFFECTS: returns class path if tree is import statement, else null
    public ClassPath tryAsImportStatement() {
        // stub
        return null;
    }

    // EFFECTS: get the content that builds the tree (if statement, semicolon excluded)
    public List<String> getContent() {
        // stub
        return null;
    }

    // EFFECTS: get the child trees of the tree
    public List<TokenTree> getChildren() {
        // stub
        return null;
    }
}
