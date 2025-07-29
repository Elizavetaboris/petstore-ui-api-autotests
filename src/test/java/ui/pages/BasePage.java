package ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.attribute;

public class BasePage {

    protected void checkAttribute(SelenideElement element, String attribute, boolean expected) {
        String expectedValue = String.valueOf(expected);
        element.shouldHave(attribute(attribute, expectedValue));
        Assertions.assertEquals(
                expectedValue,
                element.getAttribute(attribute),
                String.format("Значение атрибута '%s' у элемента <%s> должно быть '%s', но было '%s'",
                        attribute,
                        element,
                        expectedValue,
                        element.getAttribute(attribute))
        );
    }
}