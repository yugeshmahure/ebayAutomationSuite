package GlobalFunctions;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
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
import org.testng.Reporter;
import utility.UtilityFunctions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;


public class pageFunctions extends ANObjectRepo
{
    public AndroidDriver driver;
    public WebDriverWait wait;
    public static Properties testData;
    ExtentTest logger;
// Constructor to initialize driver object
public pageFunctions (AndroidDriver driver,WebDriverWait wait,ExtentTest logger)
{
    this.driver = driver;
    this.wait = wait;
    this.logger = logger;
}

public pageFunctions (AndroidDriver driver)
{
    this.driver = driver;
}
public pageFunctions ()
{
    System.out.println("");
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
public boolean login() throws InterruptedException, IOException {
    initProp();
    boolean flag = false;
    driver.findElement(home).click();
    driver.findElement(signInMenu).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(username));
    driver.findElement(username).sendKeys(testData.getProperty("username"));
    logger.log(LogStatus.INFO,"Username is entered");
    driver.findElement(password).sendKeys(testData.getProperty("password"));
    logger.log(LogStatus.INFO,"Password is entered");
    try {
    driver.findElement(signInButton).click();
    logger.log(LogStatus.INFO,"Clicked on sign in button");
    driver.findElement(NotNowButton).click();
    driver.findElement(home).click();
    String signInText = driver.findElement(signInMenu).getText();
    logger.log(LogStatus.FAIL,"User is logged in successfully");
    }
    catch (Exception e){
        logger.log(LogStatus.PASS,"User is logged in successfully");
        //logger.log(LogStatus.PASS,logger.addScreencast(getScreenshot()));
        logger.log(LogStatus.PASS,logger.addScreenCapture(getScreenshot()));
        flag = true;
    }
    driver.findElement(home).click();
    return flag;
}
/*This method is used to search for item mentioned in property file*/
public boolean searchItem() throws InterruptedException, IOException {
    initProp();
    wait.until(ExpectedConditions.elementToBeClickable(globalSearchBox));
    driver.findElement(globalSearchBox).click();
    driver.findElement(homeSearchBox).sendKeys(testData.getProperty("searchItem"));
    logger.log(LogStatus.INFO,"Searching for item");
    Thread.sleep(4000);
    String searchItem = testData.getProperty("searchItem");
    List<WebElement> listOfElements = driver.findElements(setXpath(searchItem));
    listOfElements.get(listOfElements.size()-1).click();
    //driver.findElement(By.xpath(firstSearchSuggItem)).click();
    String selectedItem = driver.findElement(fifthSearchResultItem).getText();
    driver.findElement(fifthSearchResultItem).click();
    logger.log(LogStatus.PASS,"Search result list "+logger.addScreenCapture(getScreenshot()));
    logger.log(LogStatus.INFO,"Clicked on item to navigate");
    driver.findElement(buyitnowButton).click();
    try {
        String itemDescPage = driver.findElement(descPageItemTitle).getText();

        if (itemDescPage.contains(testData.getProperty("searchItem"))) {
            logger.log(LogStatus.PASS,"Item is searched");
            return true;
        } else {
            logger.log(LogStatus.FAIL,"Not able to search the item");
            return false;
        }
    }
    catch (Exception e)
    {
        logger.log(LogStatus.INFO,"No review page is available");
        logger.log(LogStatus.PASS,"Item description page "+logger.addScreenCapture(getScreenshot()));
        if(driver.findElement(orderSummary).getText().contains("Order"))
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
        flag = driver.findElement(reviewButton).isDisplayed();
        driver.findElement(reviewButton).click();
    }
    catch (Exception e){
     flag = true;
    }
    return flag;
}
/*This method is used to navigate to checkout page*/
public boolean proceedToPay() throws InterruptedException, IOException {
    logger.log(LogStatus.INFO,"Scrolling down....");
    scrollDown(0.80f);
    scrollDown(0.50f);
    try {
        wait.until(ExpectedConditions.elementToBeClickable(proceedToPay));
        driver.findElement(proceedToPay).click();
        logger.log(LogStatus.PASS,"Clicked on Proceed to Pay button" +logger.addScreenCapture(getScreenshot()));
        Thread.sleep(4000);
    }
    catch(Exception e)
    {
        logger.log(LogStatus.FAIL,"Proceed to Pay button is not available" +logger.addScreenCapture(getScreenshot()));
    }
    Set<String> allContext = driver.getContextHandles();
    for (String context : allContext) {
        if (context.contains("WEBVIEW"))
            driver.context(context);
    }
    try {
        String paymentmode = driver.findElement(paymenentModeButton).getText();
        if (paymentmode.contains("payment")) {
            logger.log(LogStatus.PASS, "Naviated to Payament Page " + logger.addScreenCapture(getScreenshot()));
            return true;
        } else {
            logger.log(LogStatus.FAIL, "Unable to Navigate to payment page ");
            logger.log(LogStatus.FAIL, "Navigation to payement page is failed" + logger.addScreenCapture(getScreenshot()));
            return false;
        }
    }
    catch (Exception e)
    {
        logger.log(LogStatus.FAIL, "Unable to Navigate to payment page ");
        logger.log(LogStatus.FAIL, "Navigation to payement page is failed" + logger.addScreenCapture(getScreenshot()));
        return false;
    }
}

public String getScreenshot() throws IOException, InterruptedException {
    String timeStamp;
    File screenShotName;
    Thread.sleep(4000);
    File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//The below method will save the screen shot in d drive with name "screenshot.png"
    timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    screenShotName = new File("./src/Screenshot_"+timeStamp+".png");
    FileUtils.copyFile(scrFile, screenShotName);
    String filePath = "Screenshot_"+timeStamp+".png";
    return filePath;
  }
}
