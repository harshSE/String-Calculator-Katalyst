package stringcalculatorkatalyst;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class StringCalculatorTest {
    private StringCalculator calculator;

    @Before
    public void setUp() {
        calculator = new StringCalculator();
    }

    @Test
    @Parameters({
            "1,1",
            "-1,-1",
            "0,0",
    })
    public void addingOneStringIntegerNumber_ReturnSameNumber(String number, int result) {
        assertThat(calculator.add(number), is(result));
    }

    @Test
    @Parameters({
            "1\\,1,2",
            "-1\\,1,0"
    })
    public void addingTwoStringIntegerNumbers_ReturnTheirSum(String numbers, int result) {
        assertThat(calculator.add(numbers), is(result));
    }
}
