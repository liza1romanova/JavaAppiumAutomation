package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSeachInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }
    @Test
    public void testSavingTwoArticles()
    {

        MainPageObject.openFirstArtickeBySearch("Mozart");

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can't find article to open article options",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/text_input"),
                "Can't find input field to set articles folder",
                5
        );

        String name_of_folder = "Violin";

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot input field",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles field input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.Button[@text='OK']"),
                "Can't press OK button",
                10
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Can't close article, can't find X button",
                10
        );

        MainPageObject.openFirstArtickeBySearch("Vivaldi");

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can't find article to open article options",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/list_of_lists']//*[@text='"+ name_of_folder +"']"),
                "Cannot find saved list " + name_of_folder + " to add the article",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Can't close article, can't find X button",
                10
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Can't find navigation button to My list",
                10
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find saved list " + name_of_folder,
                15
        );

        String delete_article = "Antonio Vivaldi";
        String save_article = "Wolfgang Amadeus Mozart";

        MainPageObject.swipeElementToleft(
                By.xpath("//*[@text='"+ delete_article +"']"),
                "Can't find saved article" + delete_article
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='"+ delete_article +"']"),
                "Can't delete article " + delete_article,
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='"+ save_article +"']"),
                "Cannot find saved article " + save_article,
                15
        );

        WebElement page_title = MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
                "Cannot find page title",
                15
        );

        String title_found = page_title.getAttribute("text");
        assertEquals(
                "Page title doesn't match saved",
                save_article,
                title_found
        );
    }
}
