package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.MyListsPageObject;
import lib.ui.android.androidMyListsPageObject;
import lib.ui.ios.iosMyListsPageObject;

public class MyListsPageObjectFactory {
    public static MyListsPageObject get(AppiumDriver driver)
    {
        if(Platform.getInstance().isAndroid()){
            return new androidMyListsPageObject(driver);
        }
        else {
            return new iosMyListsPageObject(driver);
        }
    }
}
