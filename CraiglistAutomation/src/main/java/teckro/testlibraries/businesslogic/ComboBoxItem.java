package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Locator;

public class ComboBoxItem {
    private final Locator locator;
    private final String text;

    public ComboBoxItem(Locator locator, String text) {
        this.locator = locator;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void waitForVisible() {
        locator.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
    }

    public void select() {
        locator.click();
    }

    @Override
    public String toString() {
        return "ComboBoxItem{text='" + text + "'}";
    }
}
