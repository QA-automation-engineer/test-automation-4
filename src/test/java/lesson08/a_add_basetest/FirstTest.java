package lesson08.a_add_basetest;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;

public class FirstTest extends BaseTest{

	@Test
	public void verifyFirstTip() {
		String query1 = "Dress";
		String query2 = "T-shirt";
		LandingPage landingPage = new LandingPage(driver);

		landingPage.searchFor(query1);
		assertThat(textToBePresentInElementLocated(landingPage.firstTipLocator, query1));

		landingPage.searchFor(query2);
		assertThat(textToBePresentInElementLocated(landingPage.firstTipLocator, query2 + "dsds"));
	}

	void assertThat(ExpectedCondition<Boolean> condition) {
		assertThat(condition, 10l);
	}

	void assertThat(ExpectedCondition<Boolean> condition, long timeout) {
		(new WebDriverWait(driver, timeout))
				.until(condition);
	}
}
