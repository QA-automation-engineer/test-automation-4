package lesson10.c_file_uploading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.AssumptionViolatedException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import utils.EventHandler;
import utils.SimpleAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class BaseTest extends SimpleAPI {

	private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

	protected static WebDriver driver;

	@Override
	protected WebDriver getDriver() {
		return driver;
	}

	@Rule
	public TestWatcher testWatcher = new TestWatcher() {
		@Override
		protected void succeeded(Description description) {
			LOGGER.info(String
					.format("Test '%s' - PASSED", description.getMethodName()));
			super.succeeded(description);
		}

		@Override
		protected void failed(Throwable e, Description description) {
			LOGGER.info(String
					.format("Test '%s' - FAILED due to: %s",
							description.getMethodName(),
							e.getMessage()));
			super.failed(e, description);
		}

		@Override
		protected void skipped(AssumptionViolatedException e, Description description) {
			LOGGER.info(String
					.format("Test '%s' - SKIPPED", description.getMethodName()));
			super.skipped(e, description);
		}

		@Override
		protected void starting(Description description) {
			LOGGER.info(String
					.format("Test '%s' - is starting...", description.getMethodName()));
			super.starting(description);
		}
	};

	@BeforeClass
	public static void setUp() {
		EventFiringWebDriver wd = new EventFiringWebDriver(new ChromeDriver());
		wd.register(new EventHandler());

		driver = wd;
		LOGGER.debug("WebDriver has been started");
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
		LOGGER.debug("WebDriver has been shut down");
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
