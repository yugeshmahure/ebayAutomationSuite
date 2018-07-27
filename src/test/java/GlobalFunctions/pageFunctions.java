package GlobalFunctions;


import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Locators.ANObjectRepo;
import utility.UtilityFunctions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class pageFunctions extends ANObjectRepo
{
    public AndroidDriver driver;
    public WebDriverWait wait;
    public static Properties testData;
    Logger logger = Logger.getLogger("ebayLogger");
// Constructor to initialize driver object
public pageFunctions (AndroidDriver driver,WebDriverWait wait)
{
    this.driver = driver;
    this.wait = wait;
}
//Method to load properties file
public static void initProp()
{
    String path = "./src/main/resources/testData.properties";
    testData = UtilityFunctions.loadPropFile(path);
}
/*This is method is used to scroll down vertically
* Parameter: perct : how much scroll you want to do
*
* */
public void scrollDown(float perct) throws InterruptedException {
    Thread.sleep(3000);
    TouchAction ta = new TouchAction(driver);
    Dimension dimensions = driver.manage().window().getSize();
    float screenWidth = dimensions.getWidth();
    float screenHeight = dimensions.getHeight();
    float x = screenWidth/2;
    float y = screenHeight/1.2f;
    float y1 = screenHeight - (screenHeight * perct);
    ta.press(PointOption.point((int)x,(int)y)).moveTo(PointOption.point((int)x,(int)y1)).release().perform();
}
/*This method is used for login and it validates whether user has logged successfully or not*/
public boolean login() throws InterruptedException {
    initProp();
    boolean flag = false;
    driver.findElement(By.xpath(home)).click();
    driver.findElement(By.xpath(signInMenu)).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(username)));
    driver.findElement(By.xpath(username)).sendKeys(testData.getProperty("username"));
    driver.findElement(By.xpath(password)).sendKeys(testData.getProperty("password"));
    try {
    driver.findElement(By.xpath(signInButton)).click();
    driver.findElement(By.xpath(NotNowButton)).click();
    driver.findElement(By.xpath(home)).click();
    String signInText = driver.findElement(By.xpath(signInMenu)).getText();

    }
    catch (Exception e){
        logger.debug("Login successful");
        flag = true;
    }
    driver.findElement(By.xpath(home)).click();
    return flag;
}
/*This method is used to search for item mentioned in property file*/
public boolean searchItem() throws InterruptedException {
    initProp();
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(globalSearchBox)));
    driver.findElement(By.xpath(globalSearchBox)).click();
    driver.findElement(By.xpath(homeSearchBox)).sendKeys(testData.getProperty("searchItem"));
    logger.debug("Searching for item");
    Thread.sleep(4000);
    List<WebElement> listOfElements = driver.findElements(By.xpath(searchList));
    listOfElements.get(3).click();
    //driver.findElement(By.xpath(firstSearchSuggItem)).click();
    String selectedItem = driver.findElement(By.xpath(fifthSearchResultItem)).getText();
    driver.findElement(By.xpath(fifthSearchResultItem)).click();
    logger.debug("Clicked on item to navigate");
    driver.findElement(By.xpath(buyitnowButton)).click();
    try {
        String itemDescPage = driver.findElement(By.xpath(descPageItemTitle)).getText();

        if (itemDescPage.contains(testData.getProperty("searchItem"))) {
            logger.debug("item is searched");
            return true;
        } else {
            logger.debug("not able to search the item");
            return false;
        }
    }
    catch (Exception e)
    {
        logger.debug("No review page is available");
        if(driver.findElement(By.xpath(orderSummary)).getText().contains("Order"))
            return true;
        else
            return false;
    }
}

/*This method is used to click on Review button*/
public boolean clickOnReview()
{
    boolean flag = false;
    try {
        flag = driver.findElement(By.xpath(reviewButton)).isDisplayed();
        driver.findElement(By.xpath(reviewButton)).click();
    }
    catch (Exception e){
     flag = true;
    }
    return flag;
}
/*This method is used to navigate to checkout page*/
public boolean proceedToPay() throws InterruptedException
{
    logger.debug("Scrolling down....");
    scrollDown(0.80f);
    driver.findElement(By.xpath(proceedToPay)).click();
    String paymentmode = driver.findElement(By.xpath(paymenentModeButton)).getText();
    if(paymentmode.contains("payment")) {
        logger.debug("Navigated to payment page successfully");
        return true;
    }
    else {
        logger.debug("Navigation to payment page unsuccessfully");
        return false;
    }
}

}
