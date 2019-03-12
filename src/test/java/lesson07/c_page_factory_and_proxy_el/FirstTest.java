package lesson07.c_page_factory_and_proxy_el;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
		LandingPage landingPage = new LandingPage(driver);

		landingPage.searchFor(query1);
		(new WebDriverWait(driver, 10))
				.until(ExpectedConditions
						.textToBePresentInElement(landingPage.firstTip, query1));

		landingPage.searchFor(query2);
		(new WebDriverWait(driver, 10))
				.until(ExpectedConditions
						.textToBePresentInElement(landingPage.firstTip, query2));
	}

	class LandingPage {

		@FindBy(id = "search_query_top")
		WebElement searchField;

		@FindBy(xpath = "//*[@id=\"index\"]/div[2]/ul/li[1]")
		WebElement firstTip;

		public LandingPage(WebDriver driver) {
			PageFactory.initElements(driver, this);
		}

		void searchFor(String query) {
			searchField.clear();
			searchField.sendKeys(query);
		}

	}
}
