package stringcalculatorkatalyst;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SingleBracketAndMultipleCharsSeparatorPatternFactory extends BaseCharSeparatorPatternFactorySupport implements CustomSeparatorPatternFactory{

    public Pattern createPattern(String customSeparatorString) {
        List<String> separators = new ArrayList<>();
        String customSeparator = customSeparatorString.substring(1, customSeparatorString.length() - 1);
        separators.add(customSeparator);
        String regexString = createRegex(separators);
        return  Pattern.compile(regexString);
    }
}
