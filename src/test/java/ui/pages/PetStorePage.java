package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Selenide.$x;

public class PetStorePage extends BasePage {
    private final SelenideElement auth = $x("//*[text()='Authorize']/..");
    private final String titleModule = "//a//span[text()='%s']";
    private final String petTagSection = "//a//span[text()='%s']/../..";
    private final SelenideElement method = $x("//*[@id='operations-pet-findPetsByTags']");

    public PetStorePage checkVisibleModuleByName(String nameModule) {
        SelenideElement element = $x(String.format(titleModule, nameModule));
        element.shouldBe(Condition.visible);
        Assertions.assertTrue(element.isDisplayed(), "Элемент не отображается на странице.");
        return this;
    }

    public PetStorePage clickTitleModule(String nameModule) {
        SelenideElement element = $x(String.format(titleModule, nameModule));
        element.shouldBe(Condition.visible)
                .click();
        return this;
    }

    public PetStorePage checkAttributeOpen(String nameModule, String attribute, boolean expected) {
        SelenideElement element = $x(String.format(petTagSection, nameModule));
        element.scrollTo();
        checkAttribute(element, attribute, expected);
        return this;
    }

    public PetStorePage checkClassDeprecated(String expectedClass) {
        method.shouldHave(attributeMatching("class", ".*\\b" + expectedClass + "\\b.*"));
        return this;
    }

    public AuthPage clickAuth() {
        auth.shouldBe(Condition.visible).click();
        return new AuthPage();
    }

}