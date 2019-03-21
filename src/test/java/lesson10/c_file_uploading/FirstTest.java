package lesson10.c_file_uploading;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;

public class FirstTest extends BaseTest {

	@Test
	public void verifyUploadingImageOnGoogle() {
		// Given
		open("https://www.google.com.ua/imghp?hl=uk&tab=wi");
		assertThat(titleContains("Google"));
		// When
		$("//*[@id=\"sbtc\"]/div/div[2]/div[1]").click();
		$(By.linkText("Завантажте зображення")).click();
		$(By.id("qbfile"))
				.sendKeys("C:\\Users\\vmuser\\Documents\\comautomationpractice\\download.jpg");
		// Then
		assertThat(textToBePresentInElementLocated(By.xpath("//*[@id=\"topstuff\"]/div/div[2]/a"),
				"milliner (la modiste - renée vert)"));
	}
}