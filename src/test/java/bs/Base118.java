package bs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;


public class Base118 {
    protected static ThreadLocal <AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
    protected static  ThreadLocal<Properties> props = new ThreadLocal<Properties>();
    protected static AppiumDriverLocalService server;

    public Base118()
    {
    }

    public void setDriver(AppiumDriver driver2)
    {
        driver.set(driver2);
    }

    public AppiumDriver getDriver()
    {
        return driver.get();
    }

    public void setProps(Properties props2)
    {
        props.set(props2);
    }

    public Properties getProps()
    {
        return props.get();
    }

    public static boolean checkIfServerIsRunnning(int port) throws Exception {
        boolean isAppiumServerRunning = false;
        ServerSocket socket;
        try {
            socket = new ServerSocket(port);
            socket.close();
        } catch (IOException e) {
            System.out.println("1");
            isAppiumServerRunning = true;
        } finally {
            socket = null;
        }
        return isAppiumServerRunning;
    }


    public void initializeDriver() {

        boolean flag= false;
        try {
            flag = checkIfServerIsRunnning(4727);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!flag)
        {
            server = AppiumDriverLocalService.buildDefaultService();
            server.start();
        }

        try {
            FileInputStream fis;
            Properties props = new Properties();
            AppiumDriver driver;
            URL url;
            String propFileName = "/Users/aubergine/Downloads/Frider/src/main/resources/Global.properties";

            fis = new FileInputStream(propFileName);
            props.load(fis);
            String platformName = (String) props.get("platformName");
            String androidEmulatorAlowed = (String) props.get("androidEmulator");
            String iOSEmulatorAlowed = (String) props.get("iOSEmulator");
            String androidAppLocation = System.getProperty("user.dir") + props.get("androidAppLocation");
            String iOSAppLocation=System.getProperty("user.dir") + props.get("iOSAppLocation");

            DesiredCapabilities dc = new DesiredCapabilities();

            switch (platformName) {
                case "Android":
                    dc.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
                    dc.setCapability(MobileCapabilityType.PLATFORM_NAME, props.get("platformName"));

                    if(androidEmulatorAlowed.equalsIgnoreCase("true"))
                    {
                        dc.setCapability("platformVersion",props.get("androidPlatformVersion"));
             //           dc.setCapability("avd", props.get("androidDevice"));
                        dc.setCapability(MobileCapabilityType.DEVICE_NAME,props.get("androidDevice"));
                    }
                    else
                    {
                        dc.setCapability("udid", props.get("androidUDID"));
                    }
                    dc.setCapability(MobileCapabilityType.APP, androidAppLocation);
                    dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.get("androidAutomationName"));

                    url = new URL(props.getProperty("appiumURL") + "4723/wd/hub");
                    driver= new AndroidDriver(url,dc);
                    break;


                case "iOS":
                    dc.setCapability(MobileCapabilityType.PLATFORM_NAME, props.get("platformName"));
                    if(iOSEmulatorAlowed.equalsIgnoreCase("true"))
                    {
                        dc.setCapability("platformVersion",props.get("iOSPlatformVersion"));
                        dc.setCapability("avd", props.get("iOSDevice"));
                    }
                    else
                    {
                        dc.setCapability("udid", props.get("iosUDID"));
                    }
                    dc.setCapability(MobileCapabilityType.APP, iOSAppLocation);
                    dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.get("iOSAutomationName"));

                    url = new URL(props.getProperty("appiumURL") + "4724/wd/hub");
                    driver = new IOSDriver(url, dc);
                    break;

                default :
                    throw new Exception("Invalid platform! =");
            }
            setDriver(driver);
            String sessionId = driver.getSessionId().toString();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }


    public void quitDriver()
    {
        getDriver().quit();
    }

    public int getScreenWidth() {
        return getDriver().manage().window().getSize().width;
    }

    public int getScreenHeight() {
        return getDriver().manage().window().getSize().height;
    }

    public void scroll(int startX, int startY, int endX, int endY)
    {
        System.out.println("In scroll");
        TouchAction action = new TouchAction(getDriver());
        System.out.println("Touch Action initialized");
        action.longPress(PointOption.point(startX, startY)).moveTo(PointOption.point(endX, endY)).release().perform();
    }

    public void scrollUpFullScreen() {
        int startX = getScreenWidth() / 2;
        int startY = getScreenHeight() /2;
        int endY = getScreenHeight() * 8/9;
        scroll(startX, startY, startX, endY);
    }

    public void scrollDownFullScreen() {
        int startX = getScreenWidth() / 2;
        System.out.println("Got x");
        int startY = getScreenHeight() * 8/9;
        System.out.println("Got start y");
        int endY = getScreenHeight() / 9;
        System.out.println("Got end Y");
        scroll(startX, startY, startX, endY);
    }

    public void scrollUpHalfScreen() {
        int startX = getScreenWidth() / 2;
        int startY = getScreenHeight() /2;
        int endY = getScreenHeight() * 7/9;
        scroll(startX, startY, startX, endY);
    }

    public void scrollDownHalfScreen() {
        int startX = getScreenWidth() / 2;
        int startY = getScreenHeight() * 7/9;
        int endY = getScreenHeight() /2;
        scroll(startX, startY, startX, endY);
    }

    public void scrollUpALittle() {
        int startX = getScreenWidth() / 2;
        int startY = getScreenHeight() * 2/3;
        int endY = getScreenHeight() * 8/9;
        scroll(startX, startY, startX, endY);
    }

    public void scrollDownALittle() {
        int startX = getScreenWidth() / 2;
        int startY = getScreenHeight() * 8/9;
        int endY = getScreenHeight() * 2/3;
        scroll(startX, startY, startX, endY);
    }

    public void waitForVisibility(MobileElement e) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 70);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void waitForVisibility(WebElement e){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void clear(MobileElement e) {
        waitForVisibility(e);
        e.clear();
    }

    public void click(MobileElement e) {
        waitForVisibility(e);
        e.click();
    }

    public void sendKeys(MobileElement e, String txt) {
        waitForVisibility(e);
        e.sendKeys(txt);
    }


}
