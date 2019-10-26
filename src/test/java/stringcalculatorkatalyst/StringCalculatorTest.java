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
            "0,0",
    })
    public void addingOneStringIntegerNumber_ReturnSameNumber(String number, int result) {
        assertThat(calculator.add(number), is(result));
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

    public String[]  commaSeparatedNumbers() {
        return new String []{
            "1\\,1\\,2,4",
            "1\\,0\\,2\\,5,8",
            "1\\,1,2",
            "0\\,0,0",
        };
    }


    public String[]  newLineSeparatedNumbers() {
            return new String[] {
                    "1\n1\n2,4"
            };
    }



    public String[]  newLineAndCommaSeparatedNumbers() {
        return new String[] {
                "1\n1\\,2,4",
        };

    }

    @Test
    @Parameters(method = "commaSeparatedNumbers, newLineSeparatedNumbers, newLineAndCommaSeparatedNumbers")
    public void addingArbitraryNumberWihNewLineOrCommaSeparatorReturnTheirSum(String numbers, int result) {
        assertThat(calculator.add(numbers), is(result));
    }

    @Test
    @Parameters({
            "//;\n1;2,3",
            "//*\n1*2,3"
    })
    public void addShouldSupportCustomSeparator(String numbers, int result) {
        assertThat(calculator.add(numbers), is(result));
    }

    @Test
    public void addingNegativeNumberThrowIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalToIgnoringCase("negatives not allowed"));
        calculator.add("1,-1");
    }

    @Test
    @Parameters({
            "1001,0",
            "1000\\,1001,1000",
            "1000\\,999\\,1001,1999"
    })
    public void addingNumberBiggerThan1000WillBeIgnored(String numbers, int result) {
        assertThat(calculator.add(numbers), is(result));
    }

    @Test
    @Parameters({
            "//[***]\n1***1,2",
    })
    public void addShouldAllowSeperatorWithArbitraryLenght(String numbers, int result) {
        assertThat(calculator.add(numbers), is(result));
    }

}
