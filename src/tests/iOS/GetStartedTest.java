package tests.iOS;
import lib.CoreTestCase;
import lib.ui.ios.*;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase
{

    @Test
    public void testPassThroughWelcome()
    {
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);

        WelcomePage.waitForLearnMoreLink();
        WelcomePage.clickNextButton();

        WelcomePage.waitForNewWaysToExploreText();
        WelcomePage.clickNextButton();

        WelcomePage.waitForAddOrEditPreferredLangText();
        WelcomePage.clickNextButton();

        WelcomePage.waitForLearnMoreAboutDataCollectedText();
        WelcomePage.clickGetStarted();
    }

    @Test
    public void testSkip()
    {
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);
        WelcomePage.clickSkipbutton();
    }

    @Test
    public void testSavingTwoArticles() throws InterruptedException
//            throws InterruptedException
    {
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);
        WelcomePage.clickSkipbutton();
        iosSearchPageObject SearchPageObject = new iosSearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Mozart");
        SearchPageObject.clickByArticleWithSubstring("Wolfgang Amadeus Mozart");

        iosArticlePageObject ArticlePageObject = new iosArticlePageObject(driver);
        ArticlePageObject.assertArticleSubtitle("Austrian composer of the Classical period");
        String name_of_folder = "Violin";
        ArticlePageObject.addArticleToNewList(name_of_folder);
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.clearSearchInput();
        SearchPageObject.typeSearchLine("Vivaldi");
        SearchPageObject.clickByArticleWithSubstring("Antonio Vivaldi");
        ArticlePageObject.addArticleToExistingList(name_of_folder);
        ArticlePageObject.closeArticle();

        iosNavigationUI NavigationUI = new iosNavigationUI(driver);
        NavigationUI.clickMyLists();

        iosMyListsPageObject MyListsPageObject = new iosMyListsPageObject(driver);
        MyListsPageObject.openReadingLists();
        MyListsPageObject.waitForFolderPresent(name_of_folder);
        MyListsPageObject.openFolderByName(name_of_folder);

        String delete_article = "Antonio Vivaldi";
        String save_article = "Wolfgang Amadeus Mozart";
        String save_subtitle = "Austrian composer of the Classical period";

        MyListsPageObject.deleteSavedArticle(delete_article);
        MyListsPageObject.waitForArticleToDisappearByTitle(delete_article);
        MyListsPageObject.openSavedArticle(save_article);

        ArticlePageObject.assertArticleSubtitle(save_subtitle);
    }
}
