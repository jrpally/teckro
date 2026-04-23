import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import teckro.testlibraries.browser.BrowserEngine;
import teckro.testlibraries.browser.BrowserFactory;

public abstract class BaseTest {

    protected static Browser browser;
    private static Playwright playwright;

    @BeforeAll
    public static void beforeAll() {
        playwright = Playwright.create();
        browser = BrowserFactory.createBrowser(playwright, BrowserEngine.CHROME);
    }

    @AfterAll
    public static void afterAll() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}