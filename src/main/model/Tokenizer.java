package model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private static String stringMatch = "\"(\\\\\"|.)*?\""; // doesn't work with delimited double quotes
    private static String wordMatch = "([A-Z]|[a-z]|_)([A-Z]|[a-z]|[0-9]|_)*";
    private static String numberMatch = "[0-9]+\\.?[0-9]*";
    private static String symbolMatch = "\\!|\\@|\\%|\\^|\\&|\\*|\\(|\\)|\\||\\-|\\+|\\[|\\]|\\{|\\}|\\;\\|\\:|\\,|\\.|\\<|\\>|\\/";
    private static String[] allMatches = { stringMatch, wordMatch, numberMatch, symbolMatch };
    private static String tokenMatch = String.join("|", allMatches);
    private static Pattern pattern = Pattern.compile(tokenMatch);

    // EFFECTS: splits the content of a java file into tokens
    public static List<String> tokenize(String rawJava) {
        Matcher matcher = pattern.matcher(rawJava);
        ArrayList<String> parts = new ArrayList<String>();
        while (matcher.find()) {
            parts.add(matcher.group());
        }
        return parts;
    }
}
