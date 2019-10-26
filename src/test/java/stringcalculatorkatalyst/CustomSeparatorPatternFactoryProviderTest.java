package stringcalculatorkatalyst;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class CustomSeparatorPatternFactoryProviderTest {

    private CustomSeparatorPatternFactoryProvider customSeparatorPatternFactoryProvider;

    public CustomSeparatorPatternFactoryProviderTest() {
        customSeparatorPatternFactoryProvider =  new CustomSeparatorPatternFactoryProvider();
    }
    @Test
    public void getShouldReturnNullWhenNotMatchingProviderFound() {
        assertThat(customSeparatorPatternFactoryProvider.get(";;"), CoreMatchers.is(CoreMatchers.nullValue()));
    }
}
