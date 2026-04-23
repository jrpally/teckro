package teckro.testlibraries.businesslogic;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.microsoft.playwright.Locator;

import teckro.testlibraries.controls.WebControl;

public class ItemForSale extends WebControl {
    
    private static final String SELECTOR_TITLE = ".posting-title .label";
    private static final String SELECTOR_PRICE = ".priceinfo";

    public ItemForSale(Locator locator) {
        super(locator);
    }

    public String getItemName() {
        return locator.locator(SELECTOR_TITLE).innerText().trim();
    }

    public Float getPrice() {
        Locator priceLocator = locator.locator(SELECTOR_PRICE);
        if (priceLocator.count() > 0) {
            String priceText = priceLocator.first().innerText();
            String numericText = priceText.replaceAll("[^0-9.,-]", "").trim();
            if (numericText.isEmpty()) {
                return null;
            }
            try {                
                String langTag = (String) locator.page().evaluate("navigator.language");
                if (langTag == null || langTag.isEmpty()) langTag = "en-US";
                
                if (numericText.matches(".*\\.\\d{3}$")) {
                    langTag = "es-ES";
                }

                NumberFormat format = NumberFormat.getInstance(Locale.forLanguageTag(langTag));
                return format.parse(numericText).floatValue();
            } catch (ParseException e) {
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
        Locator priceLocator = locator.locator(SELECTOR_PRICE);
        if (priceLocator.count() > 0) {
            String priceText = priceLocator.first().innerText();
            return priceText.replaceAll("[0-9.,]", "").trim();
        }
        return "";
    }
}
