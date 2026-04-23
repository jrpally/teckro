package teckro.testlibraries.controls;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public abstract class WebControl {
    protected Page page;
    protected Locator locator;

    public WebControl(Page page) {
        this.page = page;
    }

    public WebControl(Locator locator) {
        this.page = locator.page();
        this.locator = locator;
    }

    public Browser getBrowser() {
        return page.context().browser();
    }

    public void waitForVisible(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(20000));
    }

    public void waitForVisible() {
        if (locator != null) {
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        }
    }

    public void click() {
        if (locator != null) {
            locator.click();
        }
    }
}
