package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import teckro.testlibraries.browser.WebPage;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class CraigListPage extends WebPage {

    public CraigListPage(Browser browser) {
        super(browser);
    }

    public void goHome(String location) {
        try {
            browser.newPage().navigate(new URI("https", location + ".craigslist.org", null, null).toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to open URL: " + location);
        }
    }

    public List<CraigListSection> getCraigListSections() {
        return List.of(new CraigListHousingSection(this.browser));
    }
}
