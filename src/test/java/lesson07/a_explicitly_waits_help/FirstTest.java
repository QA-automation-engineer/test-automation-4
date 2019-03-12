package lesson07.a_explicitly_waits_help;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;

public class FirstTest {

	static WebDriver driver;

	@BeforeClass
	public static void setUp() {
		driver = new ChromeDriver();

		//driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
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

		(new WebDriverWait(driver, 10))
				.until(ExpectedConditions
						.textToBePresentInElementLocated(By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]"), "Dress"));

		(new WebDriverWait(driver, 10))
				.ignoring(StaleElementReferenceException.class)
				.until(webDriver -> webDriver
						.findElement(By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]"))
						.getText()
						.contains("Dress"));

//		driver.findElement(By.id("search_query_top")).click();
		driver.findElement(By.id("search_query_top")).clear();
		driver.findElement(By.id("search_query_top")).sendKeys("T-shirt");

		(new WebDriverWait(driver, 10))
				.ignoring(StaleElementReferenceException.class)
				.until(webDriver -> webDriver
						.findElement(By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]"))
						.getText()
						.contains("T-shirt"));
	}
}
