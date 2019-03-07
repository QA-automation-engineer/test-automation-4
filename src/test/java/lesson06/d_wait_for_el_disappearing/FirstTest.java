package lesson06.d_wait_for_el_disappearing;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;

public class FirstTest {

	static WebDriver driver;

	@BeforeClass
	public static void setUp() {
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

		driver.get("http://automationpractice.com/index.php");
		driver.manage().window().maximize();
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}

	@Test
	public void verifyFirstTip() {
		driver.findElement(By.id("search_query_top")).clear();
		driver.findElement(By.id("search_query_top")).sendKeys("Dress");

		Assert.assertThat(driver
				.findElement(By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]")).getText(),
				containsString("Dress"));

//		driver.findElement(By.id("search_query_top")).click();
		driver.findElement(By.id("search_query_top")).clear();
		driver.findElement(By.id("search_query_top")).sendKeys("T-shirt");

		waitForElementDisappearing(By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]"), 5000L);
		Assert.assertThat(driver
						.findElement(By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]")).getText(),
				containsString("T-shirt"));
	}

	void waitForElementDisappearing(By by, long timeout) {
		long initialTime = System.currentTimeMillis();
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean hp = false;
		while (true) {
			if (System.currentTimeMillis() - initialTime > timeout ) {
				hp = true;
				break;
			}
			if (driver.findElements(by).isEmpty())
				break;
		}
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		if(hp) {
			throw new TimeoutException("Element is still present");
		}
	}
}
