import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class OpenTrendyol {

    WebDriver driver;

    @Step("OpenTrendyol")
    public void openTrendyol() {
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.trendyol.com/");
        WebElement closeButton = driver.findElement(By.xpath("//*[name()='svg' and @width='13px' and @height='13px']"));
        closeButton.click();

        WebElement navbarLoginButton = driver.findElement(By.xpath("//p[contains(text(),'Giriş Yap')]"));
        navbarLoginButton.click();

        WebElement emailInput = driver.findElement(By.xpath("//input[@id='login-email']"));
        emailInput.sendKeys("*");

        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='login-password-input']"));
        passwordInput.sendKeys("*");

        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();

        WebElement logo = driver.findElement(By.xpath("//img[@alt='Trendyol']"));

        if (logo.isDisplayed()) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed.");
        }

    }
}