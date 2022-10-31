package testScreens2;

import bs.Base118;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;


public class HomeScreen2 {

    @AndroidFindBy(id="android:id/button1")
    @iOSXCUITFindBy(id="test")
    public MobileElement okButton;

    @AndroidFindBy(xpath="//*[@text='OK']")
    @iOSXCUITFindBy(id="test")
    public MobileElement okButton2;

    @AndroidFindBy(accessibility ="Open navigation drawer")
    @iOSXCUITFindBy(id="test")
    public MobileElement navigationDrawer;

    @AndroidFindBy(xpath="(//*[@text='Popular Today'])[1]")
    @iOSXCUITFindBy(id="test")
    public MobileElement popularToday;

    @AndroidFindBy(xpath="(//*[@content-desc=\"Article Title\"])[1]")
    @iOSXCUITFindBy(id="test")
    public MobileElement firstArticle;

    Base118 bs118;

    public HomeScreen2 ()
    {
        bs118 = new Base118();
        PageFactory.initElements(new AppiumFieldDecorator(bs118.getDriver()), this);
    }

    public void reachHomeScreen()
    {
//        okButton.click();
//        okButton2.click();
        bs118.click(okButton);
        bs118.click(okButton2);
    }

    public void navigateToPopularNews()
    {
//        navigationDrawer.click();
//        popularToday.click();
        bs118.click(navigationDrawer);
        bs118.click(popularToday);
    }

    public void backToLatestNews()
    {
//        navigationDrawer.click();
//        firstArticle.click();
        bs118.click(navigationDrawer);
        bs118.click(firstArticle);
    }

    public void moveToFirstNews()
    {
     //   firstArticle.click();
        bs118.click(firstArticle);
    }
}
