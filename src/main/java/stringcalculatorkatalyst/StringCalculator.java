package stringcalculatorkatalyst;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;

public class StringCalculator {
    private Pattern pattern;

    public StringCalculator() {
        pattern = Pattern.compile("[,\n]");
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
            int endIndexOfNewLineChar = stringNumber.indexOf("\n");
            String customSeparator = stringNumber.substring(3, endIndexOfNewLineChar-1);
            String substring = stringNumber.substring(endIndexOfNewLineChar+1);
            HashSet<Character> escapeCharacters = new HashSet<>(Arrays.asList('\\','^','*'));

            char customSeparatorChar = customSeparator.charAt(0);
            if(escapeCharacters.contains(customSeparatorChar)) {
                StringBuilder newCustomSeparator = new StringBuilder();
                for(int index = 0; index < customSeparator.length(); index++) {
                    newCustomSeparator.append("\\").append(customSeparatorChar);
                }
                customSeparator = newCustomSeparator.toString();
            }
            String regexString = ",|\n|" + customSeparator;
            stringNumbers = substring.split(regexString);
        } else if(stringNumber.startsWith("//")) {
            int endIndexOfNewLineChar = stringNumber.indexOf("\\n");
            String customSeparator = stringNumber.substring(2, endIndexOfNewLineChar);
            String substring = stringNumber.substring(endIndexOfNewLineChar+2);
            String regexString = ",\n" + customSeparator;
            stringNumbers = substring.split("[" + regexString + "]");
        } else {
            stringNumbers = pattern.split(stringNumber);
        }
        return stringNumbers;
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
