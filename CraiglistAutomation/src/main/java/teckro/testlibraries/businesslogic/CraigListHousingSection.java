package teckro.testlibraries.businesslogic;

import teckro.testlibraries.controls.*;

import com.microsoft.playwright.Page;

public class CraigListHousingSection extends CraigListSection {
    public CraigListHousingSection(Page page) {
        super(page);
    }

    public CraigListHousingForSale clickForSale() {
        this.locator = page.locator("a.hhh");
        click();
        waitForVisible("input");
        return new CraigListHousingForSale(page);
    }
}
