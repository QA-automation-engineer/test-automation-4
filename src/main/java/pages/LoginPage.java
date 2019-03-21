package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{
	private static final Logger LOGGER = LogManager.getLogger(LoginPage.class);

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void visit(){
		open("http://automationpractice.com/index.php?controller=authentication");
	}

	public void logIn(String email, String pwd) {
		$(By.id("email")).sendKeys(email);
		$(By.id("passwd")).sendKeys(pwd);
		$(By.id("SubmitLogin")).click();
	}
}
