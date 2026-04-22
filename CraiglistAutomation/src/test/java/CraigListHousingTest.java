import com.microsoft.playwright.Browser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import teckro.testlibraries.browser.BrowserEngine;
import teckro.testlibraries.browser.BrowserFactory;
import teckro.testlibraries.businesslogic.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CraigListHousingTest {

    private CraigListPage craigListPage;
    private Browser browser;

    @BeforeEach
    public void setUp() {
        this.browser = BrowserFactory.createBrowser(BrowserEngine.CHROME);
        this.craigListPage = new CraigListPage(browser);
        craigListPage.goHome("madrid");
    }

    @Test
    public void testExample() {
        CraigListHousingSection craigListHousingSection =
                craigListPage.getCraigListSections().stream()
                        .filter(CraigListHousingSection.class::isInstance)
                        .map(CraigListHousingSection.class::cast)
                        .findFirst()
                        .orElse(null);

        CraigListHousingForSale craigListHousingForSale = craigListHousingSection.clickForSale();
        SortOption
        craigListHousingForSale.sortBy()


        assertTrue(true);
    }

    @AfterEach
    public void tearDown() {
    }
}
