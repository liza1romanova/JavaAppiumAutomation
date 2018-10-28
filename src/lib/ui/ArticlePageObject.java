package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject
{
    protected static String
        TITLE,
        SUBTITLE,
        FOOTER_ELEMENT,
        OPTIONS_BUTTON,
        OPTIONS_ADD_TO_MY_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        EXISTING_LIST_TPL,
        CLOSE_ARTICLE_BUTTON;

    public ArticlePageObject(AppiumDriver driver)
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
        return TITLE.replace("{SUBSTRING}",title);
    }
    private static String getPageSubtitle(String subtitle)
    {
        return SUBTITLE.replace("{SUBSTRING}", subtitle);
    }
    /* TEMPLATES METHODS */

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(TITLE, "Cannot find article title on page", 20);
    }

    public WebElement waitForKnownTitleElement(String title)
    {
        return this.waitForElementPresent(getPageTitle(title), "Cannot find known article title on page", 20);
    }
    public String getArticleTitle()
    {
        WebElement title_element = waitForTitleElement();
        if(Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        }
        else {
            return title_element.getAttribute("name");
        }
    }

    public void assertArticleTitle()
    {
        this.assertElementPresent(TITLE,"Cannot find article title on page");
    }

    public void swipeToFooter()
    {
        if(Platform.getInstance().isIOS()){
            this.swipeTillElementAppear(FOOTER_ELEMENT,"Cannot find the end of Article", 40);
        } else{
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of Article",
                    40
            );
        }
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

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Can't find input field to set articles folder",
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
