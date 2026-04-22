package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;

public abstract class CraigListSection {
    protected final Browser browser;
    protected Page page;

    public CraigListSection(Browser browser, Page page) {
        this.browser = browser;
        this.page = page;
    }
}
