import com.fasterxml.jackson.databind.JsonNode;
import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.*;
import java.util.List;

public class AddingProductToCart extends BaseTest {
    JsonNode config;

    String productName;

    @Step("Search for <product>")
    public void searchForProduct(String product) {
        WebElement searchInput = findElementByXpath("searchInput");
        searchInput.sendKeys(product);
        findElementByXpath("searchButton").click();
        Logger.info("The products are listed.");
    }

    @Step("Add the first product to the cart")
    public void addFirstProductToCart() {
        findElementByXpath("productList").click();
        Logger.info("First product added to the cart.");
    }

    @Step("Go to cart")
    public void goToCart() {
        Logger.info("Navigating to the cart.");
        findElementByXpath("cartPopupButton").click();
        Logger.info("Cart popup button clicked.");
        findElementByXpath("cartButton").click();
        Logger.info("Navigated to the cart page.");
    }

    @Step("Verify the product in the cart is the one added")
    public void verifyProductInCart() {
        Logger.info("Verifying the product in the cart.");
        List<WebElement> cartProducts = driver.findElements(By.xpath(config.get("locators").get("cartProductList").asText()));

        boolean productFound = false;
        for (WebElement product : cartProducts) {
            String cartProductName = product.getText();
            Logger.info("Checking product: " + cartProductName);
            if (cartProductName.equals(productName)) {
                Logger.info("The correct product is in the cart: " + cartProductName);
                productFound = true;
                break;
            }
        }

        if (!productFound) {
            Logger.error("The product was not found in the cart.");
        }
    }

    @Step("Checkout the cart")
    public void checkoutCart() {
        Logger.info("Proceeding to checkout.");
        findElementByXpath("checkoutButton").click();
        Logger.info("Checkout button clicked.");
    }
}
