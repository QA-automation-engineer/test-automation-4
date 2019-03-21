package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static utils.Conditions.CLICKABLE;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementWithText;

public class LandingPage extends BasePage {

	By searchFieldLocator = By.id("search_query_top");
	By firstTipLocator = By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]");

	public LandingPage(WebDriver driver) {
		super(driver);
	}

	public void visit() {
		open("http://automationpractice.com/index.php");
	}

	public void searchFor(String query) {
		$(searchFieldLocator, CLICKABLE).click();
		$(searchFieldLocator).clear();
		$(searchFieldLocator).sendKeys(query);
	}

	public void searchFor(String query, String oldTip) {
		searchFor(query);
		waitFor(invisibilityOfElementWithText(firstTipLocator, oldTip), 5l);
	}

	public String getFirstTipText() {
		return $(firstTipLocator).getText();
	}
}