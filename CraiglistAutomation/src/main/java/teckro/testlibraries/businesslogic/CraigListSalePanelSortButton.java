package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import teckro.testlibraries.controls.ComboBoxItem;
import teckro.testlibraries.controls.ComboBoxItemAscending;
import teckro.testlibraries.controls.ComboBoxItemDescending;
import teckro.testlibraries.controls.WebControl;
import teckro.testlibraries.utils.TestLogger;

import java.nio.file.Paths;
import java.util.List;

public class CraigListSalePanelSortButton extends WebControl {

    private static final String SELECTOR_SORT_MODE = ".cl-search-sort-mode";
    private static final String SELECTOR_SORT_BTN = ".cl-search-sort-mode button";
    private static final String SELECTOR_NEWEST_BTN_FALLBACK = ".bd-button.cl-search-sort-mode-newest";
    private static final String SELECTOR_SORT_OPTIONS = "div.items button";
    
    private static final String CLASS_PRICE_ASC = "cl-search-sort-mode-price-asc";
    private static final String CLASS_PRICE_DESC = "cl-search-sort-mode-price-desc";
    
    private static final String BTN_PRICE_ASC = "button.cl-search-sort-mode-price-asc";
    private static final String BTN_PRICE_DESC = "button.cl-search-sort-mode-price-desc";
    private static final String BTN_NEWEST = "button.cl-search-sort-mode-newest";
    private static final String BTN_OLDEST = "button.cl-search-sort-mode-oldest";
    private static final String BTN_RELEVANCE = "button.cl-search-sort-mode-relevance, button.cl-search-sort-mode-relevant";

    public CraigListSalePanelSortButton(Page page) {
        super(page.locator(SELECTOR_SORT_MODE).first());
        this.page = page;
    }

    public void openSortDropdown() {
        Locator btn = page.locator(SELECTOR_SORT_BTN).first();
        if (btn.count() == 0) {
            btn = page.locator(SELECTOR_NEWEST_BTN_FALLBACK).first();
        }
        this.locator = btn; // make sure the locator is updated to the actual button
        click();
    }

    public List<ComboBoxItem> getSortOptions() {
        return page.locator(SELECTOR_SORT_OPTIONS).all()
                .stream()
                .map(loc -> {
                    String className = loc.getAttribute("class");
                    String text = loc.innerText();
                    if (className != null && className.contains(CLASS_PRICE_ASC)) {
                        return new ComboBoxItemAscending(loc, text);
                    } else if (className != null && className.contains(CLASS_PRICE_DESC)) {
                        return new ComboBoxItemDescending(loc, text);
                    } else {
                        return new ComboBoxItem(loc, text);
                    }
                })
                .toList();
    }

    public ComboBoxItem getItem(SortOption option) {
        return switch (option) {
            case PRICE_ASCENDING ->
                    new ComboBoxItemAscending(page.locator(BTN_PRICE_ASC), "price ascending");
            case PRICE_DESCENDING ->
                    new ComboBoxItemDescending(page.locator(BTN_PRICE_DESC), "price descending");
            case NEWEST -> new ComboBoxItem(page.locator(BTN_NEWEST), "newest");
            case OLDEST -> new ComboBoxItem(page.locator(BTN_OLDEST), "oldest");
            case RELEVANCE ->
                    new ComboBoxItem(page.locator(BTN_RELEVANCE), "relevance");
            default -> throw new IllegalArgumentException("Unsupported option: " + option);
        };
    }

    public void selectOption(SortOption option) {
        TestLogger.info("Sorting by " + option);
        openSortDropdown();

        ComboBoxItem item = getItem(option);
        item.waitForVisible();
        item.select();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("sort_screenshot.png")));
    }

    public int countArrowOptions() {
        return page.getByText("→", new Page.GetByTextOptions().setExact(false)).count();
    }
}
