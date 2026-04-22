package teckro.testlibraries.browser;

import com.microsoft.playwright.Browser;

public class WebPage {
    protected final Browser browser;

    public WebPage(Browser browser) {
        this.browser = browser;
    }
}
