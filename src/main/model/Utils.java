package model;

import java.util.List;
import java.util.regex.Pattern;

// various utilities for parsing java code
public class Utils {
    private Utils() {
        
    }

    // EFFECTS: true if the token is a valid java variable name or keyword
    public static boolean isWord(String token) {
        return Pattern.compile("([A-Z]|[a-z]|_)([A-Z]|[a-z]|[0-9]|_)*").matcher(token).matches();
    }

    // EFFECTS: attempt to take a token (leaf) from a list of tokentrees
    public static String takeToken(List<TokenTree> tts) throws CodeException {
        if (tts.size() == 0) {
            throw new NoMoreTokens();
        } else if (tts.get(0).isBranch()) {
            throw new UnexpectedToken();
        } else {
            TokenTree taken = tts.remove(0);
            return taken.getToken();
        }
    }

    // EFFECTS: attempt to take a subtree (branch) from a list of tokentrees
    public static TokenTree takeTree(List<TokenTree> tts) throws CodeException  {
        if (tts.size() == 0) {
            throw new NoMoreTokens();
        } else if (tts.get(0).isLeaf()) {
            throw new UnexpectedToken();
        } else {
            TokenTree taken = tts.remove(0);
            return taken;
        }
    }
}
