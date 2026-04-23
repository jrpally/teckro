package teckro.testlibraries.businesslogic;

import teckro.testlibraries.controls.*;

import com.microsoft.playwright.Locator;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ItemForSale extends WebControl {
    public ItemForSale(Locator locator) {
        super(locator);
    }

    public String getItemName() {
        return locator.locator(".posting-title .label").innerText().trim();
    }

    public Float getPrice() {
        Locator priceLocator = locator.locator(".priceinfo");
        if (priceLocator.count() > 0) {
            String priceText = priceLocator.first().innerText();
            String numericText = priceText.replaceAll("[^0-9.,-]", "").trim();
            if (numericText.isEmpty()) {
                return null;
            }
            try {
                // If it ends with .00 or ,00, maybe strip it. If it has .000 it might be thousand separation.
                // Based on standard locale parsing
                String langTag = (String) locator.page().evaluate("navigator.language");
                if (langTag == null || langTag.isEmpty()) langTag = "en-US";
                
                // Hack fallback: if we are in Spain or seeing .000, force EU locale
                if (numericText.matches(".*\\.\\d{3}$")) {
                    langTag = "es-ES";
                }
                
                java.text.NumberFormat format = java.text.NumberFormat.getInstance(java.util.Locale.forLanguageTag(langTag));
                return format.parse(numericText).floatValue();
            } catch (Exception e) {
                try {
                    return Float.parseFloat(numericText.replace(",", ""));
                } catch (NumberFormatException nfe) {
                    return null;
                }
            }
        }
        return null;
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
