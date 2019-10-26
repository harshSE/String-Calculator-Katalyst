package stringcalculatorkatalyst;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import stringcalculatorkatalyst.exception.ValidationException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class StringParserTest {
    private CalInputStringParser parser;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        parser = new CalInputStringParser();
    }


    @Test
    @Parameters({
            "1\\,1\\,1,1,1,1",
            "1\\,,1",
    })
    public void addShouldIgnoreLastCommaInArgument(String numbers, String [] result) {
        assertThat(parser.split(numbers), is(equalTo(result)));
    }

    public String[]  commaSeparatedNumbers() {
        return new String []{
                "1\\,1\\,2,1,1,2",
                "1\\,0\\,2\\,5,1,0,2,5",
                "1\\,1,1,1",
                "0\\,0,0,0",
        };
    }


    public String[]  newLineSeparatedNumbers() {
        return new String[] {
                "1\n1\n2,1,1,2"
        };
    }



    public String[]  newLineAndCommaSeparatedNumbers() {
        return new String[] {
                "1\n1\\,2,1,1,2",
        };

    }

    @Test
    @Parameters(method = "commaSeparatedNumbers, newLineSeparatedNumbers, newLineAndCommaSeparatedNumbers")
    public void addingArbitraryNumberWihNewLineOrCommaSeparatorReturnTheirSum(String numbers, String [] result) {
        assertThat(parser.split(numbers), is(equalTo(result)));
    }

    @Test
    @Parameters({
            "//;\n1;2,1,2",
            "//*\n1*2,1,2",
            "//[\n1[2,1,2"
    })
    public void addShouldSupportCustomSeparator(String numbers, String [] result) {
        assertThat(parser.split(numbers), is(equalTo(result)));
    }


    @Test
    @Parameters({
            "//[***]\n1***1,1,1",
            "//[+++]\n1+++1,1,1",
            "//[^^^]\n1^^^1\n2,1,1,2",
    })
    public void addShouldAllowSeparatorWithArbitraryLenght(String numbers, String [] result) {
        assertThat(parser.split(numbers), is(equalTo(result)));
    }

    @Test
    @Parameters({
            "//[[\n1***1",
            "//***\n1***1",
    })
    public void addShouldThrowValidationFailExceptionWhenMoreThenOneCharacterFoundWithoutBracket(String numbers) {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(equalToIgnoringCase("Only single character is allowed as custom separator"));
        parser.split(numbers);
    }


    @Test
    public void addShouldThrowValidationFailExceptionWhenNoSeparatorProvided() {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(equalToIgnoringCase("No separator provided"));
        parser.split("//\n1***1");
    }


    @Test
    public void addShouldThrowValidationFailExceptionWhenNoSeparatorProvidedBetweenBracket() {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(equalToIgnoringCase("No separator provided between bracket"));
        parser.split("//[]\n1***1");
    }

    @Test
    public void addShouldThrowValidationFailExceptionWhenClosingBracketNotProvided() {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(equalToIgnoringCase("No closing bracket provided"));
        parser.split("//[***\n1***1");
    }

}