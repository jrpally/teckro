package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import teckro.testlibraries.browser.BrowserEngine;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

public class CraigListPage {

    private final Browser browser;

    public CraigListPage(Browser browser) {
        this.browser = browser;
    }

    public void goHome(String location) {
        try {
            this.browser.newPage().navigate(new URI("https", location + ".craigslist.org", null, null).toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to open URL: " + location);
        }
    }

    public List<CraigListSection> getCraigListSections() {
        return List.of(new CraigListHousingSection(this.browser));
    }
}
