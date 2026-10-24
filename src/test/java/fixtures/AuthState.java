package fixtures;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import data.UserFactory;
import pages.LoginPage;

public final class AuthState {
    private static final Path STORAGE_STATE = Paths.get("target", ".auth", "user.json");
    private static boolean ready = false;

    private AuthState() {}

    public static synchronized Path path(){
        if (!ready) {
            authenticate();
            ready = true;
        }
        return STORAGE_STATE;
    }

    private static void authenticate(){
        try (Playwright playwright = Playwright.create()) {
            playwright.selectors().setTestIdAttribute("data-test");
            Browser browser = playwright.chromium().launch();
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            LoginPage loginPage = new LoginPage(page);
            loginPage.open();
            loginPage.loginUser(UserFactory.loginUser());
            page.waitForURL("**/account");

            context.storageState(new BrowserContext.StorageStateOptions().setPath(STORAGE_STATE));
        }
    }
}
