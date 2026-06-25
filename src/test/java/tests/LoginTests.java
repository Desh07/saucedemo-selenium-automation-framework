package tests;

import base.BaseTest;
import Pages.LoginPage;
import Pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ScreenshotUtil;
import utils.UserDataProvider;

public class LoginTests extends BaseTest {

    @Test(
            dataProvider = "users",
            dataProviderClass = UserDataProvider.class
    )
    public void validLoginTest(String username) {

        test = extent.createTest("Valid Login Test - " + username);

        LoginPage loginPage = new LoginPage(driver);

        System.out.println("BEFORE LOGIN: " + driver.getCurrentUrl());

        InventoryPage inventoryPage =
                loginPage.login(username, "secret_sauce");

        test.info("Logging in with user: " + username);

        System.out.println("AFTER LOGIN: " + driver.getCurrentUrl());

        Assert.assertTrue(inventoryPage.isInventoryDisplayed());

        test.pass(username + " landed on Inventory page successfully");
    }

    @Test
    public void invalidLoginTest() {

        test = extent.createTest("Invalid Login Test");

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("standard_user", "wrong_password");

        test.info("Logging in with invalid credentials");

        ScreenshotUtil.takeScreenshot(driver, "invalid_login_test");

        Assert.assertTrue(loginPage.isErrorDisplayed());

        test.pass("Error message displayed correctly");
    }

    @Test
    public void emptyLoginTest() {

        test = extent.createTest("Empty Login Test");

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("", "");

        test.info("Submitted empty credentials");

        ScreenshotUtil.takeScreenshot(driver, "empty_login");

        Assert.assertTrue(loginPage.isErrorDisplayed());

        test.pass("Empty login validation passed");
    }
}