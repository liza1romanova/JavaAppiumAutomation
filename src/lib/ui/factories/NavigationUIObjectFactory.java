package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.NavigationUI;
import lib.ui.android.androidNavigationUI;
import lib.ui.ios.iosNavigationUI;

public class NavigationUIObjectFactory {
    public static NavigationUI get(AppiumDriver driver)
    {
        if(Platform.getInstance().isAndroid()){
            return new androidNavigationUI(driver);
        }
        else {
            return new iosNavigationUI(driver);
        }
    }
}
