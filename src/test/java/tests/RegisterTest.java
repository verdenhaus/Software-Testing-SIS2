package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.Test;

import data.User;
import data.UserFactory;
import fixtures.TestSetup;
import pages.RegisterPage;
import config.TestConfig;

public class RegisterTest extends TestSetup{
    @Test
    void registerUser(){
        User user = UserFactory.createUser();
        RegisterPage registerPage = new RegisterPage(page);

        registerPage.open();
        registerPage.registerUser(user);
        assertThat(page).hasURL(TestConfig.BASE_URL + "/auth/login");
    }
    
}
