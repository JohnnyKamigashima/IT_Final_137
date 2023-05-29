package Pages;

import io.appium.java_client.android.AndroidDriver;

public class BasePage {
    protected AndroidDriver android;
    public BasePage(AndroidDriver android) {
        this.android = android;
    }
}
