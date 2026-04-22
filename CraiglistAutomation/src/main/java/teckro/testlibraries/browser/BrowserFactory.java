package teckro.testlibraries.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {

    public static Browser createBrowser(Playwright playwright, BrowserEngine engine) {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(false);
        return switch (engine) {
            case CHROME -> playwright.chromium().launch(launchOptions);
            case FIREFOX -> playwright.firefox().launch(launchOptions);
            case WEBKIT -> playwright.webkit().launch(launchOptions);

            // Not supported by Playwright
            case IE, OPERA, SAFARI ->
                    throw new UnsupportedOperationException(engine + " is not supported by Playwright");

            default ->
                    throw new IllegalArgumentException("Unknown browser: " + engine);
        };
    }
}