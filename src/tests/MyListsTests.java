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
        ArticlePageObject.addArticleToNewList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSavingTwoArticles() throws InterruptedException {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSeachInput();
        SearchPageObject.typeSearchLine("Mozart");
        SearchPageObject.clickByArticleWithSubstring("Wolfgang Amadeus Mozart");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String name_of_folder = "Violin";
        ArticlePageObject.addArticleToNewList(name_of_folder);
        ArticlePageObject.closeArticle();

        SearchPageObject.initSeachInput();
        SearchPageObject.typeSearchLine("Vivaldi");
        SearchPageObject.clickByArticleWithSubstring("Antonio Vivaldi");
        ArticlePageObject.addArticleToExistingList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.waitForFolderPresent(name_of_folder);
        MyListsPageObject.openFolderByName(name_of_folder);

        String delete_article = "Antonio Vivaldi";
        String save_article = "Wolfgang Amadeus Mozart";

        MyListsPageObject.swipeByArticleToDelete(delete_article);
        MyListsPageObject.waitForArticleToDisappearByTitle(delete_article);
        MyListsPageObject.openSavedArticle(save_article);

        String title_found = ArticlePageObject.getArticleTitle();
        assertEquals(
                "Page title doesn't match saved",
                save_article,
                title_found
        );
    }
}
