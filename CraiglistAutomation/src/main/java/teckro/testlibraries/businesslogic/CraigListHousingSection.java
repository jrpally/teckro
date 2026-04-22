package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Browser;

public class CraigListHousingSection extends CraigListSection {
    public CraigListHousingSection(Browser browser) {
        super(browser);
    }

    public CraigListHousingForSale clickForSale() {
        return new CraigListHousingForSale(browser);
    }
}
