package stringcalculatorkatalyst;

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
        String separator = ",";

        if(stringNumber.contains("\n")) {
            separator = "\n";
        }
        String[] stringNumbers = stringNumber.split(separator);

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
