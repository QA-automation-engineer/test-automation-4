package lesson08.b_add_basepage_and_simple_api;

import org.junit.AfterClass;
import org.junit.AssumptionViolatedException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public abstract class BaseTest extends SimpleAPI{

	protected static WebDriver driver;

	@Override
	WebDriver getDriver() {
		return driver;
	}

	@Rule
	public TestWatcher testWatcher = new TestWatcher() {
		@Override
		protected void succeeded(Description description) {
			System.out.println(String
					.format("Test '%s' - PASSED", description.getMethodName()));
			super.succeeded(description);
		}

		@Override
		protected void failed(Throwable e, Description description) {
			System.out.println(String
					.format("Test '%s' - FAILED due to: %s",
							description.getMethodName(),
							e.getMessage()));
			super.failed(e, description);
		}

		@Override
		protected void skipped(AssumptionViolatedException e, Description description) {
			System.out.println(String
					.format("Test '%s' - SKIPPED", description.getMethodName()));
			super.skipped(e, description);
		}

		@Override
		protected void starting(Description description) {
			System.out.println(String
					.format("Test '%s' - is starting...", description.getMethodName()));
			super.starting(description);
		}
	};

	@BeforeClass
	public static void setUp() {
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}

	void assertThat(ExpectedCondition<Boolean> condition) {
		assertThat(condition, 10l);
	}

	void assertThat(ExpectedCondition<Boolean> condition, long timeout) {
		waitFor(condition, timeout);
	}
}
