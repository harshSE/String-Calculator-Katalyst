package stringcalculatorkatalyst;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

class CalInputStringParser {

    private Pattern pattern;
    private CustomSeparatorPatternFactoryProvider provider;
    private HashSet<Character> escapeCharacters;

    public CalInputStringParser(CustomSeparatorPatternFactoryProvider provider) {
        pattern = Pattern.compile("[,\n]");
        this.provider = provider;
        this.escapeCharacters = new HashSet<>(asList('\\','^','*', '$','.','|','?','+','(',')','[',']','{','}'));
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

        CustomSeparatorPatternFactory customSeparatorPatternFactory = provider.get(customSeparatorString);
        if(Objects.isNull(customSeparatorPatternFactory)) {
            throw new IllegalArgumentException("Invalid expression provided after //");
        }

        return customSeparatorPatternFactory.createPattern(customSeparatorString);

    }

    private List<String> extractSeparator(String customSeparatorString) {
        List<String> separators = new ArrayList<>();
        for(int index = 0; index < customSeparatorString.length(); index++) {
            if(customSeparatorString.charAt(index) == '[') {
                String separator = fetchUntilClosingBracketNotFound(customSeparatorString, index+1);
                index += separator.length() + 1;
                separators.add(separator);
            }
        }

        return separators;
    }

    private String fetchUntilClosingBracketNotFound(String customSeparatorString, int index) {
        StringBuilder separator = new StringBuilder();
        while (customSeparatorString.charAt(index) != ']') {
            separator.append(customSeparatorString.charAt(index++));
        }
        return separator.toString();
    }


    protected String createRegex(List<String> separators) {
        StringBuilder newCustomSeparator = new StringBuilder();
        for (String separator : separators) {

            newCustomSeparator.append('|');
            for (int index = 0; index < separator.length(); index++) {
                char customSeparatorChar = separator.charAt(index);

                if(escapeCharacters.contains(customSeparatorChar)) {
                    newCustomSeparator.append("\\").append(customSeparatorChar);
                } else {
                    newCustomSeparator.append(customSeparatorChar);
                }
            }

        }

        return ",|\n" + newCustomSeparator;
    }

}
