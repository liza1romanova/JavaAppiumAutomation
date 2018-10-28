package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

abstract public class SearchPageObject extends MainPageObject{

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_INPUT_FIELD,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_RESULT_TITLE,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_RESULT_BY_TITLE_AND_DESC_TPL;

    public SearchPageObject (AppiumDriver driver)
    {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getElementXpathByTitleAndDescription(String substring_title, String substring_description)
    {
        return SEARCH_RESULT_BY_TITLE_AND_DESC_TPL.replace("{SUBSTRING_TITLE}", substring_title).replace("{SUBSTRING_DESCRIPTION}", substring_description);
    }
    /* TEMPLATES METHODS */

    public void initSearchInput()
    {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element",5);
        this.waitForElementPresent(SEARCH_INPUT, "Cannot find search input after clicking search init element");

    }

    public void clearSearchInput()
    {
        this.waitForElementAndClear(
                SEARCH_INPUT,
                "Cannot find input field",
                5
        );
    }

    public WebElement waitForSearchInputField()
    {
        return this.waitForElementPresent(SEARCH_INPUT_FIELD, "Cannot find input field");
    }

    public String getInputFieldText()
    {
        WebElement search_field = this.waitForSearchInputField();
        return search_field.getAttribute("text");
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndClear(SEARCH_INPUT, "Cannot find and type into Search input", 5);
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into Search input", 5);
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring " + substring);
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find Search Cancel button", 5);
    }

    public void waitForCancelButtonToDisAppear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search Cancel button is still present", 5);
    }

    public void clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find Search Cancel button", 5);
    }

    public void clickByArticleWithSubstring(String substring) throws InterruptedException {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 20);
        Thread.sleep(700);
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by request",
                15
        );

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty results label by the request", 10);
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    public List<WebElement> findAllResultsTitles()
    {
        return this.waitForAllElementsPresent(SEARCH_RESULT_TITLE,"Cannot find search results", 15);
    }

    public void waitForElementByTitleAndDescription(String article_title, String article_description)
    {
        String element_xpath = getElementXpathByTitleAndDescription(article_title, article_description);
        this.waitForElementPresent(element_xpath, "Cannot find result with title "+article_title+" and description "+article_description);
    }
}
