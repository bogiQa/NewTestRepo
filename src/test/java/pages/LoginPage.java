package pages;

import MethodClass.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.HashMap;

public class LoginPage extends BaseTest {
    public LoginPage() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);

    }


    //    @AndroidFindBy(accessibility = "test-Username")
//    private WebElement usernameField;
//    @AndroidFindBy(accessibility = "test-Password")
//    private WebElement passwordField;
//    @AndroidFindBy(accessibility = "test-LOGIN")
//    private WebElement loginButton;
//    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
//    private WebElement errorText;
    @iOSXCUITFindBy(id = "More-tab-item")
    WebElement menuButton;
    @iOSXCUITFindBy(iOSNsPredicate = "name == \"LogOut-menu-item\"")
    WebElement loginMenuButton;
    @iOSXCUITFindBy(iOSNsPredicate = "type == \"XCUIElementTypeTextField\"")
    WebElement usernameField;
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeSecureTextField")
    WebElement passwordField;
    @iOSXCUITFindBy(iOSNsPredicate = "name == \"Login\" AND label == \"Login\" AND type == \"XCUIElementTypeButton\"")
    WebElement loginButton;
    @iOSXCUITFindBy(id = "Products")
    WebElement productsText;
    @iOSXCUITFindBy(iOSNsPredicate = "name == \"bob@example.com\" AND label == \"bob@example.com\" AND value == \"bob@example.com\"")
    WebElement selectUser;
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name == \"Validation Error!\"`]")
    WebElement errorPopUp;
    @iOSXCUITFindBy(iOSNsPredicate = "name == \"OK\"")
    WebElement okButton;


//    public void enterUsername(String username) throws Exception {
//        typeText(usernameTxtField, username, "Username entered");
//
//    }
//
//    public void enterPassword(String passwordField) throws Exception {
//        typeText(passwordTxtField, passwordField, "Password entered");
//
//    }
//
//    public void clickLogin() {
//        click(loginButton);
//
//    }
//
//    public void errorTextCheck() {
//        Assert.assertEquals(errorText.getText(), "Username and password do not match any user in this service.");
//    }

    public void loginProcess() throws Exception {
        click(menuButton);
        loginMenuButton.click();
        click(selectUser);
//        HashMap<String, String[]> map = new HashMap<String, String[]>();
//        String[] keys = {"Return"};
//        map.put("keys", keys);
//        ((IOSDriver) driver).executeScript("mobile: hideKeyboard", map);
        loginButton.click();
        Assert.assertTrue(productsText.isDisplayed());
    }

    public void invalidLogin(){
        click(menuButton);
        loginMenuButton.click();
        click(loginButton);
        Assert.assertTrue(errorPopUp.isDisplayed());
        okButton.click();

    }


}
