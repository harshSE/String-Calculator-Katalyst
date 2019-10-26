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
        return toInt(stringNumbers[0]) + toInt(stringNumbers[1]);
    }

    private int toInt(String stringNumber) {
        return parseInt(stringNumber);
    }
}
