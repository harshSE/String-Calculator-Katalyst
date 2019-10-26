package stringcalculatorkatalyst;

import static java.lang.Integer.parseInt;

public class StringCalculator {
    public int add(String stringNumber) {

        if(stringNumber.trim().equals("")) {
            return 0;
        }

        if(stringNumber.contains(",")) {
            return addNumbers(stringNumber);
        } else {
            return toInt(stringNumber);
        }
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
