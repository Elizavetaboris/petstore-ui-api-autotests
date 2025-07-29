package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Selenide.$;

public class AuthPage extends BasePage {
    private final SelenideElement apiKey = $("#api_key_value");

    public AuthPage checkTitleVisible() {
        apiKey.shouldBe(Condition.visible);
        Assertions.assertTrue(apiKey.isDisplayed(), "Элемент не отображается.");
        return this;
    }
}
