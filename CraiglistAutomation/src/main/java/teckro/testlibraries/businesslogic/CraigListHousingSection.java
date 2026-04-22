package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.Page;

public class CraigListHousingSection extends CraigListSection {
    public CraigListHousingSection(Browser browser, Page page) {
        super(browser, page);
    }

    public CraigListHousingForSale clickForSale() {
        page.navigate("https://madrid.craigslist.org/search/hhh");
        page.waitForSelector("input", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(20000));
        return new CraigListHousingForSale(browser, page);
    }
}
