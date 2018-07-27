package utility;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class UtilityFunctions
{
Properties prop;
AppiumServiceBuilder builder;
AppiumDriverLocalService service;
Logger logger = Logger.getLogger("ebayLogger");

public DesiredCapabilities setCapabilities()
{
    logger.debug("Test SetUp is starting.....");
    String path =  "./src/main/resources/setUpConfig.properties";   //"D:\\eBayAutomationSuite\\src\\main\\resources\\setUpConfig.properties";//D:\eBayAutomationSuite\src\
    prop = loadPropFile(path);
    DesiredCapabilities capabilities = new DesiredCapabilities();

    capabilities.setCapability("VERSION", prop.getProperty("VERSION"));
    capabilities.setCapability("deviceName", prop.getProperty("deviceName"));
    capabilities.setCapability("platformName", prop.getProperty("platformName"));
    //capabilities.setCapability("app","C:\\Users\\Yugesh.Mahure\\Desktop\\WhatsApp.apk");
    capabilities.setCapability("noReset", prop.getProperty("noReset"));
    capabilities.setCapability("appPackage", prop.getProperty("appPackage"));
    capabilities.setCapability("appActivity", prop.getProperty("appActivity"));
    logger.debug("All the required capabilities are set");
    return capabilities;
}

    public AppiumDriverLocalService startAppium(DesiredCapabilities caps) {
        builder = new AppiumServiceBuilder();
        builder.withIPAddress("0.0.0.0");
        builder.usingPort(4723);
        builder.withCapabilities(caps);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
        //Start the server with the builder
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
        logger.debug("Appium started on port 4723");
        return service;
    }

    public static Properties loadPropFile(String path)
    {
        File file = new File(path);
        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties p = new Properties();

        //load properties file
        try {
            p.load(fileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }
}

