package stringcalculatorkatalyst;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SingleCharSeparatorFactory extends BaseCharSeparatorSupport implements CustomSeparatorPatternFactory {
    public Pattern createPattern(String customSeparatorString) {

        List<String> separators = new ArrayList<>();
        separators.add(customSeparatorString);

        String regexString = createRegex(separators);
        return Pattern.compile(regexString);
    }
}
