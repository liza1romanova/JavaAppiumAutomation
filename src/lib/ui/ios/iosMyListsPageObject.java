package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MainPageObject;
import lib.ui.MyListsPageObject;

public class iosMyListsPageObject extends MyListsPageObject {

    static {
        FOLDER_BY_NAME_TPL = "xpath://XCUIElementTypeLink[@name='{FOLDER_NAME}']/parent::XCUIElementTypeCell";
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeCell/XCUIElementTypeLink[contains(@name,'{TITLE}')]";
        READING_LISTS_TAB = "xpath://XCUIElementTypeButton[@name=\"Reading lists\"]";
        EDIT_BUTTON = "xpath://XCUIElementTypeButton[@name=\"Edit\"]";
        REMOVE_BUTTON = "xpath://XCUIElementTypeButton[@name=\"Remove\"]";
    }

    public iosMyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
