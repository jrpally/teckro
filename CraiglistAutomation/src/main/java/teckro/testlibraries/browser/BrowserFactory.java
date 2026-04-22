package teckro.testlibraries.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {

    public static Browser createBrowser(BrowserEngine engine) {
        Playwright playwright = Playwright.create();
        return switch (engine) {
            case CHROME -> playwright.chromium().launch();
            case FIREFOX -> playwright.firefox().launch();
            case WEBKIT -> playwright.webkit().launch();

            // Not supported by Playwright
            case IE, OPERA, SAFARI ->
                    throw new UnsupportedOperationException(engine + " is not supported by Playwright");

            default ->
                    throw new IllegalArgumentException("Unknown browser: " + engine);
        };
    }
}