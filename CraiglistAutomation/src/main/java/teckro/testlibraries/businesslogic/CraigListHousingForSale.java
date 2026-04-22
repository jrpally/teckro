package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;

public class CraigListHousingForSale extends CraigListSection {
    public CraigListHousingForSale(Browser browser, Page page) {
        super(browser, page);
    }

    public CraigListSalePanel getCraigListSalePanel() {
        return new CraigListSalePanel(this.page);
    }
}
