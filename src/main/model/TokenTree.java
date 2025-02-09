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
        this.delimiters = DELIMITED_ROOT;
    }

    // EFFECTS: create a branch token tree
    public TokenTree(List<TokenTree> children) {
        this.isLeaf = false;
        this.token = null;
        this.trees = children;
        this.delimiters = DELIMITED_ROOT;
    }

    // REQUIRES: open/close parens and brackets line up properly
    // EFFECTS: parse a collection of flat java tokens into a token tree
    public static TokenTree parseJavaTokens(List<String> tokens) {
        List<TokenTree> ttsSoFar = new ArrayList<>();
        List<String> stack = new ArrayList<>();
        int branchStart = -1;

        for (int i=0; i<tokens.size(); i++) {
            String token = tokens.get(i);
            if (token.equals("{")
                    | token.equals("[") 
                    | token.equals("(")) {
                if (stack.size() == 0) {
                    branchStart = i + 1;
                }
                stack.add(token);
            } else if (token.equals("}")
                    | token.equals("]")
                    | token.equals(")")) { 
                if (stack.size() == 1) {
                    int branchEnd = i;
                    TokenTree inner = parseJavaTokens(tokens.subList(branchStart, branchEnd));
                    if (token.equals("}")) {
                        inner.delimiters = DELIMITED_CURLY;
                    } else if (token.equals("]")) {
                        inner.delimiters = DELIMITED_SQUARY;
                    } else {
                        inner.delimiters = DELIMITED_ROUND;
                    }
                    ttsSoFar.add(inner);
                }
                stack.remove(stack.size() - 1);
            } else if (stack.size() == 0) {
                ttsSoFar.add(new TokenTree(token));
            }
        }

        TokenTree tree = new TokenTree(ttsSoFar);
        return tree;
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
