package stringcalculatorkatalyst;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(JUnitParamsRunner.class)
public class CustomSeparatorPatternFactoryProviderTest {

    private CustomSeparatorPatternFactoryProvider customSeparatorPatternFactoryProvider;

    public CustomSeparatorPatternFactoryProviderTest() {
        customSeparatorPatternFactoryProvider =  new CustomSeparatorPatternFactoryProvider();
    }
    @Test
    @Parameters({
            ";;",
            "[*x][^]",
            "[*x];;",
            ";;[x]",
            "[*];"
    })
    public void getShouldReturnNullWhenNotMatchingProviderFound(String argument) {
        assertThat(customSeparatorPatternFactoryProvider.get(argument), is(nullValue()));
    }

    @Test
    @Parameters({
            "[*]",
            "[*][^]"
    })
    public void getShouldReturnInstanceOfMultipleBracketAndSingleCharInstanceWhenMatchingArgumentFound(String argument) {
        assertTrue(customSeparatorPatternFactoryProvider.get(argument) instanceof MultipleBracketAndSingleCharSeparatorPatternFactory);
    }

    @Test
    @Parameters({
            "[**]",
            "[***]"
    })
    public void getShouldReturnInstanceOfSingleBracketAndMultipleCharsInstanceWhenMatchingArgumentFound(String argument) {
        assertTrue(customSeparatorPatternFactoryProvider.get(argument) instanceof SingleBracketAndMultipleCharsSeparatorPatternFactory);
    }

    @Test
    @Parameters({
            "$",
            "*"
    })
    public void getShouldReturnInstanceOfSingleCharInstanceWhenMatchingArgumentFound(String argument) {
        assertTrue(customSeparatorPatternFactoryProvider.get(argument) instanceof SingleCharSeparatorPatternFactoryFactory);
    }

}
