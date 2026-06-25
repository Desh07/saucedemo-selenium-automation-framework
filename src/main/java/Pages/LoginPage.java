package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By username = By.id("user-name");
    private By password = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public InventoryPage login(String user, String pass) {

        type(username, user);
        type(password, pass);
        click(loginButton);

        return new InventoryPage(driver);
    }

    public boolean isErrorDisplayed() {
        return isVisible(errorMessage);
    }
}