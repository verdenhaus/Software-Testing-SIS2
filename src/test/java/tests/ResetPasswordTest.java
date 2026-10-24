package tests;

import org.junit.jupiter.api.Test;

import fixtures.TestSetup;
import pages.ResetPasswordPage;

public class ResetPasswordTest extends TestSetup{
    @Test 
    void forgotPassword(){
        ResetPasswordPage resetPasswordPage = new ResetPasswordPage(page);

        resetPasswordPage.open();
        resetPasswordPage.checkValidEmail("customer3@practicesoftwaretesting.com");
        resetPasswordPage.open();
        resetPasswordPage.checkInvalidEmail("val@practicesoftwaretesting.com");
    }
    
}
