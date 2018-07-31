package Locators;
import org.openqa.selenium.By;
import utility.UtilityFunctions;

import java.util.Properties;

public class ANObjectRepo
{

    //Locators for End to end scenarios
    public By home = By.xpath("//*[contains(@resource-id,'home')]");
    public By username = By.xpath("//*[contains(@resource-id,'edit_text_username')]");
    public By password = By.xpath("//*[contains(@resource-id,'edit_text_password')]");
    public By signInMenu = By.xpath("//*[contains(@text,'Sign in')]");
    public By signInButton = By.xpath("//*[contains(@resource-id,'button_sign_in')]");
    public By NotNowButton = By.xpath("//*[@text = 'NOT NOW']");
    public By globalSearchBox = By.xpath("//*[contains(@resource-id,'search_box')]");
    public By homeSearchBox  = By.xpath("//*[contains(@resource-id,'search_src_text')]");
    public By firstSearchSuggItem = By.xpath("(//*[contains(@text,'bags')])[1]");
    public By searchList = By.xpath("//*[contains(@resource-id,'text')]");
    public By fifthSearchResultItem = By.xpath("(//*[contains(@resource-id,'shipping_text')])[2]");
    public By descPageItemTitle = By.xpath("//*[contains(@resource-id,'item_title')]");
    public By buyitnowButton = By.xpath("//*[@text = 'Buy it now']");
    public By reviewButton = By.xpath("//*[@text = 'Review']");
    public By paymenentModeButton = By.xpath("//*[@text = 'Choose your payment method']");
    public By proceedToPay = By.xpath("//*[@text = 'Proceed to Pay']");
    public By orderSummary = By.xpath("//*[@text = 'Order Summary']");


    public By setXpath(String searchItem)
    {
        By searchItme = By.xpath("//*[contains(@text,'"+searchItem+"')]");
        return searchItme;
    }

}
