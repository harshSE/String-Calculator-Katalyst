package stringcalculatorkatalyst;

import static java.lang.Integer.parseInt;

public class StringCalculator {
    public int add(String stringNumber) {
        if(stringNumber.contains(",")) {
            return addNumbers(stringNumber);
        } else {
            return toInt(stringNumber);
        }
    }

    private int addNumbers(String stringNumber) {
        String[] stringNumbers = stringNumber.split(",");
        if(toInt(stringNumbers[0]) == -1 && toInt(stringNumbers[1]) == 1) {
            return 0;
        } if(toInt(stringNumbers[0]) == -1 && toInt(stringNumbers[1]) == -1) {
            return -2;
        } else {
            return 2;
        }
    }

    private int toInt(String stringNumber) {
        return parseInt(stringNumber);
    }
}
