package GlobalFunctions;


import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Locators.ANObjectRepo;
import utility.UtilityFunctions;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;


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

public pageFunctions (AndroidDriver driver)
{
    this.driver = driver;
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
    Thread.sleep(5000);
    TouchAction ta = new TouchAction(driver);
    Dimension dimensions = driver.manage().window().getSize();
    float screenWidth = dimensions.getWidth();
    float screenHeight = dimensions.getHeight();
    float x = screenWidth/2;
    float y = screenHeight/1.2f;
    float y1 = screenHeight - (screenHeight * perct);
    ta.press(PointOption.point((int)x,(int)y)).waitAction().moveTo(PointOption.point((int)x,(int)y1)).release().perform();
}


    /*This is method is used to swipe horizontally from right to left
     * Parameter: perct : how much scroll you want to do
     *
     * */
    public void swipeRight(float perct) throws InterruptedException {
        Thread.sleep(5000);
        TouchAction ta = new TouchAction(driver);
        Dimension dimensions = driver.manage().window().getSize();
        float screenWidth = dimensions.getWidth();
        float screenHeight = dimensions.getHeight();
        float x = screenWidth/1.2f;
        float y = screenHeight/2;
        float x1 = screenHeight - (screenWidth * perct);
        ta.press(PointOption.point((int)x,(int)y)).waitAction().moveTo(PointOption.point((int)x1,(int)y)).release().perform();
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
    String searchItem = testData.getProperty("searchItem");
    String xpath = "//*[contains(@text,'"+searchItem+"')]";
    List<WebElement> listOfElements = driver.findElements(By.xpath(xpath));

    listOfElements.get(listOfElements.size()-1).click();
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
    Thread.sleep(4000);

    Set contextNames = driver.getContextHandles();
    Iterator<String> itr=contextNames.iterator();

    while(itr.hasNext()){
        System.out.println(itr.next());
        if(itr.next().equalsIgnoreCase("WEBVIEW"))
        {
            logger.debug("Switching to webview");
            driver.context(itr.next());
        }
    }
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

public void getScreenshot() throws IOException {
    File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(scrFile, new File("./src/Screenshot_"+String.valueOf(System.currentTimeMillis()) +".png"));
}
}
