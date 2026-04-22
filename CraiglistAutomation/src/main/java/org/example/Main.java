package org.example;
import com.microsoft.playwright.*;
import teckro.testlibraries.browser.BrowserEngine;
import teckro.testlibraries.browser.BrowserFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        try (Playwright playwright = Playwright.create()) {
            BrowserEngine browserType = BrowserEngine.CHROME;
            Browser browser = BrowserFactory
                    .createBrowser(playwright, browserType);
//            Browser browser = playwright.chromium().launch(
//                    new BrowserType.LaunchOptions()
//                            .setHeadless(false)   // 👈 show browser
//                            .setSlowMo(100)       // 👈 optional: slow actions so you can see them
//            );

            BrowserContext context = browser.newContext(
                    new Browser.NewContextOptions()
                            .setViewportSize(null) // 👈 open full-size window
            );

            Page page = context.newPage();
            page.navigate("https://madrid.craigslist.org");

//            CraigListPage craigListPage = new CraigListPage(browser);
//            CraigListAddsSections addSections = craigListPage.goHome('madrid');
//            CraigListSection section = addSections.find<Vivienda>();
//            section.click();





            teckro.testlibraries.utils.TestLogger.info(page.title());
        }
    }
}
