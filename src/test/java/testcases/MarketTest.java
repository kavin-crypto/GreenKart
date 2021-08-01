package testcases;

import BrowserInvoke.browser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObj.cartPage;
import pageObj.checkoutPage;
import pageObj.lastPage;
import pageObj.mainPage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MarketTest extends browser {
    public WebDriver driver;
    private static final Logger log = LogManager.getLogger(MarketTest.class.getName());
    mainPage mp;
    cartPage cp;
    checkoutPage checkout;
    lastPage lp;
    int selectedLen;


    @BeforeTest
    public void invoke() throws IOException {
        driver = browserInvoke();
        driver.get(props.getProperty("URL"));
        driver.manage().window().fullscreen();
    }

    @Test
    public void VegVeggies() {

        String[] veg = {"Raspberry", "Onion", "Walnuts"};
        log.info("Selecting productNeeded{}", (Object)veg);
        selectedLen = veg.length;
        mp = new mainPage(driver);
        List<WebElement> cart = mp.getProductName();
        int count = 0;
        for (int i = 0; i < cart.size(); i++) {
            String[] name = cart.get(i).getText().split("-");
            String formatted = name[0].trim();
            List product = Arrays.asList(veg);
            if (product.contains(formatted)) {
                log.debug("Incrementing the products");
                mp.getProductIncrement().get(i).click();
                if (formatted.equalsIgnoreCase("Onion")) {
                    mp.getProductDecrement().get(i).click();
                }

                log.debug("Adding to cart");
                mp.getAddToCart().get(i).click();
                count++;
                if (count == product.size()) {
                    break;
                }
            }
        }

    }

    @Test(dependsOnMethods = "VegVeggies")
    public void VegCheckout() {
        cp = mp.getCart();
        int cartLength = cp.getNumProduct().size();
        log.debug("Comparing number of selected product is displayed");
        Assert.assertEquals(selectedLen, cartLength);
        List<WebElement> num = cp.getSumAmount();
        int sum = 0;
        for (int count = 0; count < num.size(); count++) {
            String n = num.get(count).getText();
            int x = Integer.parseInt(n);
            sum = sum + x;
        }
        log.info("Calculating the price of product");
        String sumAmount = Integer.toString(sum);
        String totalAmount = cp.getTotalAmount().getText();
        Assert.assertEquals(totalAmount, sumAmount);
        checkout = cp.getCheckout();
    }

    @Test(dependsOnMethods = "VegCheckout")
    public void VegTableVerifying() {
        log.info("Verifying th table");
        int totala = checkout.getSumTotalPrice().size();
        int sum = 0;
        for (int amount = 0; amount < totala; amount++) {
            String am = checkout.getSumTotalPrice().get(amount).getText();
            int amt = Integer.parseInt(am);
            sum = sum + amt;
        }
        log.debug("Comparing and calculating the total amount");
        String summ = Integer.toString(sum);
        String Tolamt = checkout.getTotalAmount().getText();
        Assert.assertEquals(Tolamt, summ);
    }

    @Test(dependsOnMethods = "VegTableVerifying")
    public void VegDiscount() {
        log.info("Adding promo code: rahulshettyacademy");
        checkout.getPromoCode().sendKeys("rahulshettyacademy");
        checkout.getApply().click();
        String beforeDiscount = checkout.getDiscount().getText();

        checkout.WaitPromoInfo();

        String verify = checkout.getPromoInfo().getText();
        Assert.assertEquals("Code applied ..!", verify);

        String afterDiscount = checkout.getDiscount().getText();
        log.info("Comparing before and after discount price");
        Assert.assertNotEquals(beforeDiscount, afterDiscount);

        lp= checkout.getPlaceOrder();
    }

    @Test(dependsOnMethods = "VegDiscount")
    public void VegDelivery() {
        String country = "Switzerland";
        log.debug("Selecting the delivery location");
        select(lp.getSelectLocation(),country);
        lp.getTC().click();
        lp.getProceed().click();
        String[] last = lp.getMessage().getText().split(",");
        String con = last[0];
        log.info("Verifying the conformation of order");
        Assert.assertEquals("Thank you", con);
        driver.quit();
    }

}
