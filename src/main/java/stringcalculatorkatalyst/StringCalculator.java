package stringcalculatorkatalyst;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;

public class StringCalculator {
    private Pattern pattern;
    private HashSet<Character> escapeCharacters;

    public StringCalculator() {
        pattern = Pattern.compile("[,\n]");
        escapeCharacters = new HashSet<>(asList('\\','^','*', '$','.','|','?','+','(',')','[',']','{','}'));
    }
    public int add(String stringNumber) throws IllegalArgumentException{

        if(isNull(stringNumber)) {
           throw new IllegalArgumentException("Null string not allowed");
        }

        if(stringNumber.trim().equals("")) {
            return 0;
        }

        return addNumbers(stringNumber);
    }

    private int addNumbers(String stringNumber) throws IllegalArgumentException{
        String[] stringNumbers = split(stringNumber);

        int sum = toInt(stringNumbers[0]);
        for(int index = 1; index < stringNumbers.length; index++) {
            sum += toInt(stringNumbers[index]);
        }

        return sum;
    }

    private String[] split(String stringNumber) {
        String[] stringNumbers;

        if(stringNumber.startsWith("//[")) {
            stringNumbers = splitWithArbitraryLengthOfCustomSeperator(stringNumber);
        } else if(stringNumber.startsWith("//")) {
            stringNumbers = splitWithCustomSeparator(stringNumber);
        } else {
            stringNumbers = pattern.split(stringNumber);
        }
        return stringNumbers;
    }

    private String[] splitWithCustomSeparator(String stringNumber) {
        String[] stringNumbers;
        int endIndexOfNewLineChar = stringNumber.indexOf("\n");
        String customSeparator = stringNumber.substring(2, endIndexOfNewLineChar);
        String substring = stringNumber.substring(endIndexOfNewLineChar+1);
        String regexString = ",\n" + customSeparator;
        stringNumbers = substring.split("[" + regexString + "]");
        return stringNumbers;
    }

    private String[] splitWithArbitraryLengthOfCustomSeperator(String stringNumber) {
        String[] stringNumbers;
        int endIndexOfNewLineChar = stringNumber.indexOf("\n");
        String customSeparator = stringNumber.substring(3, endIndexOfNewLineChar-1);
        String substring = stringNumber.substring(endIndexOfNewLineChar+1);
        String regexString = createRegexString(customSeparator);
        stringNumbers = substring.split(regexString);
        return stringNumbers;
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

    private int toInt(String stringNumber) {
        int val = parseInt(stringNumber.trim());
        if(val < 0) {
            throw new IllegalArgumentException("negatives not allowed");
        }

        if(val > 1000) {
            return 0;
        }

        return val;
    }
}
