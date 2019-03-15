package lesson08.c_add_assertall;

import static org.hamcrest.CoreMatchers.containsString;

import org.junit.Assert;
import org.junit.Test;

public class FirstTest extends BaseTest {

	@Test
	public void verifyFirstTip() {
		// Given
		final String query1 = "Dress";
		final String query2 = "T-shirt";
		final LandingPage landingPage = new LandingPage(driver);
		landingPage.visit();
		landingPage.searchFor(query1);
		final String oldTipText = landingPage.getFirstTipText();
		// When
		landingPage.searchFor(query2, oldTipText);
		// Then
		final String newTipText = landingPage.getFirstTipText();
		assertAll(() -> Assert.assertThat(newTipText, containsString(query2 + "")),
				() -> Assert.assertThat(newTipText, containsString(query2)),
				() -> Assert.assertThat(newTipText, containsString(query2 + "")));
	}
}