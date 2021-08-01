package BrowserInvoke;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class browser {
    public static WebDriver driver;
    public Properties props;

    public WebDriver browserInvoke() throws IOException {
        props = new Properties();
        FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/data.properties");
        props.load(file);

        //by using this we can set the browser in maven
        //String browserName = System.getProperty("Browser");

        //using properties file
        String browserName = props.getProperty("Browser");

        if (browserName.contains("chrome")) {
            WebDriverManager.chromedriver().setup();
            DesiredCapabilities chrom = DesiredCapabilities.chrome();
            chrom.acceptInsecureCerts();
            ChromeOptions options = new ChromeOptions();
            options.merge(chrom);
            if (browserName.contains("headless"))
                options.addArguments("headless");
            options.addArguments("incognito");
            options.addArguments("start-maximized");
            options.addArguments("--ignore-certificate-errors");
            driver = new ChromeDriver(options);


        } else if (browserName.equals("safari")) {
            driver = new SafariDriver();
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    public static Select select(WebElement location, String text) {
        Select gen = new Select(location);
        gen.selectByVisibleText(text);
        return gen;

    }

    public WebDriverWait Wait(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
        return wait;

    }

    public String getScreenShotPath(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) this.driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationFile = System.getProperty("user.dir") + "/Screenshot/" + testCaseName + ".png";
        FileUtils.copyFile(source, new File(destinationFile));
        return destinationFile;
    }
}
