package tests;

import org.junit.jupiter.api.Test;

import data.Product;
import data.ProductFactory;
import fixtures.TestSetup;
import pages.HomePage;
import pages.ProductPage;

public class CompareProductsTest extends TestSetup{
    @Test
    void compareProducts(){
        HomePage homePage = new HomePage(page);
        ProductPage productPage = new ProductPage(page);
        Product product1 = ProductFactory.newProduct();
        Product product2 = ProductFactory.newProduct();
        
        homePage.open();
        homePage.selectProduct("Bolt Cutters");
        product1 = productPage.getDetails();
        homePage.open();
        homePage.selectProduct("Combination Pliers");
        product2 = productPage.getDetails();
        System.out.println(product1);
        System.out.println(product2);
        productPage.compare(product1,product2);
    }
    
}
