package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import teckro.testlibraries.controls.ComboBoxItem;
import teckro.testlibraries.controls.ComboBoxItemAscending;
import teckro.testlibraries.controls.ComboBoxItemDescending;
import teckro.testlibraries.controls.WebControl;

import java.util.List;

public class CraigListSalePanelSortButton extends WebControl {

    public CraigListSalePanelSortButton(Page page) {
        super(page.locator(".cl-search-sort-mode").first());
        this.page = page;
    }

    public void openSortDropdown() {
        Locator btn = page.locator(".cl-search-sort-mode button").first();
        if (btn.count() == 0) {
            btn = page.locator(".bd-button.cl-search-sort-mode-newest").first();
        }
        this.locator = btn; // make sure the locator is updated to the actual button
        click();
    }

    public List<ComboBoxItem> getSortOptions() {
        return page.locator("div.items button").all()
                .stream()
                .map(loc -> {
                    String className = loc.getAttribute("class");
                    String text = loc.innerText();
                    if (className != null && className.contains("cl-search-sort-mode-price-asc")) {
                        return new ComboBoxItemAscending(loc, text);
                    } else if (className != null && className.contains("cl-search-sort-mode-price-desc")) {
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
                    new ComboBoxItemAscending(page.locator("button.cl-search-sort-mode-price-asc"), "price ascending");
            case PRICE_DESCENDING ->
                    new ComboBoxItemDescending(page.locator("button.cl-search-sort-mode-price-desc"), "price descending");
            case NEWEST -> new ComboBoxItem(page.locator("button.cl-search-sort-mode-newest"), "newest");
            case OLDEST -> new ComboBoxItem(page.locator("button.cl-search-sort-mode-oldest"), "oldest");
            case RELEVANCE ->
                    new ComboBoxItem(page.locator("button.cl-search-sort-mode-relevance, button.cl-search-sort-mode-relevant"), "relevance");
            default -> throw new IllegalArgumentException("Unsupported option: " + option);
        };
    }

    public void selectOption(SortOption option) {
        teckro.testlibraries.utils.TestLogger.info("Sorting by " + option);
        openSortDropdown();

        ComboBoxItem item = getItem(option);
        item.waitForVisible();
        item.select();
        page.waitForTimeout(2000);
        page.screenshot(new Page.ScreenshotOptions().setPath(java.nio.file.Paths.get("sort_screenshot.png")));
    }

    public int countArrowOptions() {
        return page.getByText("→", new Page.GetByTextOptions().setExact(false)).count();
    }
}
