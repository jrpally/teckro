package teckro.testlibraries.businesslogic;

import java.util.List;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class CraigListSalePanelSortButton {
    private final Page page;

    public CraigListSalePanelSortButton(Page page) {
        this.page = page;
    }

    public void openSortDropdown() {
        Locator sortButton = page.locator(".cl-search-sort-mode button").first();
        if (sortButton.count() == 0) {
            sortButton = page.locator(".bd-button.cl-search-sort-mode-newest").first();
        }
        sortButton.click();
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
        page.waitForTimeout(2000); // 2 second delay to guarantee sorting occurs
        page.screenshot(new com.microsoft.playwright.Page.ScreenshotOptions().setPath(java.nio.file.Paths.get("sort_screenshot.png")));
    }

    public int countArrowOptions() {
        return page.getByText("→", new Page.GetByTextOptions().setExact(false)).count();
    }
}
