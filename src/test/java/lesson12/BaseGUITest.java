package lesson12;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import utils.EventHandler;
import utils.SimpleAPI;

public abstract class BaseGUITest extends SimpleAPI {

	private static final Logger LOGGER = LogManager.getLogger(BaseGUITest.class);

	protected WebDriver driver;

	@Override
	protected WebDriver getDriver() {
		return driver;
	}

	@Rule
	public TestWatcher testWatcher = new TestWatcher() {
		@Override
		protected void succeeded(Description description) {
			LOGGER.info(String
					.format("Test '%s' - PASSED", descriptionToReadableFormat(description)));
			super.succeeded(description);
		}

		@Override
		protected void failed(Throwable e, Description description) {
			LOGGER.info(String
					.format("Test '%s' - FAILED due to: %s",
							descriptionToReadableFormat(description),
							e.getMessage()));
			captureScreenshoot(description.getMethodName());
			super.failed(e, description);
		}

		@Override
		protected void skipped(AssumptionViolatedException e, Description description) {
			LOGGER.info(String
					.format("Test '%s' - SKIPPED", descriptionToReadableFormat(description)));
			super.skipped(e, description);
		}

		@Override
		protected void starting(Description description) {
			LOGGER.info(String
					.format("Test '%s' - is starting...", descriptionToReadableFormat(description)));
			if (driver == null) {
				final String seleniumUrl = System.getProperty("selenium.url");
				if (seleniumUrl != null) {
					DesiredCapabilities caps = DesiredCapabilities.chrome();
					try {
						driver = new RemoteWebDriver(new URL(seleniumUrl), caps);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				} else {
					driver = new ChromeDriver();
				}
				if (driver == null){
					throw new RuntimeException("WebDriver wasn't instantiated.");
				}
				EventFiringWebDriver wd = new EventFiringWebDriver(new ChromeDriver());
				wd.register(new EventHandler());

				driver = wd;
				LOGGER.debug("WebDriver has been started");
				driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
				driver.manage().window().setPosition(new Point(0,0));
				driver.manage().window().setSize(new Dimension(1920,1080));
			}
			super.starting(description);
		}

		@Override
		protected void finished(Description description) {
			driver.quit();
			driver = null;
			LOGGER.debug("WebDriver has been shut down.");
			super.finished(description);
		}
	};

	private String descriptionToReadableFormat(Description description){
		return description.getMethodName().replace("_", " ");
	}

	void assertThat(ExpectedCondition<Boolean> condition) {
		assertThat(condition, 10l);
	}

	void assertThat(ExpectedCondition<Boolean> condition, long timeout) {
		waitFor(condition, timeout);
	}

	void assertAll(Assertion... assertions) {
		List<Throwable> errors = new ArrayList<>();
		for (Assertion assertion : assertions) {
			try {
				assertion.assertSmth();
			} catch (Throwable throwable) {
				errors.add(throwable);
			}
		}
		if (!errors.isEmpty()) {
			throw new AssertionError(errors
					.stream()
					.map(assertionError -> "\n Failed" + assertionError.getMessage())
					.collect(Collectors.toList()).toString());
		}
	}

	@FunctionalInterface
	public interface Assertion {
		void assertSmth();
	}
}
