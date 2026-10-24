package pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import config.TestConfig;

public class ResetPasswordPage {
    private final Page page;

    public ResetPasswordPage(Page page) {
        this.page = page;
    }
    
    public void open(){
        page.navigate(TestConfig.BASE_URL + "/auth/forgot-password");
    }

    public void checkValidEmail(String email){
        page.getByTestId("email").fill(email);
        page.getByTestId("forgot-password-submit").click();
        assertThat(page.getByRole(AriaRole.ALERT)).containsText("page.forgot-password.confirm");
    }
    
    public void checkInvalidEmail(String email){
        page.getByTestId("email").fill(email);
        page.getByTestId("forgot-password-submit").click();
        assertThat(page.getByRole(AriaRole.ALERT)).containsText("The selected email is invalid.");
    }
}
