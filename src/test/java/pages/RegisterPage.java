package pages;

import com.microsoft.playwright.Page;
import config.TestConfig;
import data.User;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class RegisterPage {
    private final Page page;

    public RegisterPage(Page page){
        this.page = page;
    }

    public void open(){
        page.navigate(TestConfig.BASE_URL + "/auth/register");
    }

    public void registerUser(User user){
        page.getByTestId("first-name").fill(user.firstName());
        page.getByTestId("last-name").fill(user.lastName());
        page.getByTestId("dob").fill(user.dateOfBirth());
        page.getByTestId("country").selectOption(user.country());
        page.getByTestId("postal_code").fill(user.postalCode());
        page.getByTestId("house_number").fill(user.houseNumber());
        assertThat(page.getByTestId("street")).not().hasValue("");
        assertThat(page.getByTestId("city")).not().hasValue("");
        assertThat(page.getByTestId("state")).not().hasValue("");
        page.getByTestId("phone").fill(user.phone());
        page.getByTestId("email").fill(user.email());
        page.getByTestId("password").fill(user.password());

        page.getByTestId("register-submit").click();
    }
}
