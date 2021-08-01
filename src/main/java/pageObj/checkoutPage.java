package pageObj;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class checkoutPage {

    WebDriver driver;

    public checkoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    By totalPrice = By.xpath("//*[contains(@class,'products')]//tbody/tr/td[5]/p");
    By totalAmount = By.xpath("//*[contains(@class,'totAmt')]");
    @FindBy(xpath = "//*[contains(@class,'promoCode')]")
    WebElement promoCode;
    @FindBy(xpath = "//*[contains(@class,'promoBtn')]")
    WebElement apply;
    @FindBy(xpath = "//*[contains(@class,'discountAmt')]")
    WebElement discount;
    @FindBy(xpath = "//*[contains(@class,'promoInfo')]")
    WebElement promoInfo;
    @FindBy(xpath = "//*[contains(text(),'Place Order')]")
    WebElement placeOrder;


    public List<WebElement> getSumTotalPrice() {
        return driver.findElements(totalPrice);
    }

    public WebElement getTotalAmount() {
        return driver.findElement(totalAmount);
    }

    public WebElement getPromoCode(){
        return promoCode;
    }
    public WebElement getApply(){
        return apply;
    }

    public WebElement getDiscount(){
        return discount;
    }

    public WebElement getPromoInfo(){
        return promoInfo;
    }

    public lastPage getPlaceOrder() {
        placeOrder.click();
        lastPage lp = new lastPage(driver);
        return lp;
    }

    public WebElement WaitPromoInfo()
    {
        WebDriverWait wait=new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'promoInfo')]")));
        return promoInfo;
    }

}
