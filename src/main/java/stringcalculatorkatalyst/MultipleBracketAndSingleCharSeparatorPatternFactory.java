package stringcalculatorkatalyst;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MultipleBracketAndSingleCharSeparatorPatternFactory extends BaseCharSeparatorPatternFactorySupport implements CustomSeparatorPatternFactory {

    @Override
    public Pattern createPattern(String customSeparatorString) {
        List<String> separators = extractSeparator(customSeparatorString);
        String regexString = createRegex(separators);
        return Pattern.compile(regexString);

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

}
