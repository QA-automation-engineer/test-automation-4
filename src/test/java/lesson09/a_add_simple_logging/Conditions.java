package lesson09.a_add_simple_logging;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.function.Function;

public enum Conditions {
	VISIBLE(ExpectedConditions::visibilityOfElementLocated),
	CLICKABLE(ExpectedConditions::elementToBeClickable);

	private final Function<By, ExpectedCondition<WebElement>> condition;

	Conditions(Function<By, ExpectedCondition<WebElement>> condition) {
		this.condition = condition;
	}

	public Function<By, ExpectedCondition<WebElement>> getCondition() {
		return condition;
	}
}