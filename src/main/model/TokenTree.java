package model;

import java.util.ArrayList;
import java.util.List;

// a tree of java language tokens, can either be a branch with some children or a leaf with some tokens
public class TokenTree {
    public static int DELIMITED_CURLY = 0;
    public static int DELIMITED_SQUARY = 1;
    public static int DELIMITED_ROUND = 2;
    public static int DELIMITED_ROOT = 3;
    
    private boolean isLeaf;
    private String token;
    private List<TokenTree> trees;
    private int delimiters;

    // EFFECTS: creates a leaf token tree
    public TokenTree(String token) {
        this.isLeaf = true;
        this.token = token;
        this.trees = null;
        this.delimiters = -1;
    }

    // EFFECTS: create a branch token tree
    public TokenTree(List<TokenTree> children, int delimiters, int seperators) {
        this.isLeaf = false;
        this.token = null;
        this.trees = children;
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

    // EFFECT: true if the tree is a leaf node with a single language token
    public boolean isLeaf() {
        return isLeaf;
    }

    // EFFECT: true if the tree is a branch node with some children
    public boolean isBranch() {
        return !isLeaf;
    }

    // REQUIRES: must be a leaf node
    // EFFECT: returns the token of the leaf node
    public String getToken() {
        return token;
    }

    // REQUIRES: must be a branch node
    // EFFECT: returns all the children of the tree
    public List<TokenTree> getTrees() {
        return trees;
    }

    // REQUIRES: must be a branch node
    // EFFECT: gets the kind of delimiters that are wrapped around the branch node
    public int getDelimiters() {
        return delimiters;
    }
}
