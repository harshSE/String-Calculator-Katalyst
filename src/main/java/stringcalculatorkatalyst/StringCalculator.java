package stringcalculatorkatalyst;

import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;

public class StringCalculator {
    private Pattern pattern;

    public StringCalculator() {
        pattern = Pattern.compile(",|\n");
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

    private int addNumbers(String stringNumber) {
        String[] stringNumbers1;
        if(stringNumber.startsWith("//")) {
            int endIndexOfNewLineChar = stringNumber.indexOf("\\n");
            String customSeparator = stringNumber.substring(2, endIndexOfNewLineChar);
            String substring = stringNumber.substring(endIndexOfNewLineChar+2);
            String regexString = ",\n" + customSeparator;
            stringNumbers1 = substring.split("[" + regexString + "]");
        } else {
            stringNumbers1 = pattern.split(stringNumber);
        }
        String[] stringNumbers = stringNumbers1;


        int sum = toInt(stringNumbers[0]);
        for(int index = 1; index < stringNumbers.length; index++) {
            sum += toInt(stringNumbers[index]);
        }

        return sum;
    }

    private int toInt(String stringNumber) {
        return parseInt(stringNumber.trim());
    }
}
