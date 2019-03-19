package lesson09.a_add_simple_logging;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static lesson09.a_add_simple_logging.Conditions.CLICKABLE;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementWithText;

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