package Locators;
import utility.UtilityFunctions;

import java.util.Properties;

public class ANObjectRepo
{

    //Locators for End to end scenarios
    public static final String home = "//*[contains(@resource-id,'home')]";
    public static final String username = "//*[contains(@resource-id,'edit_text_username')]";
    public static final String password = "//*[contains(@resource-id,'edit_text_password')]";
    public static final String signInMenu = "//*[contains(@text,'Sign in')]";
    public static final String signInButton = "//*[contains(@resource-id,'button_sign_in')]";
    public static final String NotNowButton = "//*[@text = 'NOT NOW']";
    public static final String globalSearchBox = "//*[contains(@resource-id,'search_box')]";
    public static final String homeSearchBox  = "//*[contains(@resource-id,'search_src_text')]";
    public static final String firstSearchSuggItem = "(//*[contains(@text,'bags')])[1]";
    public static final String searchList = "//*[contains(@resource-id,'text')]";
    public static final String fifthSearchResultItem = "(//*[contains(@resource-id,'shipping_text')])[2]";
    public static final String descPageItemTitle = "//*[contains(@resource-id,'item_title')]";
    public static final String checkoutPageItemTitle = "";
    public static final String buyitnowButton = "//*[@text = 'Buy it now']";
    public static final String reviewButton = "//*[@text = 'Review']";
    public static final String paymenentModeButton = "//*[@text = 'Choose your payment method']";
    public static final String proceedToPay = "//*[@text = 'Proceed to Pay']";
    public static final String orderSummary = "//*[@text = 'Order Summary']";
}
