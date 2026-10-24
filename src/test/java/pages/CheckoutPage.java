package pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Page;

import data.User;

public class CheckoutPage {
    private final Page page;

    public CheckoutPage(Page page) {
        this.page = page;
    }

    public void checkCart(String productName, String quantity){
        assertThat(page.getByTestId("product-title")).hasCount(1);;    
        assertThat(page.getByTestId("product-title")).containsText(productName);
        assertThat(page.getByTestId("product-quantity")).hasValue(quantity);

    }

    public void checkout(User adressUser){
        page.getByTestId("proceed-1").click();

        assertThat(page.getByTestId("login-submit")).not().isVisible();
        page.getByTestId("proceed-2").click();

        assertThat(page.getByTestId("country")).isVisible();
        assertThat(page.getByTestId("street")).not().hasValue("");
        assertThat(page.getByTestId("city")).not().hasValue("");
        page.getByTestId("country").selectOption(adressUser.country());
        page.getByTestId("postal_code").fill(adressUser.postalCode());
        page.getByTestId("house_number").fill(adressUser.houseNumber());
        page.getByTestId("state").fill("Alaska");

        assertThat(page.getByTestId("postal_code")).hasValue(adressUser.postalCode());
        assertThat(page.getByTestId("house_number")).hasValue(adressUser.houseNumber());
        assertThat(page.getByTestId("proceed-3")).isEnabled();
        page.getByTestId("proceed-3").click();

        assertThat(page.getByTestId("payment-method")).isVisible();
        page.getByTestId("payment-method").selectOption("cash-on-delivery");
        page.getByTestId("finish").click();
        assertThat(page.getByTestId("payment-success-message")).isVisible();
        assertThat(page.getByTestId("payment-success-message")).containsText("Payment was successful");
    }
}