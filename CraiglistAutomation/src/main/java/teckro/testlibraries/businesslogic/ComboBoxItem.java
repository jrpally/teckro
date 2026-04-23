package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Locator;

public class ComboBoxItem extends WebControl {
    private final String text;

    public ComboBoxItem(Locator locator, String text) {
        super(locator);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void select() {
        click();
    }

    @Override
    public String toString() {
        return "ComboBoxItem{text='" + text + "'}";
    }
}
