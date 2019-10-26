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
        String[] stringNumbers;
        if(stringNumber.startsWith("//")) {
            int endIndex = stringNumber.indexOf("\\n");
            String customSeparator = stringNumber.substring(2, endIndex);
            String substring = stringNumber.substring(endIndex+2);
            stringNumbers = substring.split(",|\n|" + customSeparator);
        } else {
            stringNumbers = pattern.split(stringNumber);
        }



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
