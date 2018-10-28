package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title;
        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.waitForTitleElement();
            article_title = ArticlePageObject.getArticleTitle();
        } else
        {
            ArticlePageObject.waitForKnownTitleElement("Object-oriented programming language");
            article_title = "Java (programming language)";
        }
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToNewList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIObjectFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if(Platform.getInstance().isIOS()){
            MyListsPageObject.openReadingLists();
        }
        MyListsPageObject.waitForFolderPresent(name_of_folder);
        MyListsPageObject.openFolderByName(name_of_folder);
        if(Platform.getInstance().isAndroid()) {
            MyListsPageObject.swipeByArticleToDelete(article_title);
        } else {
            MyListsPageObject.deleteSavedArticle(article_title);
        }
    }

    @Test
    public void testSavingTwoArticles() throws InterruptedException {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Mozart");
        SearchPageObject.clickByArticleWithSubstring("Wolfgang Amadeus Mozart");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.waitForTitleElement();
        } else
        {
            ArticlePageObject.waitForKnownTitleElement("Wolfgang Amadeus Mozart");
        }
        String name_of_folder = "Violin";
        ArticlePageObject.addArticleToNewList(name_of_folder);
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.clearSearchInput();
        SearchPageObject.typeSearchLine("Vivaldi");
        SearchPageObject.clickByArticleWithSubstring("Antonio Vivaldi");
        ArticlePageObject.addArticleToExistingList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIObjectFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if(Platform.getInstance().isIOS()){
            MyListsPageObject.openReadingLists();
        }
        MyListsPageObject.waitForFolderPresent(name_of_folder);
        MyListsPageObject.openFolderByName(name_of_folder);

        String delete_article = "Antonio Vivaldi";
        String save_article = "Wolfgang Amadeus Mozart";

        if(Platform.getInstance().isAndroid()) {
            MyListsPageObject.swipeByArticleToDelete(delete_article);
        }
        else {
            MyListsPageObject.deleteSavedArticle(delete_article);
        }
        MyListsPageObject.waitForArticleToDisappearByTitle(delete_article);
        MyListsPageObject.openSavedArticle(save_article);
        if(Platform.getInstance().isAndroid()) {
            String title_found = ArticlePageObject.getArticleTitle();
            assertEquals(
                    "Page title doesn't match saved",
                    save_article,
                    title_found
            );
        }
        else{
            ArticlePageObject.waitForKnownTitleElement(save_article);
        }

    }
}
