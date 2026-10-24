package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.Test;

import config.TestConfig;
import data.User;
import data.UserFactory;
import fixtures.TestSetup;
import pages.LoginPage;

public class LoginTest extends TestSetup{
    @Test 
    void loginUser(){
        User user = UserFactory.loginUser();
        LoginPage loginPage = new LoginPage(page);

        loginPage.open();
        loginPage.loginUser(user);
        assertThat(page).hasURL(TestConfig.BASE_URL + "/account");
    }

    @Test
    void invalidLoginUser(){
        User user = UserFactory.invalidLoginUser();
        LoginPage loginPage = new LoginPage(page);
        
        loginPage.open();
        loginPage.loginUser(user);
        loginPage.verifyErrorMessage();
        assertThat(page).not().hasURL(TestConfig.BASE_URL + "/account");
    
    }
    
}
