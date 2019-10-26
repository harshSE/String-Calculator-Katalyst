package stringcalculatorkatalyst;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class SingleCharSeparatorFactoryTest {
    private SingleCharSeparatorFactory factory;

    @Before
    public void setUp() {
        factory = new SingleCharSeparatorFactory();
    }

    public Object[][] parametersForCreatePatternFromProvidedInput() {
        return new Object[][] {
                {"*", Pattern.compile(",|\n|\\*")},
                {";", Pattern.compile(",|\n|;")},
        };
    }
    @Test
    @Parameters(method = "parametersForCreatePatternFromProvidedInput")
    public void createPatternThatIncludeDefaultSeparatorFromProvidedInput(String string, Pattern pattern) {
        assertThat(factory.createPattern(string).pattern(), is(equalTo(pattern.pattern())));
    }
}
