package pageObj;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class lastPage {

    WebDriver driver;

    public lastPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//*[contains(@class,'wrapperTwo')]//select")
    WebElement selectLocation;
    @FindBy(xpath = "//*[contains(@class,'chkAgree')]")
    WebElement TC;
    @FindBy(xpath = "//*[contains(text(),'Proceed')]")
    WebElement proceed;
    @FindBy(xpath = "//*[contains(@class,'wrapperTwo')]/span")
    WebElement msg;

    public WebElement getSelectLocation(){
        return selectLocation;
    }
    public WebElement getTC(){
        return TC;
    }
    public WebElement getProceed(){
        return proceed;
    }
    public WebElement getMessage(){
        return msg;
    }
}
