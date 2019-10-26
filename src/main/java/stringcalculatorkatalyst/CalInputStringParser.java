package stringcalculatorkatalyst;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

class CalInputStringParser {

    private Pattern pattern;
    private HashSet<Character> escapeCharacters;


    public CalInputStringParser() {
        pattern = Pattern.compile("[,\n]");
        escapeCharacters = new HashSet<>(asList('\\','^','*', '$','.','|','?','+','(',')','[',']','{','}'));
    }

    public int[] parse(String stringNumber) throws IllegalArgumentException{

        String val = stringNumber.trim();

        Stream<String> stringNumberStream;

        Pattern patternToUsed;

        String stringNumberToParse = val;
        if(val.startsWith("//")) {
            int startIndexOfNewLineChar = val.indexOf("\n");
            stringNumberToParse = val.substring(startIndexOfNewLineChar + 1);
            patternToUsed = createPattern(val.substring(2, startIndexOfNewLineChar));
        } else {
            patternToUsed = pattern;
        }

        stringNumberStream = patternToUsed.splitAsStream(stringNumberToParse);

        return stringNumberStream.map(String::trim).mapToInt(Integer::parseInt).toArray();
    }


    private Pattern createPattern(String customSeparatorString) {

        Pattern pattern1 = Pattern.compile("^(\\[.\\])+");

        List<String> separators = new ArrayList<>();

        if(pattern1.matcher(customSeparatorString).matches()) {
            extractSeparator(customSeparatorString, separators);
        } else if(customSeparatorString.startsWith("[") && Pattern.compile("^\\[[^\\]]+\\]").matcher(customSeparatorString).matches()) {
            String customSeparator = customSeparatorString.substring(1, customSeparatorString.length() - 1);
            separators.add(customSeparator);
        } else if(Pattern.compile("^.").matcher(customSeparatorString).matches()) {
            separators.add(customSeparatorString);
        } else {
            throw new IllegalArgumentException("Invalid expression provided after //");
        }

        String regexString = createRegex(separators);
        return  Pattern.compile(regexString);

    }

    private void extractSeparator(String customSeparatorString, List<String> separators) {
        for(int index = 0; index < customSeparatorString.length(); index+=3) {
            if(customSeparatorString.charAt(index) == '[' && customSeparatorString.charAt(index+2) == ']') {
                char separator = customSeparatorString.charAt(index + 1);
                separators.add(""+separator);
            }
        }
    }

    private String createRegex(List<String> separators) {
        StringBuilder newCustomSeparator = new StringBuilder();
        for (String separator : separators) {
            char customSeparatorChar = separator.charAt(0);

            newCustomSeparator.append('|');
            if(escapeCharacters.contains(customSeparatorChar)) {

                for(int index = 0; index < separator.length(); index++) {
                    newCustomSeparator.append("\\").append(customSeparatorChar);
                }
            } else {
                newCustomSeparator.append(customSeparatorChar);
            }
        }

        return ",|\n" + newCustomSeparator;
    }

}
