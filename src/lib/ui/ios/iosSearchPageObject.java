package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;
import org.openqa.selenium.WebElement;

import java.util.List;

public class iosSearchPageObject extends SearchPageObject {

    public iosSearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_CANCEL_BUTTON = "id:Close";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
        SEARCH_RESULT_TITLE = "xpath://XCUIElementTypeCell/XCUIElementTypeLink";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[contains[@name,'No results found']";
        SEARCH_RESULT_BY_TITLE_AND_DESC_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']/*[./*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{SUBSTRING_TITLE}'] and ./*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{SUBSTRING_DESCRIPTION}']]";
    }

}
