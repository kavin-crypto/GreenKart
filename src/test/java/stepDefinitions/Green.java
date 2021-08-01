package stepDefinitions;

import BrowserInvoke.browser;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageObj.cartPage;
import pageObj.checkoutPage;
import pageObj.lastPage;
import pageObj.mainPage;
import testcases.MarketTest;

import java.util.Arrays;
import java.util.List;

public class Green extends browser {
    private static final Logger log = LogManager.getLogger(MarketTest.class.getName());
    mainPage mp;
    cartPage cp;
    checkoutPage checkout;
    lastPage lp;
    String beforeDiscount;
    int selectedLen;

    @Given("^Invoking the browser with chrome and navigate to \"([^\"]*)\"$")
    public void invoking_the_browser_with_chrome_and_navigate_to_something(String url) throws Throwable {
        driver = browserInvoke();
        driver.get(url);
        driver.manage().window().fullscreen();
    }

    @When("^Enter the product needed and add to cart$")
    public void enter_the_product_needed_and_add_to_cart() throws Throwable {
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
        cp = mp.getCart();
    }

    @And("^Comparing number of selected product is displayed$")
    public void comparing_number_of_selected_product_is_displayed() throws Throwable {
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
    @Then("^Click on checkout$")
    public void click_on_checkout() throws Throwable {
        checkout = cp.getCheckout();
    }
    @When("^Comparing and calculating the total amount$")
    public void comparing_and_calculating_the_total_amount() throws Throwable {
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

    @And("^Add promo code and verify it$")
    public void add_promo_code_and_verify_it() throws Throwable {
        log.info("Adding promo code: rahulshettyacademy");
        checkout.getPromoCode().sendKeys("rahulshettyacademy");
        checkout.getApply().click();
        beforeDiscount = checkout.getDiscount().getText();

        checkout.WaitPromoInfo();

        String verify = checkout.getPromoInfo().getText();
        Assert.assertEquals("Code applied ..!", verify);

    }

    @And("^Compare the total amount before and after the promo code applied$")
    public void compare_the_total_amount_before_and_after_the_promo_code_applied() throws Throwable {
        String afterDiscount = checkout.getDiscount().getText();
        log.info("Comparing before and after discount price");
        Assert.assertNotEquals(beforeDiscount, afterDiscount);
    }

    @Then("^Click on Place order$")
    public void click_on_place_order() throws Throwable {
        lp= checkout.getPlaceOrder();
    }

    @When("^User should enter country \"([^\"]*)\"$")
    public void user_should_enter_country_something(String strArg1) throws Throwable {
        String country = strArg1;
        log.debug("Selecting the delivery location");
        select(lp.getSelectLocation(),country);
    }

    @Then("^Click to proceed$")
    public void click_to_proceed() throws Throwable {
        lp.getTC().click();
        lp.getProceed().click();
    }

    @And("^Verify the conformation of order$")
    public void verify_the_conformation_of_order() throws Throwable {
        String[] last = lp.getMessage().getText().split(",");
        String con = last[0];
        log.info("Verifying the conformation of order");
        Assert.assertEquals("Thank you", con);
        driver.quit();
    }




}
