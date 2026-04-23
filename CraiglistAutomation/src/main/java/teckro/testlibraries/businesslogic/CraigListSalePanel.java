package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import teckro.testlibraries.controls.SearchBox;

import java.util.List;
import java.util.function.BiPredicate;

public class CraigListSalePanel {
    private static final String SELECTOR_GALLERY_CARD = ".gallery-card";
    private final Page page;

    public CraigListSalePanel(Page page) {
        this.page = page;
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
        page.waitForSelector(SELECTOR_GALLERY_CARD, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
        return page.locator(SELECTOR_GALLERY_CARD).all()
                .stream()
                .map(ItemForSale::new)
                .toList();
    }
}
