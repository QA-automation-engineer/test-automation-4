package lesson08.b_add_basepage_and_simple_api;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.function.Function;

public enum Conditions {
	visible(ExpectedConditions::visibilityOfElementLocated),
	clickable(ExpectedConditions::elementToBeClickable);

	private final Function<By, ExpectedCondition<WebElement>> condition;

	Conditions(Function<By, ExpectedCondition<WebElement>> condition) {
		this.condition = condition;
	}

	public Function<By, ExpectedCondition<WebElement>> getCondition() {
		return condition;
	}
}
