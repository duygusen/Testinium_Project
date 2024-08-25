import com.thoughtworks.gauge.Step;
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

    @Step("Setup browser")
    public void setupBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");
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
        WebElement closeButton = driver.findElement(By.xpath("//*[name()='svg' and @width='13px' and @height='13px']"));
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

        WebElement emailInput = driver.findElement(By.xpath("//input[@id='login-email']"));
        emailInput.sendKeys(email);

        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='login-password-input']"));
        passwordInput.sendKeys(password);
    }

    @Step("Submit login form")
    public void submitLogin() {
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();
    }

    @Step("Verify login successful")
    public void verifyLogin() {
        WebElement logo = driver.findElement(By.xpath("//img[@alt='Trendyol']"));
        if (logo.isDisplayed()) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed.");
        }
        driver.quit();
    }
}