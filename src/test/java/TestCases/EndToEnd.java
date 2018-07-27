package TestCases;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utility.UtilityFunctions;
import GlobalFunctions.pageFunctions;
import java.util.concurrent.TimeUnit;

public class EndToEnd extends UtilityFunctions
{
    static AndroidDriver driver;
    static WebDriverWait wait;
    AppiumDriverLocalService service;
    DesiredCapabilities capabilities;
    pageFunctions pg1;
@BeforeClass
public void setUp()
{
    //Setting the capabilities
    capabilities = setCapabilities();
    //Starting appium server
    service = startAppium(capabilities);
    //Initializing android driver
    driver = new AndroidDriver(service, capabilities);
    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver,5);
}
//Test Case to validate end to end flow of item checkout
@Test
public void TC_01() throws InterruptedException
{
    pg1 =  new pageFunctions(driver,wait);
    Assert.assertEquals(pg1.login(),true);
    Assert.assertEquals(pg1.searchItem(),true);
    Assert.assertEquals(pg1.clickOnReview(),true);
    Assert.assertEquals(pg1.proceedToPay(),true);
}

@AfterClass
public void tearDown()
{
    service.stop();
}
}
