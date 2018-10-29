package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

    }

    @Test
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
    }

    @Test
    public void testSearchBarPlaceholder()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_bar_text = SearchPageObject.getInputFieldText();
        assertEquals(
                "We see unexpected text",
                "Search…",
                search_bar_text
        );
    }

    @Test
    public void testClearSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Beethoven");
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "We found too fiew reults",
                amount_of_search_results > 0
        );
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testSearchResultsContainText()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Beethoven");
        List<WebElement> results = SearchPageObject.findAllResultsTitles();

        for(WebElement result : results) {
            String text = result.getAttribute("text");
            boolean text_found = text.contains("Beethoven");
            assertTrue(
                    "Result " + text + " doesn't match the request",
                    text_found
            );
        }

    }

    @Test
    public void testAmountOfNonEmptySearch(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
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
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "zesrxdtcfyvgbh";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testResultByTitleAndDescription()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Python";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "We found less than 3 search results",
                amount_of_search_results > 3
        );
        SearchPageObject.waitForElementByTitleAndDescription("Python (programming language)","General-purpose, high-level programming language");

    }

    @Test
    public void testResultsValidation()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Python";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "We found less than 3 search results",
                amount_of_search_results > 3
        );

        List<WebElement> results = SearchPageObject.findAllResultsTitles();

        for(WebElement result : results) {
            String text = result.getAttribute("name");
            boolean text_found = text.contains("Python");
            assertTrue(
                    "Result " + text + " doesn't match the request",
                    text_found
            );
        }
    }
}
