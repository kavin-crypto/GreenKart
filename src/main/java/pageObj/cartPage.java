package pageObj;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class cartPage {
    WebDriver driver;

    public cartPage(WebDriver driver) {
        this.driver = driver;
    }

    By numProduct = By.xpath("//*[@class='cart-preview active']/div//li");
    By sumAmount = By.xpath("//*[@class='cart-preview active']/div//li/div[2]/p[2]");
    By totalAmount = By.xpath("//*[@class='cart-info']//tr[2]/td/strong");
    By checkout = By.xpath("//*[contains(text(),'PROCEED TO CHECKOUT')]");


    public List<WebElement> getNumProduct() {
        return driver.findElements(numProduct);
    }

    public List<WebElement> getSumAmount() {
        return driver.findElements(sumAmount);
    }

    public WebElement getTotalAmount() {
        return driver.findElement(totalAmount);
    }

    public checkoutPage getCheckout() {
        driver.findElement(checkout).click();
        checkoutPage checkout = new checkoutPage(driver);
        return checkout;

    }
}
