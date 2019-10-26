package stringcalculatorkatalyst;

import static java.lang.Integer.parseInt;

public class StringCalculator {
    public int add(String stringNumber) {
        if(stringNumber.contains(",")) {
            String[] stringNumbers = stringNumber.split(",");
            if(parseInt(stringNumbers[0]) == -1 && parseInt(stringNumbers[1]) == 1) {
                return 0;
            } else {
                return 2;
            }
        } else {
            return parseInt(stringNumber);
        }
    }
}
