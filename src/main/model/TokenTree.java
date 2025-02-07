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
    
    private boolean isLeaf;
    private List<String> tokens;
    private List<TokenTree> trees;
    private int delimiters;
    private int seperators;

    // EFFECTS: creates a leaf token tree
    public TokenTree(List<String> tokens) {
        this.isLeaf = true;
        this.tokens = tokens;
        this.trees = null;
        this.delimiters = -1;
        this.seperators = -1;
    }

    // EFFECTS: create a branch token tree
    public TokenTree(List<TokenTree> children, int delimiters, int seperators) {
        this.isLeaf = false;
        this.tokens = null;
        this.trees = children;
        this.seperators = seperators;
        this.delimiters = delimiters;
    }

    // EFFECTS: parse a collection of java tokens into a token tree
    public static TokenTree parseFlatTokens(List<String> tokens) {
        return parseFlatTokensRecursive(tokens, 0, tokens.size() - 1, DELIMITED_ROOT);
    }

    // EFFECTS: parse the tokens of a java file into a token tree
    public static TokenTree parseJavaFileTokens(List<String> tokens) {
        TokenTree tree = parseFlatTokens(tokens);
        tree.delimiters = DELIMITED_ROOT;
        return tree;
    }

    private static TokenTree parseFlatTokensRecursive(List<String> tokens, int next, int last, int delimiters) {
        return null;
    }

    // EFFECT: true if the tree is a leaf node with some flat language tokens
    public boolean isLeaf() {
        return isLeaf;
    }

    // EFFECT: true if the tree is a branch node with some children
    public boolean isBranch() {
        return !isLeaf;
    }

    // EFFECT: returns all the java language tokens inside the node
    // REQUIRES: must be a leaf node
    public List<String> getTokens() {
        return tokens;
    }

    // EFFECT: returns all the children of the tree
    // REQUIRES: must be a branch node
    public List<TokenTree> getTrees() {
        return trees;
    }

    // EFFECT: gets the kind of delimiters that are wrapped around the branch node
    // REQUIRES: must be a branch node
    public int getDelimiters() {
        return delimiters;
    }

    // EFFECT: gets the kind of seperators between the children of the 
    // REQUIRES: must be a branch node
    public int getSeperators() {
        return seperators;
    }
}
