package TestCases;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import utility.UtilityFunctions;
import GlobalFunctions.pageFunctions;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class EndToEnd extends UtilityFunctions
{
    static AndroidDriver driver;
    static WebDriverWait wait;
    AppiumDriverLocalService service;
    DesiredCapabilities capabilities;
    pageFunctions pg1,pg2;
    ExtentReports extent;
    ExtentTest logger;

/*This method is used to configure extent report*/
@BeforeTest
public void setUpReporting()
{
    extent = new ExtentReports ("./src/eBayReport.html", true);
    logger = extent.startTest("eBayTestCase");
    extent
            .addSystemInfo("Host Name", "Localhost")
            .addSystemInfo("Environment", "Chrome")
            .addSystemInfo("User Name", "ynm");
    extent.loadConfig(new File("./extent-config.xml"));
}

@BeforeClass
public void setUp()
{
    logger.log(LogStatus.INFO,"Setting up the test envirnment");
    //Setting the capabilities
    capabilities = setCapabilities();
    logger.log(LogStatus.INFO,"Setting up the capabilities");
    //Starting appium server
    service = startAppium(capabilities);
    //Initializing android driver
    driver = new AndroidDriver(service, capabilities);
    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver,7);
    ScreenOrientation orientation = driver.getOrientation();
    if(orientation.equals(ScreenOrientation.LANDSCAPE))
        driver.rotate(ScreenOrientation.PORTRAIT);
    logger.log(LogStatus.INFO,"Application is launched");

}
//Test Case to validate end to end flow of item checkout
@Test
public void TC_01() throws InterruptedException, IOException {
    pg1 =  new pageFunctions(driver,wait,logger);
    Assert.assertEquals(pg1.login(),true);
    pg2 = new pageFunctions(driver);
    Assert.assertEquals(pg1.searchItem(),true);
    Assert.assertEquals(pg1.clickOnReview(),true);
    Assert.assertEquals(pg1.proceedToPay(),true);
}

@AfterClass
public void tearDown() throws IOException {
    extent.endTest(logger);
    extent.flush();
    service.stop();
}
}
