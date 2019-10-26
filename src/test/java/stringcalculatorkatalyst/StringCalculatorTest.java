package stringcalculatorkatalyst;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StringCalculatorTest {
    private StringCalculator calculator;

    @Test
    public void addingOneStringIntegerNumber_ReturnSameNumber() {
        assertThat(calculator.add("1"), is(1));
    }
}
