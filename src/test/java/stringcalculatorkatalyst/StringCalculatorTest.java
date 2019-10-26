package stringcalculatorkatalyst;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class StringCalculatorTest {
    private StringCalculator calculator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
            "-1\\,1,0",
            "1\\,-1,0",
            "-1\\,-1,-2",
            "0\\,0,0",
            "-1\\,2,1",
            "2\\,-1,1",
            "-2\\,1,-1",
            "1\\,-2,-1",
    })
    public void addingTwoStringIntegerNumbers_ReturnTheirSum(String numbers, int result) {
        assertThat(calculator.add(numbers), is(result));
    }

    public static char[][] emptyStrings() {
        return new char[][] {
                {},
                {' '},
                {' ', ' ', ' '}
        };
    }

    @Test
    @Parameters(method = "emptyStrings")
    public void addingEmptyString_ReturnZero(char[] argument) {
        String stringNumber = new String(argument);
        assertThat(calculator.add(stringNumber), is(0));
    }


    @Test
    public void addShouldRemoveHeadingAndTrailingWhiteSpace() {
        assertThat(calculator.add(" 1 "), is(1));
        /*
            as paramrunner trim the arguments, adding two test cases to ease. other option is hierarchical context runner
         */
        assertThat(calculator.add(" 1,2 "), is(3));
    }

    @Test
    public void addShouldRemoveWhiteSpacesBetweenNumbers() {
        assertThat(calculator.add("1 ,  2"), is(3));
    }

    @Test
    @Parameters({
            "1\\,1\\,,2",
            "1\\,,1",
    })
    public void addShouldIgnoreLastCommaInArgument(String numbers, int result) {
        assertThat(calculator.add(numbers), is(result));
    }

    @Test
    public void addingNullStringThrowsIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalToIgnoringCase("null string not allowed"));
        calculator.add(null);
    }


}
