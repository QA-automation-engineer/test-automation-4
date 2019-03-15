package lesson08.c_add_assertall;

import static lesson08.c_add_assertall.Conditions.CLICKABLE;
import static lesson08.c_add_assertall.Conditions.VISIBLE;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementWithText;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LandingPage extends BasePage {

	By searchFieldLocator = By.id("search_query_top");
	By firstTipLocator = By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]");

	public LandingPage(WebDriver driver) {
		super(driver);
	}

	void visit() {
		open("http://automationpractice.com/index.php");
	}

	void searchFor(String query) {
		$(searchFieldLocator, CLICKABLE).click();
		$(searchFieldLocator).clear();
		$(searchFieldLocator).sendKeys(query);
	}

	void searchFor(String query, String oldTip) {
		searchFor(query);
		waitFor(invisibilityOfElementWithText(firstTipLocator, oldTip), 5l);
	}

	String getFirstTipText() {
		return $(firstTipLocator).getText();
	}
}