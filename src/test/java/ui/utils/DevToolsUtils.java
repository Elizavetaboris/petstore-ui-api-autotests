package ui.utils;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v115.network.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DevToolsUtils {
    private static final List<String> interceptedUrls = new ArrayList<>();

    public static void interceptRequest(String expectedUrlPart) {
        interceptedUrls.clear();

        DevTools devTools = ((HasDevTools) WebDriverRunner.getWebDriver()).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.addListener(Network.requestWillBeSent(), request -> {
            String requestUrl = request.getRequest().getUrl();
            if (requestUrl.contains(expectedUrlPart)) {
                interceptedUrls.add(requestUrl);
                System.out.println("📡 Перехвачен запрос: " + requestUrl);
            }
        });
    }

    public static boolean wasRequestSent(String expectedUrlPart) {
        return interceptedUrls.stream().anyMatch(url -> url.contains(expectedUrlPart));
    }
}
