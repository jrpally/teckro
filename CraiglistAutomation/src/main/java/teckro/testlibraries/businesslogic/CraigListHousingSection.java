package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.Page;

public class CraigListHousingSection extends CraigListSection {
    public CraigListHousingSection(Browser browser, Page page) {
        super(browser, page);
    }

    public CraigListHousingForSale clickForSale() {
        this.locator = page.locator("a.hhh");
        click();
        waitForVisible("input");
        return new CraigListHousingForSale(browser, page);
    }
}
