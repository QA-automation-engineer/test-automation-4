package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.SimpleAPI;

public abstract class BasePage extends SimpleAPI {

	protected WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@Override
	protected WebDriver getDriver() {
		return driver;
	}
}
