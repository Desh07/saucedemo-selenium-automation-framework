package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryPage extends BasePage {

    private By inventoryContainer = By.id("inventory_container");
    private By backpackAddBtn = By.id("add-to-cart-sauce-labs-backpack");
    private By cartIcon = By.className("shopping_cart_link");
    private By productItems = By.className("inventory_item");

    private By productNames = By.className("inventory_item_name");
    private By productPrices = By.className("inventory_item_price");
    private By sortDropdown = By.className("product_sort_container");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isInventoryDisplayed() {
        return isVisible(inventoryContainer);
    }

    public void addBackpackToCart() {
        click(backpackAddBtn);
    }

    public int getProductCount() {
        return driver.findElements(productItems).size();
    }

    public boolean isCartBadgeVisible() {
        return driver.findElements(By.className("shopping_cart_badge")).size() > 0;
    }

    public CartPage openCart() {
        click(cartIcon);
        return new CartPage(driver);
    }

    // ---------------- SORT ACTIONS ----------------

    public void sortByNameAZ() {
        Select select = new Select(driver.findElement(sortDropdown));
        select.selectByVisibleText("Name (A to Z)");
    }

    public void sortByNameZA() {
        Select select = new Select(driver.findElement(sortDropdown));
        select.selectByVisibleText("Name (Z to A)");
    }

    public void sortByPriceLowHigh() {
        Select select = new Select(driver.findElement(sortDropdown));
        select.selectByVisibleText("Price (low to high)");
    }

    public void sortByPriceHighLow() {
        Select select = new Select(driver.findElement(sortDropdown));
        select.selectByVisibleText("Price (high to low)");
    }

    // ---------------- SORT VALIDATION ----------------

    public boolean isSortedAZ() {
        return isSortedList(getProductNames(), true);
    }

    public boolean isSortedZA() {
        return isSortedList(getProductNames(), false);
    }

    public boolean isPriceSortedLowToHigh() {
        List<Double> prices = getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        return prices.equals(sorted);
    }

    public boolean isPriceSortedHighToLow() {
        List<Double> prices = getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        sorted.sort(Collections.reverseOrder());
        return prices.equals(sorted);
    }

    // ---------------- HELPERS ----------------

    private List<String> getProductNames() {
        List<WebElement> products = driver.findElements(productNames);

        List<String> names = new ArrayList<>();
        for (WebElement p : products) {
            names.add(p.getText().trim());
        }
        return names;
    }

    private List<Double> getProductPrices() {
        List<WebElement> prices = driver.findElements(productPrices);

        List<Double> priceList = new ArrayList<>();
        for (WebElement p : prices) {
            priceList.add(Double.parseDouble(p.getText().replace("$", "").trim()));
        }
        return priceList;
    }

    private boolean isSortedList(List<String> actual, boolean ascending) {
        List<String> sorted = new ArrayList<>(actual);

        if (ascending) {
            Collections.sort(sorted);
        } else {
            sorted.sort(Collections.reverseOrder());
        }

        return actual.equals(sorted);
    }

    // ---------------- FIXED WAIT METHOD ----------------

    public void waitForProductsToRefresh() {

        handleAlertIfPresent(); // 🔥 IMPORTANT

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNames));

        handleAlertIfPresent(); // optional second check
    }
}