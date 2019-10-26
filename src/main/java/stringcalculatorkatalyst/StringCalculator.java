package stringcalculatorkatalyst;

import stringcalculatorkatalyst.exception.ValidationException;

import java.util.HashSet;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;

public class StringCalculator {
    private Pattern pattern;
    private HashSet<Character> escapeCharacters;
    private CalInputStringParser parser;

    public StringCalculator() {
        pattern = Pattern.compile("[,\n]");
        escapeCharacters = new HashSet<>(asList('\\','^','*', '$','.','|','?','+','(',')','[',']','{','}'));
        parser = new CalInputStringParser();

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
        String[] stringNumbers = parser.split(stringNumber);

        int sum = toInt(stringNumbers[0]);
        for(int index = 1; index < stringNumbers.length; index++) {
            sum += toInt(stringNumbers[index]);
        }

        return sum;
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
