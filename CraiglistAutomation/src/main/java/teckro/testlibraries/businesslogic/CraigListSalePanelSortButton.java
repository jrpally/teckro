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
        switch (option) {
            case PRICE_ASCENDING:
                return new ComboBoxItemAscending(page.locator("button.cl-search-sort-mode-price-asc"), "price ascending");
            case PRICE_DESCENDING:
                return new ComboBoxItemDescending(page.locator("button.cl-search-sort-mode-price-desc"), "price descending");
            case NEWEST:
                return new ComboBoxItem(page.locator("button.cl-search-sort-mode-newest"), "newest");
            case OLDEST:
                return new ComboBoxItem(page.locator("button.cl-search-sort-mode-oldest"), "oldest");
            case RELEVANCE:
                return new ComboBoxItem(page.locator("button.cl-search-sort-mode-relevance, button.cl-search-sort-mode-relevant"), "relevance");
            default:
                throw new IllegalArgumentException("Unsupported option: " + option);
        }
    }

    public void selectOption(SortOption option) {
        openSortDropdown();

        ComboBoxItem item = getItem(option);
        item.waitForVisible();
        item.select();
    }

    public int countArrowOptions() {
        return page.getByText("→", new Page.GetByTextOptions().setExact(false)).count();
    }
}
