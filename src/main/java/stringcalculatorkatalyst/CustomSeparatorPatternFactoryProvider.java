package stringcalculatorkatalyst;

import java.util.regex.Pattern;

public class CustomSeparatorPatternFactoryProvider {

    private Pattern multipleBracketAndSingleCharPatten = Pattern.compile("^(\\[.\\])+");
    private Pattern singleBracketAndMultipleCharPatten = Pattern.compile("^\\[[^\\]]+\\]");
    private Pattern singleCharPatten = Pattern.compile("^.");

    public CustomSeparatorPatternFactory get(String customSeparatorString) {
        if(multipleBracketAndSingleCharPatten.matcher(customSeparatorString).matches()) {
            return new MultipleCommaAndSingleCharSeparatorFactory();
        } else if(customSeparatorString.startsWith("[") && singleBracketAndMultipleCharPatten.matcher(customSeparatorString).matches()) {
           return new SingleBracketAndMultipleCharsSeparatorFactory();
        } else if(singleCharPatten.matcher(customSeparatorString).matches()) {
            return new SingleCharSeparatorFactory();
        } else {
            return null;
        }
    }
}
