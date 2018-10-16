package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class CoreTestCase extends TestCase {

    private static final String
            PLATFORM_IOS = "ios",
            PLATFORM_ANDROID = "android";

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = this.getCapabilitiesByPlatformEnv();

//        driver = new IOSDriver(new URL(AppiumURL), capabilities);
        this.getDriverByPlatform(AppiumURL,capabilities);
        this.rotateScreenPortrait();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }
    protected void rotateScreenPortrait()
    {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape()
    {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp()
    {
        driver.runAppInBackground(Duration.ofMillis(250));
    }

    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception
    {
        String platform = System.getenv("PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)) {
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "AndroidTestDevice");
            capabilities.setCapability("platformVersion", "6.0");
            capabilities.setCapability("automationName", "Appium");
            capabilities.setCapability("appPackage", "org.wikipedia");
            capabilities.setCapability("appActivity", ".main.MainActivity");
            capabilities.setCapability("app", "/Users/Elizabeth/JavaAppiumAutomation/apks/org.wikipedia.apk");
        }
        else if (platform.equals(PLATFORM_IOS)){
            capabilities.setCapability("platformName","iOS");
            capabilities.setCapability("deviceName","iPhone 8");
            capabilities.setCapability("platformVersion","12.0");
            capabilities.setCapability("app","/Users/elizabeth/Automation/JavaAppiumAutomation/apks/Wikipedia.app");
        }
        else {
            throw new Exception("Cannot get run platform from env variable. Platform value " + platform);
        }

        return capabilities;
    }

    private void getDriverByPlatform(String url, Capabilities caps) throws Exception
    {
        String platform = System.getenv("PLATFORM");
        if (platform.equals(PLATFORM_ANDROID)) {
            driver = new AndroidDriver(new URL(url), caps);
        }
        else if (platform.equals(PLATFORM_IOS)){
            driver = new IOSDriver(new URL(url), caps);
        }
        else {
            throw new Exception("Cannot get run driver from env variable. Platform value " + platform);
        }
    }
}
