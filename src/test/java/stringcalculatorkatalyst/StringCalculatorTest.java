package stringcalculatorkatalyst;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import stringcalculatorkatalyst.exception.ValidationException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;

@RunWith(JUnitParamsRunner.class)
public class StringCalculatorTest {
    private StringCalculator calculator;
    private CalInputStringParser parser;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        parser = Mockito.spy(new CalInputStringParser());
        calculator = new StringCalculator(parser);
    }

    @Test
    @Parameters({
            "1,1",
            "0,0",
    })
    public void addingOneStringIntegerNumberReturnSameNumber(String number, int result) {
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
    public void addingEmptyStringReturnZero(char[] argument) {
        String stringNumber = new String(argument);
        assertThat(calculator.add(stringNumber), is(0));
    }



    @Test
    public void addingNullStringThrowsIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalToIgnoringCase("null string not allowed"));
        calculator.add(null);
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
    public void addShouldThrowValidationFailExceptionWhenParsingFail() {
        doThrow(new ValidationException("parsing fail")).when(parser).split(anyString());
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(equalToIgnoringCase("parsing fail"));

        calculator.add("//[[\n1***1");
    }

}
