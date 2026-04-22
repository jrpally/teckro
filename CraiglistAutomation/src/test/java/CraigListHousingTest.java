import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import teckro.testlibraries.browser.BrowserEngine;
import teckro.testlibraries.browser.BrowserFactory;
import teckro.testlibraries.businesslogic.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CraigListHousingTest {

    private CraigListPage craigListPage;
    private static Browser browser;
    private static Playwright  playwright;

    @BeforeAll
    public static void beforeAll() {
        playwright = Playwright.create();
        browser = BrowserFactory.createBrowser(playwright, BrowserEngine.CHROME);
    }

    @BeforeEach
    public void setUp() {
        this.craigListPage = new CraigListPage(browser);
        this.craigListPage.goHome("madrid");
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
        CraigListSalePanel craigListSalePanel = craigListHousingForSale.getCraigListSalePanel();
        CraigListSalePanelSortButton craigListSalePanelSortButton = craigListSalePanel.getCraigListPanelSortButton();


        assertTrue(true);
    }

    @AfterEach
    public void tearDown() {
    }
}
