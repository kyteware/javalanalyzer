package model;

import java.util.List;

// a tree of java language tokens
public class TokenTree {
    // fields

    // REQUIRES: the tokens represent valid java code
    // EFFECTS: generates a tree from a flat list of tokens
    public TokenTree(List<String> tokens) {
        // stub
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
