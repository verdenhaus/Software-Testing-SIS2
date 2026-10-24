package pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import data.Product;

public class ProductPage {
    private final Page page;

    public ProductPage(Page page){
        this.page = page;
    }

    public void addProductToCart(String productName){
        assertThat(page.getByTestId("product-name")).hasText(productName);
        page.getByTestId("add-to-cart").click();
        assertThat(page.getByRole(AriaRole.ALERT)).containsText("Product added to shopping cart");
    }

    public Product getDetails(){
        String productName = page.getByTestId("product-name").innerText();

        String priceText = page.getByTestId("unit-price").innerText();
        float price = Float.parseFloat(priceText);

        String lengthText = page.getByTestId("product-specs")
        .locator("[data-test-spec='length']")
        .getByTestId("spec-value-text").innerText();
        Integer length = Integer.parseInt(lengthText);

        String weightText = page.getByTestId("product-specs")
        .locator("[data-test-spec='weight']")
        .getByTestId("spec-value-text").innerText();
        Integer weight = Integer.parseInt(weightText);

        return new Product(productName, price, length, weight);
    }

    public void compare(Product product1,Product product2){
        assertTrue(product1.price() > product2.price());
        assertFalse(product1.title() == product2.title());
        assertTrue(product1.length() > product2.length());
        assertFalse(product1.weight() < product2.weight());
    }
}
