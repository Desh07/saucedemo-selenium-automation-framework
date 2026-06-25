package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.InventoryPage;
import Pages.LoginPage;
import utils.ScreenshotUtil;
import utils.UserDataProvider;

public class InventoryTests extends BaseTest {

    private InventoryPage inventoryPage;

    // ---------------- LOGIN ONCE PER TEST ----------------
    @org.testng.annotations.BeforeMethod
    public void login() {
        LoginPage loginPage = new LoginPage(driver);
        inventoryPage = loginPage.login("standard_user", "secret_sauce");
    }

    // ---------------- ADD TO CART ----------------

    @Test
    public void addToCartTest() {

        test = extent.createTest("Add To Cart Test");

        Assert.assertTrue(inventoryPage.isInventoryDisplayed());

        inventoryPage.addBackpackToCart();

        test.info("Product added to cart");

        ScreenshotUtil.takeScreenshot(driver, "add_to_cart");

        Assert.assertTrue(inventoryPage.isCartBadgeVisible());

        test.pass("Cart contains product");
    }

    // ---------------- PRODUCT COUNT ----------------

    @Test(dataProvider = "users", dataProviderClass = UserDataProvider.class)
    public void productCountTest(String username) {

        test = extent.createTest("Product Count Test - " + username);

        int count = inventoryPage.getProductCount();

        test.info("Product count retrieved");

        Assert.assertTrue(count > 0);

        test.pass("Product count validation passed");
    }

    // ---------------- SORT A-Z ----------------

    @Test
    public void sortAZTest() {

        test = extent.createTest("Sort A-Z Test");

        inventoryPage.sortByNameAZ();
        inventoryPage.waitForProductsToRefresh();

        ScreenshotUtil.takeScreenshot(driver, "AZ_sort");

        Assert.assertTrue(inventoryPage.isSortedAZ());
        test.pass("A-Z sorting validated");
    }

    // ---------------- SORT Z-A ----------------

    @Test
    public void sortZATest() {

        test = extent.createTest("Sort Z-A Test");

        inventoryPage.sortByNameZA();
        inventoryPage.waitForProductsToRefresh();

        ScreenshotUtil.takeScreenshot(driver, "ZA_sort");

        Assert.assertTrue(inventoryPage.isSortedZA());
        test.pass("Z-A sorting validated");
    }

    // ---------------- PRICE LOW-HIGH ----------------

    @Test
    public void sortPriceLowHighTest() {

        test = extent.createTest("Sort Price Low-High Test");

        inventoryPage.sortByPriceLowHigh();
        inventoryPage.waitForProductsToRefresh();

        ScreenshotUtil.takeScreenshot(driver, "price_low_high");

        Assert.assertTrue(inventoryPage.isPriceSortedLowToHigh());
        test.pass("Price Low-High validated");
    }

    // ---------------- PRICE HIGH-LOW ----------------

    @Test
    public void sortPriceHighLowTest() {

        test = extent.createTest("Sort Price High-Low Test");

        inventoryPage.sortByPriceHighLow();
        inventoryPage.waitForProductsToRefresh();

        ScreenshotUtil.takeScreenshot(driver, "price_high_low");

        Assert.assertTrue(inventoryPage.isPriceSortedHighToLow());
        test.pass("Price High-Low validated");
    }
}