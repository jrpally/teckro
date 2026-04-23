package teckro.testlibraries.businesslogic;

import teckro.testlibraries.controls.*;

import com.microsoft.playwright.Page;

public class CraigListHousingForSale extends CraigListSection {
    public CraigListHousingForSale(Page page) {
        super(page);
    }

    public CraigListSalePanel getCraigListSalePanel() {
        return new CraigListSalePanel(this.page);
    }
}
