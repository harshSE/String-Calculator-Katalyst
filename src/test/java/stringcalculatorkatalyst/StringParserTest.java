package stringcalculatorkatalyst;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

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
        parser = new CalInputStringParser(new CustomSeparatorPatternFactoryProvider());
    }


    public Object[][]  lastCommaInArgument() {
        return new Object [][]{
                {"1,1,1,", new int[]{1,1,1}},
                {"1,", new int[]{1}}
        };
    }
    @Test
    @Parameters(method = "lastCommaInArgument")
    public void parseShouldIgnoreLastCommaInArgument(String numbers, int [] result) {
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
    public void parseShouldSupportCustomSeparator(String numbers, int [] result) {
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
    public void parseShouldAllowSeparatorWithArbitraryLength(String numbers, int [] result) {
        assertThat(parser.parse(numbers), is(equalTo(result)));
    }

    public Object[][]  multipleSeparatorsWithMultipleCharacters() {
        return new Object [][]{
                {"//[*][+][^]\n1*1+2^3", new int[]{1,1,2,3}},
                {"//[*x][+][^]\n1*x1+2^3", new int[]{1,1,2,3}},
                {"//[*][+s][^]\n1*1+s2^3", new int[]{1,1,2,3}},
                {"//[*][+][^x]\n1*1+2^x3", new int[]{1,1,2,3}},
                {"//[*x][+][^]\n1*x1+2^3", new int[]{1,1,2,3}},
                {"//[*][+s][^]\n1*1+s2^3", new int[]{1,1,2,3}},
                {"//[*][+][^x]\n1*1+2^x3", new int[]{1,1,2,3}},
        };
    }

    @Test
    @Parameters(method = "multipleSeparatorsWithMultipleCharacters")
    public void parseShouldAllowMultipleSeparatorsWithSingleCharacter(String numbers, int[] result) {
        assertThat(parser.parse(numbers), is(equalTo(result)));
    }

    public Object[][]  moreThenOneCharacterFoundWithoutBracket() {
        return new Object [][]{
                {"//[[\n1***1"},
                {"//***\n1***1"},
        };
    }

    @Test
    @Parameters(method = "moreThenOneCharacterFoundWithoutBracket")
    public void parseShouldThrowIllegalArgumentExceptionWhenMoreThenOneCharacterFoundWithoutBracket(String numbers) {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalToIgnoringCase("Invalid expression provided after //"));
        parser.parse(numbers);
    }


    @Test
    public void parseShouldThrowIllegalArgumentExceptionWhenNoSeparatorProvided() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalToIgnoringCase("Invalid expression provided after //"));
        parser.parse("//\n1***1");
    }


    @Test
    public void parseShouldThrowIllegalArgumentExceptionWhenNoSeparatorProvidedBetweenBracket() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalToIgnoringCase("Invalid expression provided after //"));
        parser.parse("//[]\n1***1");
    }

    @Test
    public void parseShouldThrowIllegalArgumentExceptionWhenClosingBracketNotProvided() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(equalToIgnoringCase("Invalid expression provided after //"));
        parser.parse("//[***\n1***1");
    }

    @Test
    public void parseShouldRemoveHeadingAndTrailingWhiteSpace() {
        assertThat(parser.parse(" 1 "), is(equalTo(new int[]{1})));
        /*
            as paramrunner trim the arguments, adding two test cases to ease. other option is hierarchical context runner
         */
        assertThat(parser.parse(" 1,2 "), is(equalTo(new int[]{1,2})));
    }

    @Test
    public void parseShouldRemoveWhiteSpacesBetweenNumbers() {
        assertThat(parser.parse("1 ,  2"), is(equalTo(new int[]{1,2})));
    }



}