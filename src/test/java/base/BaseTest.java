package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Dashboard;
import pages.Login;
import pages.Registration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Registration registration;
    protected Login login;
    protected Dashboard dashboard;


    @BeforeMethod(alwaysRun = true)
    public void setup() {
        System.out.println(">>> SETUP CALLED <<<");

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        registration = new Registration(driver, wait);
        login = new Login(driver, wait);
        dashboard = new Dashboard(driver, wait);
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println(">>> TEARDOWN CALLED <<<");

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}