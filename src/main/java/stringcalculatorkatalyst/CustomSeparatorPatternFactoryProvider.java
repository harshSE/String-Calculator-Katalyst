package stringcalculatorkatalyst;

import java.util.regex.Pattern;

public class CustomSeparatorPatternFactoryProvider {

    private Pattern multipleBracketAndSingleCharPatten = Pattern.compile("^(\\[.\\])+");
    private Pattern singleBracketAndMultipleCharPatten = Pattern.compile("^\\[[^\\]]+\\]");
    private Pattern singleCharPatten = Pattern.compile("^.");

    public CustomSeparatorPatternFactory get(String customSeparatorString) {
        if(multipleBracketAndSingleCharPatten.matcher(customSeparatorString).matches()) {
            return new MultipleBracketAndSingleCharSeparatorPatternFactory();
        } else if(customSeparatorString.startsWith("[") && singleBracketAndMultipleCharPatten.matcher(customSeparatorString).matches()) {
           return new SingleBracketAndMultipleCharsSeparatorPatternFactory();
        } else if(singleCharPatten.matcher(customSeparatorString).matches()) {
            return new SingleCharSeparatorPatternFactoryFactory();
        } else {
            return null;
        }
    }
}
