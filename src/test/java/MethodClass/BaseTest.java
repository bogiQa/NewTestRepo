package MethodClass;

import Utils.testUtils;
import com.aventstack.extentreports.reporter.ExtentReporter;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.internal.Utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import org.apache.commons.codec.binary.Base64;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;

public class BaseTest {
    WebDriverWait webDriverWait;
    int maxRetries = 1;

    protected static AppiumDriver driver;
    protected static Properties props;
    protected static String dateTime;
    protected static AppiumDriverLocalService server;
    protected static ThreadLocal <String> platform = new ThreadLocal<String>();
    protected static ThreadLocal <String> deviceName = new ThreadLocal<String>();
    public String getPlatform() {
        return platform.get();
    }

    public void setPlatform(String platform2) {
        platform.set(platform2);
    }
    public String getDeviceName() {
        return deviceName.get();
    }

    public void setDeviceName(String deviceName2) {
        deviceName.set(deviceName2);
    }

    testUtils utils;

    InputStream inputStream;

    public BaseTest() {
    }

    @BeforeMethod
    public void beforeMethod(){
        ((CanRecordScreen) driver).startRecordingScreen();
    }
    @AfterMethod
    public void afterMethod(ITestResult result) throws FileNotFoundException {

    }
//    @BeforeSuite
//    public void beforeSuit(){
////    server = getAppiumServerDefault();
////    server.start();
////    }
//    @AfterSuite
////    public void afterSuit(){
////        server.stop();
//    }

    public AppiumDriverLocalService getAppiumServerDefault(){
        return AppiumDriverLocalService.buildDefaultService();
    }

    @Parameters({"platformName", "deviceName"})
    @BeforeTest
    public void beforeTest(@Optional String platformName, @Optional String deviceName) throws Exception {
        URL url;
        utils = new testUtils();
        dateTime = utils.getDateTime();
        try {

            props = new Properties();
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream == null) {
                throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
            }
            props.load(inputStream);
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", platformName);
            caps.setCapability("deviceName", deviceName);

            switch (platformName) {
                case "Android":
                    caps.setCapability("automationName", props.getProperty("androidAutomationName"));
//                    caps.setCapability("avd", "Pixel_3a_API_34_extension_level_7_arm64-v8a");
                    caps.setCapability("appPackage", props.getProperty("androidAppPackage"));
                    caps.setCapability("appActivity", props.getProperty("androidAppActivity"));
                    caps.setCapability("noReset", true);
                    caps.setCapability("avdLaunchTimeout", 300000);
                    caps.setCapability("readyTimeout", 180000);
                    caps.setCapability("newCommandTimeout", 300);
                    caps.setCapability("appWaitActivity", "*");
                    String androidAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                            + File.separator + "java" + File.separator + "app" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
//                    URL androidAppUrl = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
                    caps.setCapability("app", androidAppUrl);
                    caps.setCapability("udid", "emulator-5554");
                    url = new URL(props.getProperty("appiumUrl"));
                    driver = new AndroidDriver(url, caps);
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    break;

                case "iOS":
                    caps.setCapability("automationName", "XCUITest");
                    String iOSAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                            + File.separator + "java" + File.separator + "app" + File.separator + "My Demo App.app";
//                    String iOSAppUrl = getClass().getResource(props.getProperty("iOSAppLocation")).getFile();
//                    caps.setCapability("app", iOSAppUrl);
                    caps.setCapability("bundleId","com.saucelabs.mydemoapp.ios");
                    url = new URL(props.getProperty("appiumUrl"));
                    driver = new IOSDriver(url, caps);
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    break;
                default:
                    throw new Exception("Invalid platform:" + platformName);
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public void waitForVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));

    }

    public void click(WebElement element) {
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
        element.click();

    }

    public void typeText(WebElement element, String text, String log) throws Exception {
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(15));

        int retryCount = 0;
        while (retryCount < maxRetries) {
            try {
                webDriverWait.until(ExpectedConditions.visibilityOf(element));
                element.sendKeys(text);
//                System.out.println(getCurrentTimeDate()+" Typed: " +text+" - "+ log);
                break;
            } catch (Exception e) {
                retryCount++;
                System.out.println("Retry: " + retryCount + " to type to " + log);
                if (retryCount == maxRetries) {

                }
            }
        }
    }

    public void closeAppAndroid(){
        ((InteractsWithApps)driver).terminateApp("com.swaglabsmobileapp");
    }
    public void closeAppIOS(){
        ((InteractsWithApps)driver).terminateApp("com.saucelabs.mydemoapp.ios");
    }

    public void launchAppAndroid(){
        ((InteractsWithApps)driver).activateApp("com.swaglabsmobileapp");
    }
    public void launchAppIOS(){
        ((InteractsWithApps)driver).activateApp("com.saucelabs.mydemoapp.ios");
    }

//
//    public WebElement scrollToElement(){
//        return driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()" + ".description(\"Parent"))
//                .scrollIntoView(" + "new UiSelector().description("Child\"));");
//    }
    public void iOSscroll(){
        RemoteWebElement parent = (RemoteWebElement) driver.findElement(By.className("ime klase"));
        String parentID = parent.getId();
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("element", parentID);
        scrollObject.put("direction", "down");
        scrollObject.put("predicateString", "label == 'ADD TO CART");
        scrollObject.put("name", "element name");
        driver.executeScript("mobile:scroll", scrollObject);

    }

    public AppiumDriver getDriver(){
        return driver;
    }

    public String getDateTime(){
        return dateTime;
    }

//    String media = ((CanRecordScreen) driver).stopRecordingScreen();
//
//       if(result.getStatus() == 2){
//        Map<String,String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
//        String dir = "videos" + File.separator + params.get("platformName") + "_" + params.get("platformDevice")
//                + File.separator + dateTime + File.separator + result.getTestClass().getRealClass().getSimpleName();
//        File videoDir = new File(dir);
//        if(!videoDir.exists()){
//            videoDir.mkdirs();
//        }try {
//            FileOutputStream stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
//            byte[] decodedBytes = Base64.decodeBase64(media);
//            stream.write(decodedBytes);
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }



    @AfterTest
    public void afterTest() {
        driver.quit();
    }



}
