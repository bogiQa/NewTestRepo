package pages;

import MethodClass.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class AndroidLoginPage extends BaseTest {
    public AndroidLoginPage() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);

    }
    @AndroidFindBy(accessibility = "test-Username")
    private WebElement usernameField;
    @AndroidFindBy(accessibility = "test-Password")
    private WebElement passwordField;
    @AndroidFindBy(accessibility = "test-LOGIN")
    private WebElement loginButton;
//    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
//    private WebElement errorText;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"PRODUCTS\"]")
    private WebElement productsTitleText;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]")
    private WebElement errorText;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Username is required\"]")
    private WebElement emptyErrorTxt;

    public void validLogin() throws Exception {
        typeText(usernameField,"standard_user","username entered");
        typeText(passwordField,"secret_sauce","password entered");
        click(loginButton);
        Assert.assertTrue(productsTitleText.isDisplayed());
    }

    public void invalidUsername() throws Exception {
        typeText(usernameField,"standard_userrrr","username entered");
        typeText(passwordField,"secret_sauce","password entered");
        click(loginButton);
        Assert.assertEquals(errorText.getText(),"Username and password do not match any user in this service.");
    }
    public void invalidUPassword() throws Exception {
        typeText(usernameField,"standard_user","username entered");
        typeText(passwordField,"secret_sauceeee","password entered");
        click(loginButton);
        Assert.assertEquals(errorText.getText(),"Username and password do not match any user in this service.");
    }
    public void emptyCredentials() throws Exception {
        typeText(usernameField,"","username empty");
        typeText(passwordField,"","password empty");
        click(loginButton);
        Assert.assertEquals(emptyErrorTxt.getText(),"Username is required");
    }












}


