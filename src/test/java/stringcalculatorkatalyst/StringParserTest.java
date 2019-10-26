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


    public Object[][]  lastCommaInArgument() {
        return new Object [][]{
                {"1,1,1,", new int[]{1,1,1}},
                {"1,", new int[]{1}}
        };
    }
    @Test
    @Parameters(method = "lastCommaInArgument")
    public void splitShouldIgnoreLastCommaInArgument(String numbers, int [] result) {
        assertThat(parser.parse(numbers), is(equalTo(result)));
    }

    public Object[][]  commaSeparatedNumbers() {
        return new Object [][]{
                {"1,1,2", new int[]{1,1,2}},
        };
    }


    public Object[][]  newLineSeparatedNumbers() {
        return new Object[][] {
                {"1\n1\n2", new int[]{1,1,2}}
        };
    }



    public Object[][]  newLineAndCommaSeparatedNumbers() {
        return new Object[][] {
                {"1\n1,2", new int[]{1,1,2}}
        };

    }

    @Test
    @Parameters(method = "commaSeparatedNumbers, newLineSeparatedNumbers, newLineAndCommaSeparatedNumbers")
    public void parsingArbitraryNumberWihNewLineOrCommaSeparator(String numbers, int[] result) {
        assertThat(parser.parse(numbers), is(equalTo(result)));
    }


    public Object[][]  customSeparator() {
        return new Object [][]{
                {"//;\n1;2", new int[]{1,2}},
                {"//*\n1*2", new int[]{1,2}},
                {"//[\n1[2", new int[]{1,2}},
        };
    }

    @Test
    @Parameters(method = "customSeparator")
    public void splitShouldSupportCustomSeparator(String numbers, int [] result) {
        assertThat(parser.parse(numbers), is(equalTo(result)));
    }


    public Object[][]  separatorWithArbitraryLength() {
        return new Object [][]{
                {"//[***]\n1***1", new int[]{1,1}},
                {"//[+++]\n1+++1", new int[]{1,1}},
                {"//[^^^]\n1^^^1\n2", new int[]{1,1,2}},
        };
    }
    @Test
    @Parameters(method = "separatorWithArbitraryLength")
    public void splitShouldAllowSeparatorWithArbitraryLength(String numbers, int [] result) {
        assertThat(parser.parse(numbers), is(equalTo(result)));
    }


    @Test
    public void splitShouldAllowMultipleSeparatorsWithSingleCharacter() {
        assertThat(parser.parse("//[*][+][^]\n1*1+2^3"), is(equalTo(new int[]{1,2,3})));
    }

    public Object[][]  moreThenOneCharacterFoundWithoutBracket() {
        return new Object [][]{
                {"//[[\n1***1"},
                {"//***\n1***1"},
        };
    }

    @Test
    @Parameters(method = "moreThenOneCharacterFoundWithoutBracket")
    public void splitShouldThrowValidationFailExceptionWhenMoreThenOneCharacterFoundWithoutBracket(String numbers) {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(equalToIgnoringCase("Only single character is allowed as custom separator"));
        parser.parse(numbers);
    }


    @Test
    public void splitShouldThrowValidationFailExceptionWhenNoSeparatorProvided() {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(equalToIgnoringCase("No separator provided"));
        parser.parse("//\n1***1");
    }


    @Test
    public void splitShouldThrowValidationFailExceptionWhenNoSeparatorProvidedBetweenBracket() {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(equalToIgnoringCase("No separator provided between bracket"));
        parser.parse("//[]\n1***1");
    }

    @Test
    public void splitShouldThrowValidationFailExceptionWhenClosingBracketNotProvided() {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage(equalToIgnoringCase("No closing bracket provided"));
        parser.parse("//[***\n1***1");
    }

    @Test
    public void addShouldRemoveHeadingAndTrailingWhiteSpace() {
        assertThat(parser.parse(" 1 "), is(equalTo(new int[]{1})));
        /*
            as paramrunner trim the arguments, adding two test cases to ease. other option is hierarchical context runner
         */
        assertThat(parser.parse(" 1,2 "), is(equalTo(new int[]{1,2})));
    }

    @Test
    public void addShouldRemoveWhiteSpacesBetweenNumbers() {
        assertThat(parser.parse("1 ,  2"), is(equalTo(new int[]{1,2})));
    }



}