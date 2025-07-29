package ui.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ui.config.ConfigApp;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    private static final ConfigApp config = ConfigFactory.create(ConfigApp.class);

    @BeforeAll
    public static void setup() {
        Configuration.browser = config.browser();
        Configuration.browserSize = config.browserSize();
        Configuration.timeout = config.timeout();
        Configuration.baseUrl = config.baseUrl();
        Configuration.pageLoadTimeout = 60_000;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterAll
    public static void close() {
        closeWebDriver();
    }
}