package model;

import java.util.List;

// a tree of java language tokens, can either be a branch with some children or a leaf with some tokens
public class TokenTree {
    public static int DELIMITED_CURLY = 0;
    public static int DELIMITED_SQUARY = 1;
    public static int DELIMITED_ROUND = 2;
    public static int DELIMITED_ROOT = 3;
    public static int SEPERATED_NA = 0;
    public static int SEPERATED_SEMICOLON = 1;
    public static int SEPERATED_COMMA = 2;
    
    // fields

    // EFFECTS: creates a leaf token tree
    public TokenTree(List<String> tokens) {
        // stub
    }

    // EFFECTS: create a branch token tree
    public TokenTree(List<TokenTree> children, int delimiters, int seperators) {
        // stub
    }

    // EFFECTS: parse a collection of java tokens into a token tree
    public static TokenTree parseFlatTokens(List<String> tokens) {
        return null;
    }

    public static TokenTree parseJavaFileTokens(List<String> tokens) {
        return null;
    }

    private static TokenTree parseFlatTokensRecursive(List<String> tokens, int next, int last, int delimiters) {
        return null;
    }

    // EFFECT: true if the tree is a leaf node with some flat language tokens
    public boolean isLeaf() {
        return false;
    }

    // EFFECT: true if the tree is a branch node with some children
    public boolean isBranch() {
        return false;
    }

    // EFFECT: returns all the java language tokens inside the node
    // REQUIRES: must be a leaf node
    public List<String> getTokens() {
        return null;
    }

    // EFFECT: returns all the children of the tree
    // REQUIRES: must be a branch node
    public List<TokenTree> getTrees() {
        return null;
    }

    // EFFECT: gets the kind of delimitors that are wrapped around the branch node
    // REQUIRES: must be a branch node
    public int getDelimiters() {
        return DELIMITED_ROOT;
    }

    // EFFECT: gets the kind of seperators between the children of the 
    // REQUIRES: must be a branch node
    public int getSeperators() {
        return SEPERATED_NA;
    }
}
