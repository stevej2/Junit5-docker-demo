import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Testcontainers
@DisplayName("Starts the docker container once, used for all test methods")
class ContainerStartedOnceTest {

    static String ContainerID_1 = "";
    static String ContainerID_2 = "";

    @Container
    private static final BrowserWebDriverContainer BROWSER_CONTAINER = new BrowserWebDriverContainer()
            .withCapabilities(new ChromeOptions());

    private static WebDriver browser;

    @BeforeAll
    static void configureBrowser() {
        browser = BROWSER_CONTAINER.getWebDriver();
    }

    @Test
    @DisplayName("The bbc.co.uk web site should have the correct title")
    void BBCWebSiteShouldHaveCorrectTitle() {
        ContainerID_1 = BROWSER_CONTAINER.getContainerId();
        System.out.println("Container ID Test 1: " + ContainerID_1);
        browser.get("https://www.bbc.co.uk");
        assertEquals("BBC - Home",browser.getTitle());
    }

    @Test
    @DisplayName("The google.co.uk web site should have the correct title")
    void GoogleWebSiteShouldHaveCorrectTitle() {
        ContainerID_2 = BROWSER_CONTAINER.getContainerId();
        System.out.println("Container ID Test 2: " + ContainerID_2);
        browser.get("https://www.google.co.uk");
        assertEquals("Google", browser.getTitle());
    }

    @AfterAll
    static void checkDifferentContainersWereUsed() {
        assertEquals(ContainerID_1, ContainerID_2, "Same container NOT used for all tests");
    }
}
