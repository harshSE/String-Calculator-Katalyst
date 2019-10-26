package stringcalculatorkatalyst;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

class CalInputStringParser {

    private Pattern pattern;
    private HashSet<Character> escapeCharacters;


    public CalInputStringParser() {
        pattern = Pattern.compile("[,\n]");
        escapeCharacters = new HashSet<>(asList('\\','^','*', '$','.','|','?','+','(',')','[',']','{','}'));
    }

    public int[] parse(String stringNumber) {

        String val = stringNumber.trim();

        Stream<String> stringNumberStream;

        Pattern patternToUsed;

        String substring = val;
        if(stringNumber.startsWith("//")) {
            int startIndexOfNewLineChar = val.indexOf("\n");
            substring = stringNumber.substring(startIndexOfNewLineChar + 1);
            patternToUsed = createPattern(val);
        } else {
            patternToUsed = pattern;
        }



        stringNumberStream = patternToUsed.splitAsStream(substring);


        return stringNumberStream.map(String::trim).mapToInt(Integer::parseInt).toArray();
    }

    private Pattern createPattern(String stringNumber) {
        int startIndexOfNewLineChar = stringNumber.indexOf("\n");

        Pattern pattern1 = Pattern.compile("^(\\[.\\])+");

        if(pattern1.matcher(stringNumber.substring(2,startIndexOfNewLineChar)).matches()) {
            List<String> separators = new ArrayList<>();
            for(int index = 2; index < startIndexOfNewLineChar; index+=3) {
                if(stringNumber.charAt(index) == '[' && stringNumber.charAt(index+2) == ']') {
                    char separator = stringNumber.charAt(index + 1);
                    separators.add(""+separator);
                }
            }

            String regex = createRegex(separators);
            return Pattern.compile(regex);

        } else if(stringNumber.startsWith("//[") && Pattern.compile("^\\[[^\\]]+\\]").matcher(stringNumber.substring(2,startIndexOfNewLineChar)).matches()) {
            int endIndex = startIndexOfNewLineChar - 1;
            String customSeparator = stringNumber.substring(3, endIndex);
            String regexString = createRegex(singletonList(customSeparator));
            return  Pattern.compile(regexString);
        } else if(Pattern.compile("^.").matcher(stringNumber.substring(2,startIndexOfNewLineChar)).matches()) {
            String customSeparator = stringNumber.substring(2, startIndexOfNewLineChar);
            String regexString = createRegex(singletonList(customSeparator));
            return Pattern.compile(regexString);
        } else {
            throw new IllegalArgumentException("Invalid expression provided after //");
        }

    }



    private Stream<String> splitWithCustomSeparator(String stringNumber) {
        int endIndexOfNewLineChar = stringNumber.indexOf("\n");
        return splitWithCustomSeparator(stringNumber, endIndexOfNewLineChar, 2, endIndexOfNewLineChar);
    }

    private Stream<String> splitWithArbitraryLengthOfCustomSeparator(String stringNumber) {
        int endIndexOfNewLineChar = stringNumber.indexOf("\n");
        int endIndex = endIndexOfNewLineChar - 1;
        return splitWithCustomSeparator(stringNumber, endIndexOfNewLineChar, 3, endIndex);

    }

    private Stream<String> splitWithCustomSeparator(String stringNumber, int endIndexOfNewLineChar, int beginIndex, int endIndex) {
        String customSeparator = stringNumber.substring(beginIndex, endIndex);
        String substring = stringNumber.substring(endIndexOfNewLineChar + 1);
        String regexString = createRegex(singletonList(customSeparator));
        Pattern pattern = Pattern.compile(regexString);
        return  pattern.splitAsStream(substring);
    }



    private String createRegex(List<String> separators) {
        StringBuilder newCustomSeparator = new StringBuilder();
        for (String separator : separators) {
            char customSeparatorChar = separator.charAt(0);

            newCustomSeparator.append('|');
            if(escapeCharacters.contains(customSeparatorChar)) {

                for(int index = 0; index < separator.length(); index++) {
                    newCustomSeparator.append("\\").append(customSeparatorChar);
                }
            } else {
                newCustomSeparator.append(customSeparatorChar);
            }
        }

        return ",|\n" + newCustomSeparator;
    }




}
