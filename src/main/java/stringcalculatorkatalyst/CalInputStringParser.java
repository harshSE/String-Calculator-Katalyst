package stringcalculatorkatalyst;

import stringcalculatorkatalyst.exception.ValidationException;

import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

class CalInputStringParser {

    private Pattern pattern;
    private HashSet<Character> escapeCharacters;

    public CalInputStringParser() {
        pattern = Pattern.compile("[,\n]");
        escapeCharacters = new HashSet<>(asList('\\','^','*', '$','.','|','?','+','(',')','[',']','{','}'));

    }

    public String[] split(String stringNumber) {

        String val = stringNumber.trim();

        Stream<String> stringNumberStream;

        if(stringNumber.startsWith("//[")) {
            stringNumberStream = splitWithArbitraryLengthOfCustomSeparator(val);
        } else if(stringNumber.startsWith("//")) {
            stringNumberStream = splitWithCustomSeparator(val);
        } else {
            stringNumberStream = pattern.splitAsStream(val);
        }

        return stringNumberStream.map(String::trim).toArray(String[]::new);
    }

    private Stream<String> splitWithCustomSeparator(String stringNumber) {
        int endIndexOfNewLineChar = stringNumber.indexOf("\n");

        if (isNoCharBetweenDoubleSlashAndNewChar(endIndexOfNewLineChar)) {
            throw new ValidationException("No separator provided");
        } else if (isMoreThanOneCharBetweenDoubleSlashAndNewChar(endIndexOfNewLineChar)) {
            throw new ValidationException("Only single character is allowed as custom separator");
        }
        return splitWithCustomSeparator(stringNumber, endIndexOfNewLineChar, 2, endIndexOfNewLineChar);
    }

    private boolean isMoreThanOneCharBetweenDoubleSlashAndNewChar(int endIndexOfNewLineChar) {
        return endIndexOfNewLineChar != 3;
    }

    private boolean isNoCharBetweenDoubleSlashAndNewChar(int endIndexOfNewLineChar) {
        return endIndexOfNewLineChar == 2;
    }

    private Stream<String> splitWithArbitraryLengthOfCustomSeparator(String stringNumber) {

        int endIndexOfNewLineChar = stringNumber.indexOf("\n");
        int endIndex = endIndexOfNewLineChar - 1;
        char charAtEndIndex = stringNumber.charAt(endIndex);

        if (charAtEndIndex == '[') {
            return splitWithCustomSeparator(stringNumber);
        } else if (charAtEndIndex == ']' && isAnyCharsBetweenBracket(endIndex)) {
            throw new ValidationException("No separator provided between bracket");
        }  else if (charAtEndIndex != ']') {
            throw new ValidationException("No closing bracket provided");
        } else {
            return splitWithCustomSeparator(stringNumber, endIndexOfNewLineChar, 3, endIndex);
        }

    }

    private boolean isAnyCharsBetweenBracket(int indexOfClosingBracket) {
        return indexOfClosingBracket - 2 == 1;
    }

    private Stream<String> splitWithCustomSeparator(String stringNumber, int endIndexOfNewLineChar, int beginIndex, int endIndex) {

        String customSeparator = stringNumber.substring(beginIndex, endIndex);
        String substring = stringNumber.substring(endIndexOfNewLineChar + 1);
        String regexString = createRegexString(customSeparator);
        Pattern pattern = Pattern.compile(regexString);
        return  pattern.splitAsStream(substring);
    }

    private String createRegexString(String customSeparator) {
        char customSeparatorChar = customSeparator.charAt(0);
        if(escapeCharacters.contains(customSeparatorChar)) {
            StringBuilder newCustomSeparator = new StringBuilder();
            for(int index = 0; index < customSeparator.length(); index++) {
                newCustomSeparator.append("\\").append(customSeparatorChar);
            }
            customSeparator = newCustomSeparator.toString();
        }
        return ",|\n|" + customSeparator;
    }

}
