package lib.ui;

import io.appium.java_client.AppiumDriver;

public class MyListsPageObject extends MainPageObject{

    protected static String
        FOLDER_BY_NAME_TPL,
        ARTICLE_BY_TITLE_TPL,
        READING_LISTS_TAB,
        EDIT_BUTTON,
        REMOVE_BUTTON;

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

    public MyListsPageObject(AppiumDriver driver)
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

    public void swipeByArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getFolderXpathByName(article_title);
        this.swipeElementToleft(
                article_xpath,
                "Can't find saved article"
        );
        this.waitForArticleToDisappearByTitle(article_title);
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
