package pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Page;

import config.TestConfig;
import data.User;

public class LoginPage {
    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void open(){
        page.navigate(TestConfig.BASE_URL + "/auth/login");
    }

    public void loginUser(User user){
        page.getByTestId("email").fill(user.email());
        page.getByTestId("password").fill(user.password());
        page.getByTestId("login-submit").click();
    }

    public void verifyErrorMessage(){
        assertThat(page.getByTestId("login-error")).isVisible();
    }
}
