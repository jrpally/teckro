package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;

public class CraigListSalePanel {
    private final Page page;
    private static final Pattern PRICE_PATTERN = Pattern.compile("[\u00A3\u20AC\\$]\u00A0?([0-9]{1,3}(?:[.,][0-9]{3})*(?:[.,][0-9]+)?)");

    public CraigListSalePanel(Page page) {
        this.page = page;
    }

    public CraigListSalePanelSortButton getCraigListPanelSortButton() {
        return new CraigListSalePanelSortButton(page);
    }

    public SearchBox getSearchBox() {
        return new SearchBox(page);
    }

    public CraigListSalePanel search(String query) {
        getSearchBox().search(query);
        return this;
    }

    public List<ItemForSale> getItemsForSale() {
        page.waitForSelector(".gallery-card", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
        return page.locator(".gallery-card").all()
            .stream()
            .map(ItemForSale::new)
            .toList();
    }

    public static boolean isSorted(List<Float> values, BiPredicate<Float, Float> predicate) {
        if (values.isEmpty()) return true;
        Float prev = values.get(0);
        for (int i = 1; i < values.size(); i++) {
            Float current = values.get(i);
            if (!predicate.test(prev, current)) return false;
            prev = current;
        }
        return true;
    }
}
