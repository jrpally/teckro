package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public abstract class WebControl {
    protected final Browser browser;
    protected Page page;
    protected com.microsoft.playwright.Locator locator;

    public WebControl(Browser browser, Page page) {
        this.browser = browser;
        this.page = page;
    }

    public WebControl(Browser browser, Page page, Locator locator) {
        this.browser = browser;
        this.page = page;
        this.locator = locator;
    }

    public WebControl(Locator locator) {
        this.browser = null;
        this.page = locator.page();
        this.locator = locator;
    }

    public void waitForVisible(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(20000));
    }

    public void waitForVisible() {
        if (locator != null) {
            locator.waitFor(new com.microsoft.playwright.Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        }
    }

    public void click() {
        if (locator != null) {
            locator.click();
        }
    }
}
