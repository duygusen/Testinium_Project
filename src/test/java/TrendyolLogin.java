import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.WebElement;

public class TrendyolLogin extends BaseTest {

    public TrendyolLogin() {
        Logger.info("TrendyolLogin test initiated.");

    }

    @Step("Click login button on navbar")
    public void clickLoginButton() {
        Logger.info("Clicking login button on navbar.");
        findElementByXpath("navbarLoginButton").click();
    }

    @Step("Enter email <email> and password <password>")
    public void enterCredentials(String email, String password) {
        Logger.info("Entering credentials.");
        WebElement emailInput = findElementByXpath("emailInput");
        emailInput.sendKeys(email);

        WebElement passwordInput = findElementByXpath("passwordInput");
        passwordInput.sendKeys(password);
    }

    @Step("Submit login form")
    public void submitLogin() {
        Logger.info("Submitting login form.");
        findElementByXpath("loginButton").click();
    }

    @Step("Verify login successful")
    public void verifyLogin() {
        Logger.info("Verifying login.");
        if (findElementByXpath("logo").isDisplayed()) {
            Logger.info("Login successful!");
        } else {
            Logger.error("Login failed.");
        }
    }

}