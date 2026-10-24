package fixtures;

import com.microsoft.playwright.Browser;

public class AuthenticatedTestSetup extends TestSetup {
    @Override
    protected Browser.NewContextOptions contextOptions(){
        return new Browser.NewContextOptions().setStorageStatePath(AuthState.path());
    }
}
