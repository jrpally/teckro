package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class SearchBox extends WebControl {

    public SearchBox(Page page) {
        super(page.locator("input:visible").first());
    }

    public void search(String query) {
        locator.fill("");
        locator.type(query);
        locator.press("Enter");
        page.waitForLoadState();
        page.waitForSelector("li", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
    }
}
