package stringcalculatorkatalyst;

import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;

public class StringCalculator {

    private CalInputStringParser parser;

    public StringCalculator(CalInputStringParser parser) {
        this.parser = parser;
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
        int[] stringNumbers = parser.parse(stringNumber);

        int sum = validate(stringNumbers[0]);
        for(int index = 1; index < stringNumbers.length; index++) {
            sum += validate(stringNumbers[index]);
        }

        return sum;
    }

    private int validate(int val) {

        if(val < 0) {
            throw new IllegalArgumentException("negatives not allowed");
        }

        if(val > 1000) {
            return 0;
        }

        return val;
    }
}
