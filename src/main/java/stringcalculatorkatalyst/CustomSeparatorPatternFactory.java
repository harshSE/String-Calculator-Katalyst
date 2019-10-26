package stringcalculatorkatalyst;

import java.util.regex.Pattern;

public interface CustomSeparatorPatternFactory {
    Pattern createPattern(String customSeparatorString);
}
