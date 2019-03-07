package lesson06.a_no_waits_equals_pain;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.containsString;

public class FirstTest {

	static WebDriver driver;

	@BeforeClass
	public static void setUp() {
		driver = new ChromeDriver();

//		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
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

		Stream<WebElement> streamWe = driver
				.findElements(By.xpath("//*[@id=\"index\"]/div[2]/ul/li"))
				.stream();
		Optional<WebElement> webElementWithTip = streamWe
				.filter(we -> we.getText().contains("Dress"))
				.findAny();

		Assert.assertThat(webElementWithTip.get().getText(), containsString("Dress"));
		Assert.assertTrue(webElementWithTip.get().getText().contains("Dress"));
	}
}
