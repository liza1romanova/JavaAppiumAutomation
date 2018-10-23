package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MainPageObject;
import org.openqa.selenium.WebElement;

import java.util.List;

public class iosSearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "xpath://*[contains(@name,'Search Wikipedia')]",
            SEARCH_INPUT = "xpath://XCUIElementTypeApplication[@name='Wikipedia']/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeSearchField",
            SEARCH_CANCEL_BUTTON = "id:Close",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeApplication[@name=\"Wikipedia\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell/*[contains(@name,'{SUBSTRING}')]",
            SEARCH_RESULT_ELEMENT = "xpath:////XCUIElementTypeApplication[@name='Wikipedia']/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell",
            SEARCH_RESULT_TITLE = "xpath://*[@resource-id='org.wikipedia:id/search_results_container']//*[@resource-id='org.wikipedia:id/page_list_item_title']",
            SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']",
            SEARCH_RESULT_BY_TITLE_AND_DESC_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']/*[./*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{SUBSTRING_TITLE}'] and ./*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{SUBSTRING_DESCRIPTION}']]";

    public iosSearchPageObject (AppiumDriver driver)
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
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element",10);
        this.waitForElementPresent(SEARCH_INPUT, "Cannot find search input after clicking search init element",10);

    }

    public void clearSearchInput()
    {
        this.waitForElementAndClear(
                SEARCH_INPUT,
                "Cannot find input field",
                5
        );
    }

    public WebElement waitForSearchField()
    {
        return this.waitForElementPresent(SEARCH_INPUT, "Cannot find input field");
    }

    public String getInputFieldText()
    {
        WebElement search_field = this.waitForSearchField();
        return search_field.getAttribute("text");
    }

    public void typeSearchLine(String search_line)
    {
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
        this.waitForElementAndClick(getResultSearchElement(substring), "Cannot find and click search result with substring ", 20);
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
