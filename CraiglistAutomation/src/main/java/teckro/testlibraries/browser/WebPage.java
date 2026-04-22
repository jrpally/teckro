package teckro.testlibraries.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;

public class WebPage {
    protected final Browser browser;
    protected Page page;

    public WebPage(Browser browser) {
        this.browser = browser;
    }

    public Page getPage() {
        if (page == null) {
            page = browser.newPage();
        }
        return page;
    }
}
