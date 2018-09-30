package tests;

import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

import java.util.List;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch() throws InterruptedException
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSeachInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

    }

    @Test
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSeachInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();

    }

    @Test
    public void testSearchBarPlaceholder()
    {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        WebElement search_field = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                10
        );

        String search_bar_text = search_field.getAttribute("text");

        assertEquals(
                "We see unexpected text",
                "Search…",
                search_bar_text
        );
    }

    @Test
    public void testClearSearch()
    {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Beethoven",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForAllElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Cannot find search results",
                15
        );

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_container"),
                "Element is still present",
                15
        );

    }

    @Test
    public void testSearchResultsContainText()
    {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Beethoven",
                "Cannot find search input",
                5
        );

        List<WebElement> results = MainPageObject.waitForAllElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Cannot find search results",
                15
        );

        for(WebElement result : results) {
            String text = result.getAttribute("text");
            boolean text_found = text.contains("Beethoven");
            assertTrue(
                    "Result doesn't match the request",
                    text_found
            );
        }

    }

    @Test
    public void testAmountOfNonEmptySearch(){

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSeachInput();
        String search_line = "Linkin Park Discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too fiew reults",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSeachInput();
        String search_line = "zesrxdtcfyvgbh";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testAssertTitle() throws InterruptedException {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        String search_line = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
                15
        );

        Thread.sleep(5000);

        MainPageObject.assertElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
                " Can't find page title"
        );

    }
}
