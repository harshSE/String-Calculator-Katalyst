package stringcalculatorkatalyst;

import org.hamcrest.CoreMatchers;

import static org.junit.Assert.assertThat;

public class StringCalculatorTest {
    private StringCalculator calculator;

    public void addingOneStringIntegerNumber_ReturnSameNumber() {
        assertThat(calculator.add("1"), CoreMatchers.is(1));
    }
}
