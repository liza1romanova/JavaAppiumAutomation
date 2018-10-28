package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.android.androidArticlePageObject;
import lib.ui.ios.iosArticlePageObject;

public class ArticlePageObjectFactory {
    public static ArticlePageObject get(AppiumDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new androidArticlePageObject(driver);
        } else {
            return new iosArticlePageObject(driver);
        }

    }
}
