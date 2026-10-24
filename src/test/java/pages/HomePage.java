package pages;

import com.microsoft.playwright.Page;

import config.TestConfig;

public class HomePage {
    private final Page page;

    public HomePage(Page page){
        this.page = page;
    }

    public void open(){
        page.navigate(TestConfig.BASE_URL);
    }

    public void selectProduct(String productName){
        page.getByText(productName, new Page.GetByTextOptions().setExact(true)).click();
    }

    public void navigateToCart(){
        page.getByTestId("nav-cart").click();
        page.waitForURL("**/checkout");
    }
}