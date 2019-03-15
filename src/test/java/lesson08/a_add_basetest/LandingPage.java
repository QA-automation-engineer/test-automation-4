package lesson08.a_add_basetest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LandingPage {

	private WebDriver driver;

	By searchFieldLocator = By.id("search_query_top");
	By firstTipLocator = By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]");

	public LandingPage(WebDriver driver) {
		this.driver = driver;
	}

	void searchFor(String query) {
		$(searchFieldLocator).clear();
		$(searchFieldLocator).sendKeys(query);
	}

	WebElement $(By locator) {
		return driver.findElement(locator);
	}
}
