package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MainPageObject;

public class iosMyListsPageObject extends MainPageObject{

    private static final String
        FOLDER_BY_NAME_TPL = "xpath://XCUIElementTypeLink[@name='{FOLDER_NAME}']/parent::XCUIElementTypeCell",
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeCell/XCUIElementTypeLink[contains(@name,'{TITLE}')]",
        READING_LISTS_TAB = "xpath://XCUIElementTypeButton[@name=\"Reading lists\"]",
        EDIT_BUTTON = "xpath://XCUIElementTypeButton[@name=\"Edit\"]",
        REMOVE_BUTTON = "xpath://XCUIElementTypeButton[@name=\"Remove\"]";

    /* TEMPLATES METHODS */
    private static String getFolderXpathByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }
    /* TEMPLATES METHODS */

    public iosMyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public void openFolderByName(String name_of_folder)
    {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                15
        );
    }

    public void openReadingLists()
    {
        this.waitForElementAndClick(
                READING_LISTS_TAB,
                "cannot find reading lists tab",
                10
        );
    }

    public void waitForFolderPresent(String name_of_folder)
    {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementPresent(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                15
        );
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(article_xpath, "Cannot find saved article by title " + article_title, 15);
    }

    public void waitForArticleToDisappearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(article_xpath, "Saved article still present with title " + article_title, 15);
    }

    public void deleteSavedArticle(String article_title)
    {
        this.waitForElementAndClick(EDIT_BUTTON, "Can't fing edit button", 5);
        this.waitForElementAndClick(
                getSavedArticleXpathByTitle(article_title),
                "Can't find saved article" + article_title,
                5
        );
        this.waitForElementAndClick(REMOVE_BUTTON, "Can't find remove button", 5);
    }

    public void openSavedArticle(String article_title)
    {
        this.waitForElementAndClick(
                getSavedArticleXpathByTitle(article_title),
                "Cannot find saved article " + article_title,
                15
        );
    }
}
