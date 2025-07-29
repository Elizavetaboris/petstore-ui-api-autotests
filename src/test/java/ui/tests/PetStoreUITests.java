package ui.tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ui.pages.PetStorePage;
import ui.utils.DevToolsUtils;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$;
import static ui.utils.DevToolsUtils.wasRequestSent;

class PetStoreUITests extends TestBase {
    private final PetStorePage petStore = new PetStorePage();

    @Test
    public void checkPageHeaders() {
        Selenide.open("");
        petStore.checkVisibleModuleByName("pet")
                .checkVisibleModuleByName("store")
                .checkVisibleModuleByName("user");
    }

    @Test
    public void checkOpenCloseOrder() {
        Selenide.open("");
        petStore.checkAttributeOpen("pet", "data-is-open", true)
                .clickTitleModule("pet")
                .checkAttributeOpen("pet", "data-is-open", false)
                .checkAttributeOpen("store", "data-is-open", true)
                .clickTitleModule("store")
                .checkAttributeOpen("store", "data-is-open", false)
                .checkAttributeOpen("user", "data-is-open", true)
                .clickTitleModule("user")
                .checkAttributeOpen("user", "data-is-open", false);
    }

    @Test
    public void checkDeprecatedMarkers() {
        Selenide.open("");
        petStore.checkClassDeprecated("deprecated");
    }

    @Test
    public void checkAuthBtn() {
        Selenide.open("");
        petStore.clickAuth()
                .checkTitleVisible();
    }

    @Test
    public void checkDevToolsSendRequest() {
        Selenide.open("");

        $("div.opblock-summary[data-path='/v2/pet'][data-method='post'] button").click();
        $("div.opblock[data-path='/v2/pet'][data-method='post'] button.try-out__btn").click();
        $("div.opblock[data-path='/v2/pet'][data-method='post'] button.execute")
            .shouldBe(enabled)
            .click();

        Assertions.assertTrue(
            DevToolsUtils.wasRequestSent("/v2/pet"),
            "Запрос к '/v2/pet' не был отправлен"
        );

    }
}

