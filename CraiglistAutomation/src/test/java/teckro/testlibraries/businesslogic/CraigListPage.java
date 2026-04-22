package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Browser;
import teckro.testlibraries.browser.BrowserEngine;

import java.net.URI;

public class CraigListPage {

    private final Browser browser;

    public CraigListPage(Browser browser) {
        this.browser = browser;
    }

    public void goHome(String location) {
        this.browser.newPage().navigate(new URI("https", location + ".craigslist.org", null, null).toString());
    }
}
