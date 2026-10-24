package tests;

import org.junit.jupiter.api.Test;

import data.User;
import data.UserFactory;
import fixtures.AuthenticatedTestSetup;
import pages.CheckoutPage;
import pages.HomePage;
import pages.ProductPage;

public class BuyProductTest extends AuthenticatedTestSetup{
    @Test 
    void buyProduct(){
        User address = UserFactory.createUser();

        HomePage homePage = new HomePage(page);
        ProductPage productPage = new ProductPage(page);
        CheckoutPage checkoutPage = new CheckoutPage(page);

        homePage.open();
        homePage.selectProduct("Bolt Cutters");
        productPage.addProductToCart("Bolt Cutters");
        homePage.navigateToCart();
        checkoutPage.checkCart("Bolt Cutters", "1");
        checkoutPage.checkout(address);
    }
    
}
