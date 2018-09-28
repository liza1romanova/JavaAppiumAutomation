import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;
    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","6.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/Elizabeth/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }
    @After
    public void tearDown()
    {
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.quit();
    }

    @Test
    public void firstTest() throws InterruptedException
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );

    }

    @Test
    public void testCancelSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find page title",
                15
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testSwipeArticle()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find topic searching by 'Appium'",
                15
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find page title",
                15
        );

        SwipeUpToFindElement(
                By.xpath("//*[contains(@text, 'View page in browser')]"),
                "Cannot find the end of article",
                20
        );
    }

    @Test
    public void testSearchBarPlaceholder()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        WebElement search_field = waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                10
        );

        String search_bar_text = search_field.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected text",
                "Search…",
                search_bar_text
        );
    }

    @Test
    public void testClearSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Beethoven",
                "Cannot find search input",
                5
        );

        waitForAllElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Cannot find search results",
                15
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_container"),
                "Element is still present",
                15
        );

    }

    @Test
    public void testSearchResultsContainText()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Beethoven",
                "Cannot find search input",
                5
        );

        List<WebElement> results = waitForAllElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Cannot find search results",
                15
        );

        for(WebElement result : results) {
            String text = result.getAttribute("text");
            boolean text_found = text.contains("Beethoven");
            Assert.assertTrue(
                    "Result doesn't match the request",
                    text_found
            );
        }

    }

    @Test
    public void saveFirstArticleToMyList()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find page title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can't find article to open article options",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/text_input"),
                "Can't fing input field to set articles folder",
                5
        );

        String name_of_folder = "Learning programming";

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot input field",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles field input",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.Button[@text='OK']"),
                "Can't press OK button",
                10
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Can't close article, can't find X button",
                10
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Can't find navigation button to My list",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find saved list 'Learning programming'",
                15
        );

        waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)'",
                15
        );

        swipeElementToleft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Can't find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Can't delete saved article",
                5
        );
    }

    @Test
    public void amountOfNonEmptySearch(){

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        String search_line = "Linkin Park Discography";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot fing anything by request" + search_line,
                15
        );

        int amount_of_search_results = getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue(
                "We found too fiew reults",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        String search_line = "zesrxdtcfyvgbh";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

        String empty_result_label = "//*[@text='No results found']";

        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty results label by the request " + search_line,
                15
        );

        assertElementNotPresent(
                By.xpath(search_result_locator),
                "We've found some results by request" + search_line
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearchresults()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        String search_line = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
                15
        );

        String title_before_rotation = waitforElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitforElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title has been change after rotation",
                title_before_rotation,
                title_after_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = waitforElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title has been change after rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    @Test
    public void testSearchArticleInBackground()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        String search_line = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
                15
        );

        driver.runAppInBackground(3);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find article after going to background" + search_line,
                15
        );
    }

    @Test
    public void testSavingTwoArticles()
    {

        openFirstArtickeBySearch("Mozart");

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can't find article to open article options",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/text_input"),
                "Can't find input field to set articles folder",
                5
        );

        String name_of_folder = "Violin";

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot input field",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles field input",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.Button[@text='OK']"),
                "Can't press OK button",
                10
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Can't close article, can't find X button",
                10
        );

        openFirstArtickeBySearch("Vivaldi");

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can't find article to open article options",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/list_of_lists']//*[@text='"+ name_of_folder +"']"),
                "Cannot find saved list " + name_of_folder + " to add the article",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Can't close article, can't find X button",
                10
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Can't find navigation button to My list",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find saved list " + name_of_folder,
                15
        );

        String delete_article = "Antonio Vivaldi";
        String save_article = "Wolfgang Amadeus Mozart";

        swipeElementToleft(
                By.xpath("//*[@text='"+ delete_article +"']"),
                "Can't find saved article" + delete_article
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='"+ delete_article +"']"),
                "Can't delete article " + delete_article,
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='"+ save_article +"']"),
                "Cannot find saved article " + save_article,
                15
        );

        WebElement page_title = waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
                "Cannot find page title",
                15
        );

        String title_found = page_title.getAttribute("text");
        Assert.assertEquals(
                "Page title doesn't match saved",
                save_article,
                title_found
        );
    }

    @Test
    public void testAssertTitle() throws InterruptedException {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        String search_line = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
                15
        );

        Thread.sleep(5000);

        assertElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
                " Can't find page title"
        );

    }

    @Test
    public void testDeviceRotate()
    {
        driver.rotate(ScreenOrientation.LANDSCAPE);

        assertElementPresent(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'"
        );
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message)
    {
        return waitForElementPresent(by,error_message,5);
    }

    private List<WebElement> waitForAllElementsPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
        );
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver,timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    protected void swipeUp(int tomeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action
                .press(x, start_y)
                .waitAction(tomeOfSwipe).moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick()
    {
        swipeUp(200);
    }

    protected void SwipeUpToFindElement(By by, String error_message, int max_swipes)
    {
      int already_swiped = 0;

        while (driver.findElements(by).size() == 0){

            if(already_swiped > max_swipes){
                waitForElementPresent(by,"Can't find element by swiping up\n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }

    }

    protected void swipeElementToleft(By by, String error_message){
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10
        );

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y)/2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300).moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return elements.size();

    }

    private void assertElementNotPresent(By by, String error_message)
    {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0){
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + error_message);
        }
    }

    private String waitforElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    protected void openFirstArtickeBySearch(String search_request)
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_request,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container'][@index=0]"),
                "Cannot find topic searching by " + search_request,
                15
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find page title",
                15
        );
    }

    private void assertElementPresent(By by, String error_message)
    {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements == 0){
            String default_message = "An element '" + by.toString() + "' is not found on the page";
            throw new AssertionError(default_message + error_message);
        }
    }
}
