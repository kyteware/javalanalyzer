package model;

import java.util.regex.Pattern;

public class Utils {
    public static boolean isWord(String token) {
        return Pattern.compile("([A-Z]|[a-z]|_)([A-Z]|[a-z]|[0-9]|_)*").matcher(token).matches();
    }
}
