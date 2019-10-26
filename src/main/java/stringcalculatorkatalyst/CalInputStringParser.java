package stringcalculatorkatalyst;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class CalInputStringParser {

    private Pattern pattern;
    private CustomSeparatorPatternFactoryProvider provider;

    public CalInputStringParser(CustomSeparatorPatternFactoryProvider provider) {
        pattern = Pattern.compile("[,\n]");
        this.provider = provider;
    }

    public int[] parse(String stringNumber) throws IllegalArgumentException{

        String val = stringNumber.trim();

        Stream<String> stringNumberStream;

        Pattern patternToUsed;

        String stringNumberToParse = val;
        if(val.startsWith("//")) {
            int startIndexOfNewLineChar = val.indexOf("\n");
            stringNumberToParse = val.substring(startIndexOfNewLineChar + 1);

            patternToUsed = createPattern(val.substring(2, startIndexOfNewLineChar));
        } else {
            patternToUsed = pattern;
        }

        stringNumberStream = patternToUsed.splitAsStream(stringNumberToParse);

        return stringNumberStream.map(String::trim).mapToInt(Integer::parseInt).toArray();
    }


    private Pattern createPattern(String customSeparatorString) {

        CustomSeparatorPatternFactory customSeparatorPatternFactory = provider.get(customSeparatorString);
        if(Objects.isNull(customSeparatorPatternFactory)) {
            throw new IllegalArgumentException("Invalid expression provided after //");
        }

        return customSeparatorPatternFactory.createPattern(customSeparatorString);

    }

}
