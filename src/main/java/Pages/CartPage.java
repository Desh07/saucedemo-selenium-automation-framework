package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private By cartItem = By.className("cart_item");
    private By removeButton = By.id("remove-sauce-labs-backpack");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCartItemDisplayed() {
        return isVisible(cartItem);
    }

    public void removeItem() {
        click(removeButton);
    }

    public boolean isCartEmpty() {
        return driver.findElements(By.className("cart_item")).isEmpty();
    }
}