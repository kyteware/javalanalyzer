package model;

import java.util.List;
import java.util.regex.Pattern;

// various utilities for parsing java code
public class Utils {
    // EFFECTS: true if the token is a valid java variable name or keyword
    public static boolean isWord(String token) {
        return Pattern.compile("([A-Z]|[a-z]|_)([A-Z]|[a-z]|[0-9]|_)*").matcher(token).matches();
    }

    // EFFECTS: attempt to take a token (leaf) from a list of tokentrees
    public static String takeToken(List<TokenTree> tts, Integer current) {
        return null; //stub
    }

    // EFFECTS: attempt to take a subtree (branch) from a list of tokentrees
    public static TokenTree takeTree(List<TokenTree> tts, Integer current) {
        return null; //stub
    }
}
