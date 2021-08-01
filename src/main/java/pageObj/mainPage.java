package pageObj;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class mainPage {


    WebDriver driver;
    public mainPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver ,this);
    }

    By productName= By.xpath("//*[contains(@class,'product')]/h4");
    By productIncrement= By.xpath("//*[contains(@class,'product')]//a[2]");
    By productDecrement= By.xpath("//*[contains(@class,'product')]//a[1]");
    By addToCart= By.xpath("//*[contains(@class,'product')]/button");

    @FindBy(xpath = "//*[@alt='Cart']")
    WebElement cart;

    public List<WebElement> getProductName(){
        return driver.findElements(productName);
    }

    public List<WebElement> getProductIncrement(){
        return driver.findElements(productIncrement);
    }

    public List<WebElement> getProductDecrement(){
        return driver.findElements(productDecrement);
    }

    public List<WebElement> getAddToCart(){
        return driver.findElements(addToCart);
    }


    public cartPage getCart() {
       cart.click();
        cartPage cp = new cartPage(driver);
        return cp;
    }

}
