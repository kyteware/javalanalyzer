package model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// utility class for tokenizing java code
public class Tokenizer {
    private static String stringMatch = "\"(\\\\\"|.)*?\"";
    private static String charMatch = "'.'";
    private static String wordMatch = "([A-Z]|[a-z]|_)([A-Z]|[a-z]|[0-9]|_)*";
    private static String numberMatch = "[0-9]+\\.?[0-9]*";
    private static String symbolMatch 
            = "\\!|\\@|\\%|\\^|\\&|\\*|\\(|\\)|\\||\\-|\\+|\\[|\\]|\\{|\\}|\\;|\\:|\\,|\\.|\\<|\\>|\\/";
    private static String[] allMatches = { stringMatch, charMatch, wordMatch, numberMatch, symbolMatch };
    private static String tokenMatch = String.join("|", allMatches);
    private static Pattern pattern = Pattern.compile(tokenMatch);

    // empty contructor to ignore code coverage for class init
    private Tokenizer() {
        
    }

    // EFFECTS: splits the content of a java file into tokens
    public static List<String> tokenize(String rawJava) {
        Matcher matcher = pattern.matcher(rawJava);
        ArrayList<String> parts = new ArrayList<String>();
        while (matcher.find()) {
            parts.add(matcher.group());
        }
        EventLog.getInstance().logEvent(new Event(
            "Java code tokenized (" + parts.size() + " tokens found)"
        ));
        return parts;
    }
}
