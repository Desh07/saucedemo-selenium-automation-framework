package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.CartPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import utils.ScreenshotUtil;
import utils.UserDataProvider;

public class CartTests extends BaseTest {

    @Test(
            dataProvider = "users",
            dataProviderClass = UserDataProvider.class
    )
    public void addItemToCartTest(String username) {

        test = extent.createTest(
                "Add To Cart Test - " + username
        );

        LoginPage loginPage = new LoginPage(driver);

        InventoryPage inventoryPage =
                loginPage.login(username, "secret_sauce");

        test.info("User logged in successfully");

        inventoryPage.addBackpackToCart();
        test.info("Backpack added to cart");

        CartPage cartPage = inventoryPage.openCart();

        Assert.assertFalse(cartPage.isCartEmpty());

        test.pass("Cart contains item successfully");
    }

    @Test
    public void removeItemFromCartTest() {

        test = extent.createTest("Remove Item From Cart Test");

        LoginPage loginPage = new LoginPage(driver);

        InventoryPage inventoryPage =
                loginPage.login("standard_user", "secret_sauce");

        inventoryPage.addBackpackToCart();

        CartPage cartPage = inventoryPage.openCart();

        ScreenshotUtil.takeScreenshot(driver, "cart_with_item");

        cartPage.removeItem();

        ScreenshotUtil.takeScreenshot(driver, "cart_after_removal");

        Assert.assertTrue(cartPage.isCartEmpty());

        test.pass("Item removed and cart is empty");
    }
}