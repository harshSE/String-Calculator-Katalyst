package stringcalculatorkatalyst;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MultipleBracketAndSingleCharSeparatorPatternFactory extends BaseCharSeparatorPatternFactorySupport implements CustomSeparatorPatternFactory {

    public Pattern createPattern(String customSeparatorString) {
        List<String> separators = extractSeparator(customSeparatorString);
        String regexString = createRegex(separators);
        return Pattern.compile(regexString);

    }

    private List<String> extractSeparator(String customSeparatorString) {
        List<String> separators = new ArrayList<>();
        for(int index = 0; index < customSeparatorString.length(); index+=3) {
            if(customSeparatorString.charAt(index) == '[' && customSeparatorString.charAt(index+2) == ']') {
                char separator = customSeparatorString.charAt(index + 1);
                separators.add(""+separator);
            }
        }

        return separators;
    }
}
