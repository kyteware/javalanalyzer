package model;

import java.util.List;

// a tree of java language tokens, can either be a branch with some children or a leaf with some tokens
public class TokenTree {
    // fields

    // EFFECTS: creates a token tree from some content and children
    public TokenTree(List<String> content, List<TokenTree> children) {
        // stub
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

    // EFFECT: true if the tree is surrounded by curly braces
    // REQUIRES: must be a branch node
    public boolean isCurly() {
        return false;
    }

    // EFFECT: true if the tree is surrounded by square braces
    // REQUIRES: must be a branch node
    public boolean isSquare() {
        return false;
    }

    // EFFECT: true if the tree is surrounded by round paranthesis
    // REQUIRES: must be a branch node
    public boolean isRound() {
        return false;
    }

    // EFFECT: true if the tree isn't surround by anything because it is the tree of a java file
    // REQUIRES: must be a branch node
    public boolean isRoot() {
        return false;
    }

    // EFFECT: 
    // REQUIRES: must be a branch node
    public boolean isCommaSeperated() {
        return false;
    }

    // EFFECT:
    // REQUIRES: must be a branch node
    public boolean isSemicolonSeperated() {
        return false;
    }
}
