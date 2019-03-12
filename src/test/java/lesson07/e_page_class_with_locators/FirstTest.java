package lesson07.e_page_class_with_locators;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

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
		String query1 = "Dress";
		String query2 = "T-shirt";
		LandingPage landingPage = new LandingPage();

		landingPage.searchFor(query1);
		(new WebDriverWait(driver, 10))
				.until(ExpectedConditions
						.textToBePresentInElementLocated(landingPage.firstTipLocator, query1));

		landingPage.searchFor(query2);
		(new WebDriverWait(driver, 10))
				.until(ExpectedConditions
						.textToBePresentInElementLocated(landingPage.firstTipLocator, query2));
	}

	class LandingPage {

		// Don't do so
		//WebElement searchField = driver.findElement(By.id("search_query_top"));

		By searchFieldLocator = By.id("search_query_top");
		By firstTipLocator = By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]");

		void searchFor(String query) {
			$(searchFieldLocator).clear();
			$(searchFieldLocator).sendKeys(query);
		}

		WebElement $(By locator) {
			return driver.findElement(locator);
		}
	}
}
