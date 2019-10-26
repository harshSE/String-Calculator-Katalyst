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
                {"//[+]\n1+1", new int[]{1,1}},
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
        assertThat(parser.parse("//[*][+][^]\n1*1+2^3"), is(equalTo(new int[]{1,1,2,3})));
    }

    public Object[][]  multipleSeparatorsWithMultipleCharacters() {
        return new Object [][]{
                {"//[*x][+][^]\n1*1+2^3"},
                {"//[*][+s][^]\n1*1+2^3"},
                {"//[*][+][^x]\n1*1+2^3"},
        };
    }

    @Test
    @Parameters(method = "multipleSeparatorsWithMultipleCharacters")
    public void splitShouldThrowIllegalArgumentExceptionWhenMultipleSeparatorsWithMultipleCharactersFound(String numbers) {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalToIgnoringCase("Invalid expression provided after //"));
        parser.parse(numbers);
    }

    public Object[][]  moreThenOneCharacterFoundWithoutBracket() {
        return new Object [][]{
                {"//[[\n1***1"},
                {"//***\n1***1"},
        };
    }

    @Test
    @Parameters(method = "moreThenOneCharacterFoundWithoutBracket")
    public void splitShouldThrowIllegalArgumentExceptionWhenMoreThenOneCharacterFoundWithoutBracket(String numbers) {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalToIgnoringCase("Invalid expression provided after //"));
        parser.parse(numbers);
    }


    @Test
    public void splitShouldThrowIllegalArgumentExceptionWhenNoSeparatorProvided() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalToIgnoringCase("Invalid expression provided after //"));
        parser.parse("//\n1***1");
    }


    @Test
    public void splitShouldThrowIllegalArgumentExceptionWhenNoSeparatorProvidedBetweenBracket() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalToIgnoringCase("Invalid expression provided after //"));
        parser.parse("//[]\n1***1");
    }

    @Test
    public void splitShouldThrowIllegalArgumentExceptionWhenClosingBracketNotProvided() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalToIgnoringCase("Invalid expression provided after //"));
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