package stringcalculatorkatalyst;

import java.util.Objects;

import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;

public class StringCalculator {
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
        String[] stringNumbers = stringNumber.split(",");

        int sum = toInt(stringNumbers[0]);
        if(stringNumbers.length == 2) {
            sum += toInt(stringNumbers[1]);
        }

        return sum;
    }

    private int toInt(String stringNumber) {
        return parseInt(stringNumber.trim());
    }
}
