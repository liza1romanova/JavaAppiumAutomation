package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MainPageObject;
import org.openqa.selenium.WebElement;

public class iosArticlePageObject extends MainPageObject {

    private static final String
        TITLE = "xpath://XCUIElementTypeStaticText[contains(@name,'{SUBSTRING}')]",
        SUBTITLE = "xpath://XCUIElementTypeStaticText[contains(@name,'{SUBSTRING}')]",
        FOOTER_ELEMENT = "xpath://*[@text, 'View page in browser']",
        OPTIONS_BUTTON = "xpath://XCUIElementTypeButton[@name=\"Share\"]",
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://XCUIElementTypeOther[@name='ActivityListView']//XCUIElementTypeButton[@name='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "xpath://XCUIElementTypeButton[@name=\"Add\"]",
        MY_LIST_NAME_INPUT = "xpath:(//XCUIElementTypeTextField)[1]",
        MY_LIST_OK_BUTTON = "xpath://XCUIElementTypeButton[@name=\"Create reading list\"]",
        EXISTING_LIST_TPL = "xpath://XCUIElementTypeLink[@label='{SUBSTRING}']/parent::XCUIElementTypeCell",
        CLOSE_ARTICLE_BUTTON = "xpath://XCUIElementTypeButton[@name=\"Back\"]";

    public iosArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getExistingListXpath(String list_name)
    {
        return EXISTING_LIST_TPL.replace("{SUBSTRING}", list_name);
    }
    private static String getPageTitle(String title)
    {
        return TITLE.replace("{SUBSTRING}", title);
    }
    private static String getPageSubtitle(String subtitle)
    {
        return SUBTITLE.replace("{SUBSTRING}", subtitle);
    }
    /* TEMPLATES METHODS */

    public WebElement waitForTitleElement(String title)
    {
        String page_title = getPageTitle(title);
        return this.waitForElementPresent(page_title, "Cannot find article title on page", 20);
    }

//    public String getArticleTitle()
//    {
//        WebElement title_element = waitForTitleElement();
//        return title_element.getAttribute("text");
//    }

    public void assertArticleTitle()
    {
        this.assertElementPresent(TITLE,"Cannot find article title on page");
    }

    public void assertArticleSubtitle(String subtitle)
    {
        this.waitForElementPresent(getPageSubtitle(subtitle),"Cannot find article title on page",10);
    }

    public void swipeToFooter()
    {
        this.swipeUpToFindElement(
                FOOTER_ELEMENT,
                "Cannot find the end of Article",
                20
        );
    }

    public void addArticleToNewList(String name_of_folder) throws InterruptedException {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Can't find article to open article options",
                5
        );

        Thread.sleep(15000);

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );

        Thread.sleep(3000);

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles field input",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Can't press OK button",
                10
        );
    }

    public void addArticleToExistingList(String list_name) throws InterruptedException {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Can't find article to open article options",
                5
        );

        Thread.sleep(15000);

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );

        Thread.sleep(3000);

        String element = getExistingListXpath(list_name);

        this.waitForElementAndClick(
                element,
                "Cannot find saved list " + list_name + " to add the article",
                15);
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Can't close article, can't find X button",
                10
        );
    }
}
