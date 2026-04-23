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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

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
        CraigListSalePanel salePanel = craigListPage.navigateToHousingForSale().getCraigListSalePanel();
        salePanel.getCraigListPanelSortButton().openSortDropdown();
        
        int arrowCount = salePanel.getCraigListPanelSortButton().countArrowOptions();
        assertThat(arrowCount).withFailMessage("Expected at least two price sort options").isGreaterThanOrEqualTo(2);
        
        List<ComboBoxItem> options = salePanel.getCraigListPanelSortButton().getSortOptions();
        assertThat(options)
                .withFailMessage("Expected 'newest' (nuevo) sort option to be present")
                .anyMatch(opt -> opt.getText().toLowerCase().contains("nuevo"));
    }

    @Test
    @DisplayName("Price sorting orders listings from low to high and high to low")
    public void testPriceSorting() {
        CraigListSalePanel salePanel = craigListPage.navigateToHousingForSale().getCraigListSalePanel();

        salePanel.getCraigListPanelSortButton().selectOption(SortOption.PRICE_ASCENDING); 
        
        List<ItemForSale> ascItems = salePanel.getItemsForSale();
        List<Float> ascPrices = ascItems.stream()
            .map(ItemForSale::getPrice)
            .filter(Objects::nonNull)
            .toList();
        assertThat(ascPrices).withFailMessage("Prices should be sorted in ascending order: "
                + ascPrices).isSorted();

        salePanel.getCraigListPanelSortButton().selectOption(SortOption.PRICE_DESCENDING);
        List<ItemForSale> descItems = salePanel.getItemsForSale();
        List<Float> descPrices = descItems.stream()
            .map(ItemForSale::getPrice)
            .filter(Objects::nonNull)
            .toList();
        assertThat(descPrices).withFailMessage("Prices should be sorted in descending order: "
                        + descPrices).isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    @DisplayName("Search results expose additional sorting options: relevance")
    public void testSortOptionsAfterSearch() {
        CraigListSalePanel salePanel = craigListPage.navigateToHousingForSale().getCraigListSalePanel();
        salePanel.search("house");
            
        salePanel.getCraigListPanelSortButton().openSortDropdown();
        
        int arrowCountAfterSearch = salePanel.getCraigListPanelSortButton().countArrowOptions();
        assertThat(arrowCountAfterSearch).withFailMessage("Expected price sort options after search")
                .isGreaterThanOrEqualTo(2);
        
        List<ComboBoxItem> options = salePanel.getCraigListPanelSortButton().getSortOptions();
        
        // "upcoming" is not displayed on Madrid's version of Craigslist, so we skip checking it here.
        assertThat(options)
                .withFailMessage("Expected 'relevance' (relevancia/relevantes) sort option after search")
                .anyMatch(opt -> opt.getText().toLowerCase().contains("relevan"));
    }

    @AfterEach
    public void tearDown() {
    }
}
