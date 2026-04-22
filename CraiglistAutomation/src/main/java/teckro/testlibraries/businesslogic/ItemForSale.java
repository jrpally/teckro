package teckro.testlibraries.businesslogic;

import com.microsoft.playwright.Locator;

public class ItemForSale {
    private final Locator locator;

    public ItemForSale(Locator locator) {
        this.locator = locator;
    }

    public String getItemName() {
        return locator.locator(".posting-title .label").innerText().trim();
    }

    public String getPrice() {
        Locator priceLocator = locator.locator(".priceinfo");
        if (priceLocator.count() > 0) {
            String priceText = priceLocator.first().innerText();
            return priceText.replaceAll("[^0-9]", "");
        }
        return "";
    }

    public String getCurrency() {
        Locator priceLocator = locator.locator(".priceinfo");
        if (priceLocator.count() > 0) {
            String priceText = priceLocator.first().innerText();
            return priceText.replaceAll("[0-9.,]", "").trim();
        }
        return "";
    }
}
