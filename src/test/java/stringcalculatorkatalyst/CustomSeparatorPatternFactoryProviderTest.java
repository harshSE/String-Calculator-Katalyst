package stringcalculatorkatalyst;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class CustomSeparatorPatternFactoryProviderTest {

    private CustomSeparatorPatternFactoryProvider customSeparatorPatternFactoryProvider;

    public CustomSeparatorPatternFactoryProviderTest() {
        customSeparatorPatternFactoryProvider =  new CustomSeparatorPatternFactoryProvider();
    }
    @Test
    public void getShouldReturnNullWhenNotMatchingProviderFound() {
        assertThat(customSeparatorPatternFactoryProvider.get(";;"), is(nullValue()));
    }
}
