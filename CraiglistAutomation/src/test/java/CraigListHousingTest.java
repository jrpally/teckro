import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import teckro.testlibraries.browser.BrowserEngine;
import teckro.testlibraries.browser.BrowserFactory;
import teckro.testlibraries.businesslogic.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CraigListHousingTest {

    private CraigListPage craigListPage;
    private static Browser browser;
    private static Playwright playwright;

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
    @DisplayName("Default page shows price ascending, descending and newest sort options")
    public void testDefaultSortOptions() {
        CraigListHousingForSale housingForSale = craigListPage.navigateToHousingForSale();
        CraigListSalePanel salePanel = housingForSale.getCraigListSalePanel();
        CraigListSalePanelSortButton sortBtn = salePanel.getCraigListPanelSortButton();

        sortBtn.openSortDropdown();
        int arrowCount = sortBtn.countArrowOptions();
        assertTrue(arrowCount >= 2, "Expected at least two price sort options");
        
        List<ComboBoxItem> options = sortBtn.getSortOptions();
        assertTrue(options.stream().anyMatch(opt -> opt.getText().toLowerCase().contains("nuevo")), "Expected 'newest' (nuevo) sort option to be present");
    }

    @Test
    @DisplayName("Price sorting orders listings from low to high and high to low")
    public void testPriceSorting() {
        CraigListHousingForSale housingForSale = craigListPage.navigateToHousingForSale();
        CraigListSalePanel salePanel = housingForSale.getCraigListSalePanel();
        CraigListSalePanelSortButton sortBtn = salePanel.getCraigListPanelSortButton();

        sortBtn.selectOption(SortOption.PRICE_ASCENDING); // Match precio initially
        
        List<ItemForSale> ascItems = salePanel.getItemsForSale();
        List<Integer> ascPrices = ascItems.stream()
            .map(ItemForSale::getPrice)
            .filter(p -> !p.isEmpty())
            .map(Integer::parseInt)
            .toList();
        assertTrue(CraigListSalePanel.isAscending(ascPrices), "Prices should be sorted in ascending order: " + ascPrices);

        sortBtn.selectOption(SortOption.PRICE_DESCENDING); // We will control toggle logic from the options button
        List<ItemForSale> descItems = salePanel.getItemsForSale();
        List<Integer> descPrices = descItems.stream()
            .map(ItemForSale::getPrice)
            .filter(p -> !p.isEmpty())
            .map(Integer::parseInt)
            .toList();
        assertTrue(CraigListSalePanel.isDescending(descPrices), "Prices should be sorted in descending order: " + descPrices);
    }

    @Test
    @DisplayName("Search results expose additional sorting options: relevance")
    public void testSortOptionsAfterSearch() {
        CraigListHousingForSale housingForSale = craigListPage.navigateToHousingForSale();
        CraigListSalePanel salePanel = housingForSale.getCraigListSalePanel();
        
        salePanel.search("house");
        
        CraigListSalePanelSortButton sortBtn = salePanel.getCraigListPanelSortButton();
        sortBtn.openSortDropdown();
        
        int arrowCountAfterSearch = sortBtn.countArrowOptions();
        assertTrue(arrowCountAfterSearch >= 2, "Expected price sort options after search");
        
        List<ComboBoxItem> options = sortBtn.getSortOptions();
        
        // "upcoming" is not displayed on Madrid's version of Craigslist, so we skip checking it here.
        assertTrue(options.stream().anyMatch(opt -> opt.getText().toLowerCase().contains("relevan")), "Expected 'relevance' (relevancia/relevantes) sort option after search");
    }

    @AfterEach
    public void tearDown() {
    }
}
