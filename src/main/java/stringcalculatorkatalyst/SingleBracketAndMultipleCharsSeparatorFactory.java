package stringcalculatorkatalyst;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public class SingleBracketAndMultipleCharsSeparatorFactory implements CustomSeparatorPatternFactory{

    private HashSet<Character> escapeCharacters;


    public SingleBracketAndMultipleCharsSeparatorFactory() {
        escapeCharacters = new HashSet<>(asList('\\','^','*', '$','.','|','?','+','(',')','[',']','{','}'));
    }

    public Pattern createPattern(String customSeparatorString) {
        List<String> separators = new ArrayList<>();
        String customSeparator = customSeparatorString.substring(1, customSeparatorString.length() - 1);
        separators.add(customSeparator);
        String regexString = createRegex(separators);
        return  Pattern.compile(regexString);
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
