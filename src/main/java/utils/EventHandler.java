package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.util.Arrays;

public class EventHandler extends AbstractWebDriverEventListener{
	private static final Logger LOG = LogManager.getLogger(EventHandler.class);

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		LOG.info("Browser going to navigate to: " + url);
	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		LOG.debug("Finding element: " + by);
	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		LOG.debug("Going to click on element: " + getElementLocator(element));
	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		LOG.info("Going to set value: " + Arrays.toString(keysToSend) + " for element: " + getElementLocator(element));
	}

	private String getElementLocator(WebElement element){
		String elDescription = element.toString();
		int descriptionLength = elDescription.length();
		int startIndex = elDescription.indexOf("-> ") + 3;
		return "by " + elDescription.substring(startIndex, descriptionLength-1);
	}
}