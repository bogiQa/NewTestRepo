package Tests;

import MethodClass.BaseTest;
import listeners.TestListener;
import org.testng.annotations.*;
import pages.AndroidLoginPage;

@Listeners(TestListener.class)
public class AndroidLoginTests extends BaseTest {
    AndroidLoginPage androidLoginPage;



    @BeforeClass
    public void beforeClass() {
        closeAppAndroid();
    }

    @AfterClass
    public void afterClass() {
    }

    @BeforeMethod
    public void beforeMethod() {
        launchAppAndroid();
        androidLoginPage = new AndroidLoginPage();


    }

    @AfterMethod
    public void afterMethod() {
        closeAppAndroid();
    }

    @Test(description = "Valid login test")
    public void validLogin() throws Exception {
        System.out.println("Executing validLogin test");
        androidLoginPage.validLogin();

    }

    @Test(description = "Invalid login test")
    public void invalidUsername() throws Exception {
        System.out.println("Executing invalidUsername test");
        androidLoginPage.invalidUsername();
        androidLoginPage.invalidUPassword();
        androidLoginPage.emptyCredentials();
    }


}
