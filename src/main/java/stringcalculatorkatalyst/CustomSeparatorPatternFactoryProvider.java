package stringcalculatorkatalyst;

import java.util.regex.Pattern;

public class CustomSeparatorPatternFactoryProvider {

    public CustomSeparatorPatternFactory get(String customSeparatorString) {
        Pattern pattern1 = Pattern.compile("^(\\[.\\])+");

        if(pattern1.matcher(customSeparatorString).matches()) {
            return new MultipleCommaAndSingleCharSeparatorFactory();
        } else {
            return null;
        }
    }
}
