import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.Step;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BaseTest {
    protected static WebDriver driver;
    protected static JsonNode config;

    static {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            config = objectMapper.readTree(new File("config.json"));
            Logger.info("Config loaded from config.json");
        } catch (IOException e) {
            Logger.error("Failed to load config.json", e);
        }
    }

    @Step("Setup browser")
    public void setupBrowser() {
        if (driver == null) {
            String browser = config.get("browser").asText();
            Logger.info("Setting up browser: " + browser);
            try {
                switch (browser.toLowerCase()) {
                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        driver = new FirefoxDriver();
                        break;
                    case "edge":
                        WebDriverManager.edgedriver().setup();
                        driver = new EdgeDriver();
                        break;
                    case "chrome":
                    default:
                        WebDriverManager.chromedriver().setup();
                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("--incognito");
                        options.addArguments("--disable-notifications");
                        driver = new ChromeDriver(options);
                        break;
                }
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                driver.manage().window().maximize();
                Logger.info("Browser setup complete.");
            } catch (Exception e) {
                Logger.error("Error setting up browser:", e);
            }
        }
    }

    @Step("Navigate to Trendyol")
    public void navigateToTrendyol() {
        Logger.info("Navigating to Trendyol.");
        driver.get("https://www.trendyol.com/");
    }

    @Step("Close pop-up")
    public void closePopup() {
        Logger.info("Closing pop-up.");
        findElementByXpath("closeButton").click();
    }

    protected WebElement findElementByXpath(String key) {
        String xpath = config.get("locators").get(key).asText();
        Logger.debug("Finding element by xpath: " + xpath);
        return driver.findElement(By.xpath(xpath));
    }

    @Step("Tear down")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
            Logger.info("Browser closed.");
        }
    }
}
