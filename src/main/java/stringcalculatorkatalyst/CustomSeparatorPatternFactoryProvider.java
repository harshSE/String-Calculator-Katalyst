package stringcalculatorkatalyst;

import java.util.regex.Pattern;

public class CustomSeparatorPatternFactoryProvider {

    public CustomSeparatorPatternFactory get(String customSeparatorString) {
        Pattern pattern = Pattern.compile("^(\\[.\\])+");

        if(pattern.matcher(customSeparatorString).matches()) {
            return new MultipleCommaAndSingleCharSeparatorFactory();
        } else if(customSeparatorString.startsWith("[") && Pattern.compile("^\\[[^\\]]+\\]").matcher(customSeparatorString).matches()) {
           return new SingleBracketAndMultipleCharsSeparatorFactory();
        } else if(Pattern.compile("^.").matcher(customSeparatorString).matches()) {
            return new SingleCharSeparatorFactory();
        } else {
            return null;
        }
    }
}
