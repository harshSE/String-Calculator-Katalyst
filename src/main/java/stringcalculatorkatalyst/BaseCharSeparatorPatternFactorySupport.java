package stringcalculatorkatalyst;

import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;

public abstract class BaseCharSeparatorPatternFactorySupport {

    private HashSet<Character> escapeCharacters;


    public BaseCharSeparatorPatternFactorySupport() {
        escapeCharacters = new HashSet<>(asList('\\','^','*', '$','.','|','?','+','(',')','[',']','{','}'));
    }

    protected String createRegex(List<String> separators) {
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
