package Tests;

import MethodClass.BaseTest;
import org.testng.annotations.*;
import pages.LoginPage;


public class LoginTests extends BaseTest {

    LoginPage loginPage;


    @BeforeClass
    public void beforeClass() {
    }

    @AfterClass
    public void afterClass() {

    }

    @BeforeMethod
    public void beforeMethod() {
        loginPage = new LoginPage();

    }

    @AfterMethod
    public void afterMethod() {

    }

    @Test
    public void loginProcess() throws Exception {
        loginPage.loginProcess();
        loginPage.closeAppAndroid();
        System.out.println("App closed");
    }

//    @Test
//    public void invalidLoginProcess() {
//        loginPage.invalidLogin();
//    }

//    public void invalidUsername() throws Exception {
//
//            loginPage.enterUsername("standard_userrr");
//            loginPage.enterPassword("secret_sauce");
//            loginPage.clickLogin();
//            loginPage.errorTextCheck();
//
//    }
//    @Test
//    public void invalidPassword() throws Exception {
//        loginPage.enterUsername("standard_user");
//        loginPage.enterPassword("secret_sauceeee");
//        loginPage.clickLogin();
//        loginPage.errorTextCheck();
//
//
//    }
//    @Test
//    public void successfulLogin() throws Exception {
//        loginPage.enterUsername("standard_user");
//        loginPage.enterPassword("secret_sauce");
//        loginPage.clickLogin();
//        productsPage.productTitle();
//
//    }

}

