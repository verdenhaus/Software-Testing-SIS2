package fixtures;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

public class TestSetup {
    private static final Path RESULTS_DIR = Paths.get("test-results");

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    private boolean failed;
    private String testName;

    @RegisterExtension
    final AfterTestExecutionCallback failureWatcher = extensionContext -> failed = extensionContext
            .getExecutionException().isPresent();

    @BeforeEach
    void setUp(TestInfo testInfo) {
        testName = testInfo.getTestClass().map(Class::getSimpleName).orElse("Test")
                + "." + testInfo.getTestMethod().map(m -> m.getName()).orElse("unknown");

        playwright = Playwright.create();
        playwright.selectors().setTestIdAttribute("data-test");
        browser = playwright.chromium().launch();
        context = browser.newContext(contextOptions());
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page = context.newPage();
    }

    protected Browser.NewContextOptions contextOptions() {
        return new Browser.NewContextOptions();
    }

    @AfterEach
    void kill() {
        try {
            if (context != null) {
                if (failed) {
                    Path dir = RESULTS_DIR.resolve(testName);
                    String attempt = String.valueOf(System.currentTimeMillis());
                    if (page != null && !page.isClosed()) {
                        page.screenshot(new Page.ScreenshotOptions()
                                .setPath(dir.resolve("failure-" + attempt + ".png"))
                                .setFullPage(true));
                    }
                    context.tracing().stop(new Tracing.StopOptions().setPath(dir.resolve("trace-" + attempt + ".zip")));
                } else {
                    context.tracing().stop();
                }
            }
        } finally {
            if (context != null)
                context.close();
            if (browser != null)
                browser.close();
            if (playwright != null)
                playwright.close();
        }
    }
}