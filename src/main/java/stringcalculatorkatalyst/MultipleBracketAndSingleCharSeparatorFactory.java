package stringcalculatorkatalyst;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public class MultipleBracketAndSingleCharSeparatorFactory implements CustomSeparatorPatternFactory {

    private HashSet<Character> escapeCharacters;


    public MultipleBracketAndSingleCharSeparatorFactory() {
        escapeCharacters = new HashSet<>(asList('\\','^','*', '$','.','|','?','+','(',')','[',']','{','}'));
    }

    public Pattern createPattern(String customSeparatorString) {

        List<String> separators = new ArrayList<>();


        extractSeparator(customSeparatorString, separators);


        String regexString = createRegex(separators);
        return Pattern.compile(regexString);

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
