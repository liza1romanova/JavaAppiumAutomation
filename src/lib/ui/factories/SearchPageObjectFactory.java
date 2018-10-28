package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.android.androidSearchPageObject;
import lib.ui.ios.iosSearchPageObject;

public class SearchPageObjectFactory {
    public static SearchPageObject get(AppiumDriver driver)
    {
        if(Platform.getInstance().isAndroid()){
            return new androidSearchPageObject(driver);
        }
        else {
            return new iosSearchPageObject(driver);
        }
    }
}
