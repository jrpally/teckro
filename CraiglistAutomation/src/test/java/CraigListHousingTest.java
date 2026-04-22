import com.microsoft.playwright.Browser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import teckro.testlibraries.browser.BrowserEngine;
import teckro.testlibraries.browser.BrowserFactory;
import teckro.testlibraries.businesslogic.CraigListHomeSection;
import teckro.testlibraries.businesslogic.CraigListPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CraigListHousingTest {

    @BeforeEach
    public void setUp() {
        Browser browser = BrowserFactory.createBrowser(BrowserEngine.CHROME);
        CraigListPage craigListPage = new CraigListPage(browser);
        craigListPage.goHome();
    }

    @Test
    public void testExample() {
        assertTrue(true);
    }

    @AfterEach
    public void tearDown() {
    }
}
