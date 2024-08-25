import com.thoughtworks.gauge.Step;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class OpenTrendyol {

    WebDriver driver;
    Properties locators;

    public OpenTrendyol() throws IOException {
        locators = new Properties();
        FileInputStream locatorsFile = new FileInputStream("locators.properties");
        locators.load(locatorsFile);
    }
    @Step("Setup browser")
    public void setupBrowser() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Step("Navigate to Trendyol")
    public void navigateToTrendyol() {
        driver.get("https://www.trendyol.com/");
    }

    @Step("Close pop-up")
    public void closePopup() {
        WebElement closeButton = findElementByXpath("closeButton");
        closeButton.click();
    }

    @Step("Click login button on navbar")
    public void clickLoginButton() {
        WebElement navbarLoginButton = driver.findElement(By.xpath("//p[contains(text(),'Giriş Yap')]"));
        navbarLoginButton.click();
    }

    @Step("Enter email and password")
    public void enterCredentials() throws IOException {
        Properties properties = new Properties();
        FileInputStream file = new FileInputStream("config.properties");
        properties.load(file);

        String email = properties.getProperty("email");
        String password = properties.getProperty("password");

        WebElement emailInput = findElementByXpath("emailInput");
        emailInput.sendKeys(email);

        WebElement passwordInput = findElementByXpath("passwordInput");
        passwordInput.sendKeys(password);
    }

    @Step("Submit login form")
    public void submitLogin() {
        WebElement loginButton = findElementByXpath("loginButton");
        loginButton.click();
    }

    @Step("Verify login successful")
    public void verifyLogin() {
        WebElement logo = findElementByXpath("logo");
        if (logo.isDisplayed()) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed.");
        }
        driver.quit();
    }

    public WebElement findElementByXpath(String key) {
        return driver.findElement(By.xpath(locators.getProperty(key)));
    }
}