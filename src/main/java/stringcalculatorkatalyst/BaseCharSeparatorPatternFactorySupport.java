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
