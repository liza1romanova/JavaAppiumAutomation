package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MainPageObject;

public class iosNavigationUI extends MainPageObject{

    private static final String
        MY_LISTS_LINK = "xpath://XCUIElementTypeButton[@name=\"Saved\"]";

    public iosNavigationUI(AppiumDriver driver)
    {
        super(driver);
    }

    public void clickMyLists()
    {
        this.waitForElementAndClick(
                MY_LISTS_LINK,
                "Can't find navigation button to My list",
                10
        );
    }
}
