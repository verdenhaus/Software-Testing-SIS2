# SIS2 - Test Automation with Playwright
## List of Test Cases to be automated
1) Register a new customer
2) Log in with valid/invalid credentials
3) Buy a product
4) Compare two products
5) Reset a password

## Description
### 1) Register a new customer
A unique new account is created and the user ends up signed in or on the login page.

[RegisterText.java](src/test/java/tests/RegisterTest.java) covers a customer registration test using `registerUser()` test method. First, [`createUser()`](src/test/java/data/UserFactory.java#L7) method creates a new user with a unique email utilising `UUID.randomUUID()` method. Further, [`registerUser()`](src/test/java/pages/RegisterPage.java#L19) method fills out the form, using assertions to make sure the street, city and state fields are autofilled. Once the button is pressed, another assertion is made to confirm registraion was successful and we were redirected to login page.

### 2) Log in with valid/invalid credentials
Valid credentials reach the account page, invalid ones show the error and do not.

[LoginTest.java](src/test/java/tests/LoginTest.java) covers login operations. [`loginUser()`](src/test/java/tests/LoginTest.java#L15) method logs in with valid credentials, using [`loginUser()`](src/test/java/data/UserFactory.java#L17) method to create a user with valid credentials and passing them to [`loginUser()`](src/test/java/pages/LoginPage.java#L21) to fill out the form with. Further assertion validates if account page was reached. Similarly [`invalidLoginUser()`](src/test/java/tests/LoginTest.java#L25) checks page response for invalid user, verifying an error message is shown with [`verifyErrorMessage()`](src/test/java/pages/LoginPage.java#L27), and account page was not reached. (I genuinely didn't realise I named all the methods loginUser() till writing md, sorry if it's confusing lol)

### 3) Buy a product
Verifies add to cart -> cart shows the right item and quantity -> checkout completes flow.

[`BuyProductTest.java`](src/test/java/tests/BuyProductTest.java) handles this test case with [`buyProduct()`](src/test/java/tests/BuyProductTest.java#L14) method. A user is created to fill the Billing address, while sign in part is technically skipped as we're using signed in state. Then a product is selected using [`selectProduct()`](src/test/java/pages/HomePage.java#L18) method, further being added to cart by [`addProductToCart()`](src/test/java/pages/ProductPage.java#L19), that also checks if the right product was selected and if the product was added to cart with assertions. Going back to cart, [`checkCart()`](src/test/java/pages/CheckoutPage.java#L16) method ensures that the right item and quantity are in the cart. If everything is correct, [`checkout()`] method handles the checkout. Both proceed buttons are pressed before Billing address form is handled. Since we're using a seeded account `customer3@practicesoftwaretesting.com` from [Practice Software Testing](https://testsmith-io.github.io/practice-software-testing/#/), the method checks if street and city fields are filled before proceeding to fill the rest, further choosing payment method and finishing the purchase. The method also checks for a confirmation message.

### 4) Compare two products
Open two products, capture name and price (weight and length for extra comparisons), assert the comparison we state.

[`CompareProductsTest.java`](src/test/java/tests/CompareProductsTest.java) handles comparison in [`compareProducts()`](src/test/java/tests/CompareProductsTest.java#L13) method. Two product instances are first created using [`newProduct()`](src/test/java/data/ProductFactory.java#L4) method. Then, using [`selectProduct()`](src/test/java/pages/HomePage.java#L18) one after another product pages are opened to retrieve details using [`getDetails()`](src/test/java/pages/ProductPage.java#L25) method. Finally, the [`compare()`](src/test/java/pages/ProductPage.java#L44) compares the two products as following:
1) If the p1 price is greater than p2
2) if product1 title name is different than product2
3) If product1 length is more than product2
4) If product1 weight isn't less than product2

### 5) Reset a password
The forgot-password flow gives the expected confirmation for a known email, and an error for an invalid one

[`ResetPasswordTest.java`](src/test/java/tests/ResetPasswordTest.java) checks the forgot password flow using [`forgotPassword()`](src/test/java/tests/ResetPasswordTest.java#L10) method. First the forgot password page is opened before [`checkValidEmail()`](src/test/java/pages/ResetPasswordPage.java#21) method validates if existing email shows confirmation. Likewise, [`checkInvalidEmail()`](src/test/java/pages/ResetPasswordPage.java#27) checks if non existent email shows an error. The alert messages don't have data-test ID's, and CSS classes are prohibited from using, hence text messages were used to validate success/danger alerts.

---
## Other files
### Configuration
[`TestConfig.java`](src/test/java/config/TestConfig.java)
Stores the base url as a constant to be reused for tests.
### Data factory
[`ProductFactory.java`](src/test/java/data/ProductFactory.java) provides product instances using [`Product`](src/test/java/data/Product.java) record.  
[`UserFanctory.java`](src/test/java/data/UserFactory.java) provides user instances and methods using [`User`](src/test/java/data/User.java) record.
### Fixtures
[`TestSetup.java`](src/test/java/fixtures/TestSetup.java) provides set up and closure for tests. Before each test, it initializes Playwright, configures data-test as the test ID attribute, launches Chromium, creates a new browser context and page. After each test, it closes the context, browser, and Playwright instance. The [`contextOptions()`](src/test/java/fixtures/TestSetup.java#L26) method allows browser context options to be customized by subclasses.  
[`AuthenticatedTestSetup.java`](src/test/java/fixtures/AuthenticatedTestSetup.java) extends [`TestSetup.java`](src/test/java/fixtures/TestSetup.java) and provides context with saved authentication state.  
[`AuthState.java`](src/test/java/fixtures/AuthState.java) is used by [`AuthenticatedTestSetup.java`](src/test/java/fixtures/AuthenticatedTestSetup.java) to handle authentication and saves the browser's state to a file. Logs in only once and reuses teh saved state for other tests.