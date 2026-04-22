package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Browser;

public abstract class CraigListSection {
    protected final Browser browser;

    public CraigListSection(Browser browser) {
        this.browser = browser;
    }
}
