package utils;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class SimpleAPI {

	private static final Logger LOGGER = LogManager.getLogger(SimpleAPI.class);

	protected abstract WebDriver getDriver();

	protected void open(String url) {
		getDriver().get(url);
	}

	protected WebElement $(By locator) {
		return $(locator, Conditions.VISIBLE);
	}

	protected WebElement $(String xPath) {
		return $(By.xpath(xPath));
	}

	protected WebElement $(By locator, Function<By, ExpectedCondition<WebElement>> condition) {
		return waitFor(condition.apply(locator));
	}

	protected WebElement $(By locator, Conditions condition) {
		return waitFor(condition.getCondition().apply(locator));
	}

	protected List<WebElement> $$(By locator) {
		return waitFor(visibilityOfAllElementsLocatedBy(locator));
	}

	protected List<WebElement> $$(String xPath) {
		return $$(By.xpath(xPath));
	}

	protected <T> T waitFor(ExpectedCondition<T> condition, long timeout) {
		return (new WebDriverWait(getDriver(), timeout)).until(condition);
	}

	protected <T> T waitFor(ExpectedCondition<T> condition) {
		return waitFor(condition, 30l);
	}

	protected void setValue(WebElement element, String value) {
		clickOn(element);
		element.clear();
		element.sendKeys(value);
	}

	protected void setValue(By locator, String value) {
		clickOn(locator);
		$(locator).clear();
		$(locator).sendKeys(value);
	}

	protected void clickOn(WebElement element) {
		waitFor(elementToBeClickable(element)).click();
	}

	protected void clickOn(By locator) {
		$(locator, Conditions.CLICKABLE).click();
	}

	protected void waitForDocumentCompleteState() {
		try {
			waitFor(driver -> {
				String documentState = (String) ((JavascriptExecutor) driver)
						.executeScript("return document.readyState");
				LOGGER.debug(String.format("Current document state is: %s", documentState));
				return "complete".equals(documentState);
			}, 30);
		} catch (TimeoutException e) {
			LOGGER.warn("Can't wait till document.readyState is complete");
		}
	}

	public String getPageTitle() {
		return getDriver().getTitle();
	}

	protected void captureScreenshoot(String methodName) {
		File screenshot = ((TakesScreenshot)getDriver())
				.getScreenshotAs(OutputType.FILE);
		String screenshotName = screenshot.getName().replace("screenshot", methodName + "_");
		String path = System.getProperty("report.path") + "/screenshots/" + screenshotName;
		try {
			FileUtils.copyFile(screenshot, new File(path));
			LOGGER.error("Screenshot was got: " + screenshotName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}