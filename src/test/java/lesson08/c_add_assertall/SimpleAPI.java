package lesson08.c_add_assertall;

import static lesson08.c_add_assertall.Conditions.VISIBLE;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;

import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class SimpleAPI {

	abstract WebDriver getDriver();

	protected void open(String url) {
		getDriver().get(url);
	}

	protected WebElement $(By locator) {
		return $(locator, VISIBLE);
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

	<T> T waitFor(ExpectedCondition<T> condition, long timeout) {
		return (new WebDriverWait(getDriver(), timeout)).until(condition);
	}

	<T> T waitFor(ExpectedCondition<T> condition) {
		return waitFor(condition, 10l);
	}
}
