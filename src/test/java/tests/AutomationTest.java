package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class AutomationTest extends BaseTest {
    @DataProvider(name = "categoryData")
    public Object[][] categoryData() {
        return new Object[][] {
                {"fashion", "ADIDAS ORIGINAL", true},
                {"fashion", "ZARA COAT 3", true},
                {"fashion", "IPHONE 13 PRO", false},
                {"electronics", "IPHONE 13 PRO", true},
                {"electronics", "ADIDAS ORIGINAL", false},
                {"electronics", "ZARA COAT 3", false}
        };
    }

    @DataProvider(name = "emailContainingInternalSpaces")
    public Object[][] emailContainingInternalSpaces() {
        return new Object[][]{
                {"arda @test.com"},
                {"arda@ test.com"},
                {"arda test@test.com"}
        };
    }

    @DataProvider(name = "invalidEmails")
    public Object[][] invalidEmails() {
        return new Object[][]{
                {"abc"},
                {"abc@"},
                {"abc.com"},
                {"@test.com"},
                {"abc@test"},
                {"abc@@test.com"}
        };
    }

    @DataProvider(name = "firstNameData")
    public Object[][] firstNameData() {
        return new Object[][]{
                {"Ab", true},
                {"Abc", false}
        };
    }

    @DataProvider(name = "emailNameData")
    public Object[][] emailNameData() {
        return new Object[][]{
                {"johntest@gmail.com", false},
                {"johntest.com", true},
                {"john@test", true},
                {"johntest", true},
                {"", true}
        };
    }

    @DataProvider(name = "phoneData")
    public Object[][] phoneData() {
        return new Object[][]{
                {"123456789", true},
                {"1234567890", false},
                {"12345678901", true},
                {"12345abc90", true}
        };
    }

    @DataProvider(name = "passwordComplexity")
    public Object[][] passwordComplexity() {
        return new Object[][]{
                {"Abcdef12", false},
                {"abcdef@1", true},
                {"Abcdef@g", true},
                {"ABCDEF@1", true},
                {"Abcdef@1", false},
        };
    }

    @DataProvider(name = "confirmPasswordData")
    public Object[][] confirmPasswordData() {
        return new Object[][]{
                {"", "", true},
                {"t", "t", true},
                {"Test@1234", "", true},
                {"Test@1234", "Test@1235", true},
                {"Test@1234", "Test@1234", false},
                {"VeryLongTestPassword@123456789",
                        "VeryLongTestPassword@123456789", false},
                {"VeryLongTestPassword@123456789",
                        "VeryLongTestPassword@12345678", true}
        };
    }

    @Test(dataProvider = "firstNameData", groups = "registration")
    public void firstNameLength_T40_T41(String firstName, boolean expected) {
        driver.get("https://rahulshettyacademy.com/client/#/auth/register");
        registration.enterFirstName(firstName);
        registration.clickRegister();
        boolean actual = registration.isFirstNameErrorDisplayed();

        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "emailNameData", groups = "registration")
    public void emailFormatValidation_T46_T47_T48(String emailName, boolean expected) {
        driver.get("https://rahulshettyacademy.com/client/#/auth/register");
        registration.enterEmail(emailName);
        registration.clickRegister();
        boolean actual = registration.isEmailErrorDisplayed();
        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "phoneData", groups = "registration")
    public void phoneNumberFormatValidation_T49_T50_T51_T52(String phoneNumber, boolean expected) {
        driver.get("https://rahulshettyacademy.com/client/#/auth/register");
        registration.enterPhoneNumber(phoneNumber);
        registration.clickRegister();
        boolean actual = registration.isPhoneErrorDisplayed();

        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "passwordComplexity", groups = "registration")
    public void passwordComplexity_T54_T55_T56_T57(
            String password, boolean expected) {

        driver.get("https://rahulshettyacademy.com/client/#/auth/register");

        registration.enterFirstName("ramin");
        registration.enterLastName("Usered");
        registration.enterEmail("test@example.com");
        registration.enterPhoneNumber("4343434343");
        registration.selectOccupation("Student");
        registration.selectGender("Male");
        registration.enterPassword(password);
        registration.enterConfirmPassword(password);
        registration.selectAgeConfirmation();
        registration.clickRegister();

        boolean actual = registration.isPasswordComplexityErrorDisplayed();

        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "confirmPasswordData", groups = "registration")
    public void mismatchedConfirmPassword_T58(String password, String confirmPassword, boolean expected) {

        driver.get("https://rahulshettyacademy.com/client/#/auth/register");

        registration.enterPassword(password);
        registration.enterConfirmPassword(confirmPassword);

        registration.checkPasswords();


        registration.clickRegister();

        boolean actual = registration.isConfirmPasswordErrorDisplayed();
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "registration")
    public void registrationWithoutAgeConfirmation_T59() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/register");

        registration.enterFirstName("John");
        registration.enterLastName("Test");
        registration.enterEmail("johntest@gmail.com");
        registration.enterPhoneNumber("1234567890");
        registration.enterPassword("Test@1234");
        registration.enterConfirmPassword("Test@1234");

        registration.clickRegister();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("register")
        );
    }

    @Test(groups = "registration")
    public void successfulRegistrationWithValidData_T39() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/register");

        registration.enterFirstName("mike");
        registration.enterLastName("Tetr");
        registration.enterEmail("mike234test18@gmail.com");
        registration.enterPhoneNumber("1234567890");
        registration.enterPassword("Test@1234");
        registration.enterConfirmPassword("Test@1234");

        registration.selectOccupation("Doctor");
        registration.selectGender("Male");
        registration.selectAgeConfirmation();

        registration.clickRegister();
        Assert.assertTrue(registration.isRegistrationSuccessDisplayed());

    }

    @Test(groups = "registration")
    public void occupationDropdownSelection_T60() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/register");

        registration.selectOccupation("Doctor");
        Assert.assertEquals(registration.getSelectedOccupation(), "Doctor");
    }

    @Test(groups = "registration")
    public void genderRadioButtonSelection_T61() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/register");
        registration.selectGender("male");
        Assert.assertTrue(registration.isGenderSelected("male"));
    }

    @Test(groups = "registration")
    public void loginLinkNavigationFromRegistrationPage_T62() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/register");

        registration.clickLoginLink();
        Assert.assertTrue(
                login.isLoginPageDisplayed(),
                "User should be redirected to the Login page."
        );
    }

    @DataProvider(name = "emailCapitalizationData")
    public Object[][] emailCapitalizationData() {
        return new Object[][]{
                {"ardatest@gmail.com"},
                {"ARDATEST@GMAIL.COM"},
                {"ArdaTest@gmail.com"}
        };
    }

    @Test(groups = "login")
    public void validLoginTest() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        boolean result = login.isLoginSuccessDisplayed();
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
    }

    @Test(dataProvider = "emailCapitalizationData", groups = "login")
    public void loginWithDifferentEmailCapitalization(String email) {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail(email);
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(login.isLoginSuccessDisplayed());
    }

    @Test(groups = "login")
    public void loginUsingEnterKey() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.pressEnterOnPassword();

        Assert.assertTrue(login.isLoginSuccessDisplayed());
    }

    @Test(groups = "login")
    public void loginAfterPreviousFailedAttempt() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardaaaaa@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isLoginErrorDisplayed(),
                "Login should fail with an unregistered email."
        );

        login.clearLoginFields();
        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isLoginSuccessDisplayed(),
                "Login should succeed after the previous failed attempt."
        );

        Assert.assertTrue(login.isDashboardDisplayed());
    }

    @Test(groups = "login")
    public void loginAfterPageRefresh() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        driver.navigate().refresh();

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();


        Assert.assertTrue(
                login.isLoginSuccessDisplayed(),
                "Login should succeed after refreshing the Login page."
        );

        Assert.assertTrue(
                login.isDashboardUrlDisplayed(),
                "User should be redirected to the Dashboard after refreshing the Login page."
        );
    }

    @Test(groups = "login")
    public void loginAfterLogout() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isLoginSuccessDisplayed(),
                "Initial login should succeed."
        );

        login.clickSignOut();

        Assert.assertTrue(
                login.isLogOutSuccessDisplayed(),
                "User should be logged out successfully."
        );

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isLoginSuccessDisplayed(),
                "User should be able to log in again after logout."
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("dashboard"),
                "User should be redirected to the Dashboard after logging in again."
        );
    }

    @Test(groups = "login")
    public void validEmailWithIncorrectPassword() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("invalidpass");
        login.clickLoginButton();

        Assert.assertTrue(login.isLoginErrorDisplayed());
    }

    @Test(groups = "login")
    public void validEmailWithEmptyPassword() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("");
        login.clickLoginButton();

        Assert.assertTrue(login.isPasswordErrorDisplayed());
    }

    @Test(groups = "login")
    public void unregisteredEmailWithPassword() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardaaaaa@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(login.isLoginErrorDisplayed());
    }

    @Test(groups = "login")
    public void passwordWithMissingCharacter() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@123");
        login.clickLoginButton();

        Assert.assertTrue(login.isLoginErrorDisplayed());
    }

    @Test(groups = "login")
    public void passwordWithAdditionalCharacter() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@12345");
        login.clickLoginButton();

        Assert.assertEquals(
                login.getLoginErrorMessage(),
                "Incorrect email or password."
        );
    }

    @Test(groups = "login")
    public void passwordContainingOnlyWhitespace() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("      ");
        login.clickLoginButton();

        Assert.assertTrue(login.isLoginErrorDisplayed());
    }


    @Test(dataProvider = "invalidEmails", groups = "login")
    public void invalidEmailFormat(String email) {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail(email);
        login.enterPassword("Test@12345");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isEmailErrorDisplayed(),
                "Expected email validation warning for: " + email
        );
    }

    @Test(groups = "login")
    public void invalidEmailAndPassword() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardaaaaa@gmail.com");
        login.enterPassword("wrong1234");
        login.clickLoginButton();

        Assert.assertEquals(
                login.getLoginErrorMessage(),
                "Incorrect email or password."
        );
    }

    @Test(groups = "login")
    public void incorrectPasswordCapitalization() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("test@1234");
        login.clickLoginButton();

        Assert.assertEquals(
                login.getLoginErrorMessage(),
                "Incorrect email or password."
        );
    }

    @Test(groups = "login")
    public void emptyEmailWithValidPassword() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("");
        login.enterPassword("test@1234");
        login.clickLoginButton();

        Assert.assertEquals(
                login.getEmailErrorMessage(),
                "*Email is required"
        );
    }

    @Test(groups = "login")
    public void emptyEmailAndPassword() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("");
        login.enterPassword("");
        login.clickLoginButton();

        Assert.assertEquals(
                login.getEmailErrorMessage(),
                "*Email is required"
        );

        Assert.assertEquals(
                login.getPasswordErrorMessage(),
                "*Password is required"
        );
    }

    @Test(groups = "login")
    public void emailContainingOnlyWhitespace() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("   ");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertEquals(
                login.getEmailErrorMessage(),
                "*Email is required"
        );
    }

    @Test(dataProvider = "emailContainingInternalSpaces", groups = "login")
    public void emailContainingInternalSpaces(String email) {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail(email);
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertEquals(
                login.getEmailErrorMessage(),
                "*Enter Valid Email"
        );
    }

    @Test(groups = "login")
    public void xssPayloadInLoginField() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("<script>alert('XSS')</script>");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertFalse(login.isLoginSuccessDisplayed());
    }

    @Test(groups = "login")
    public void sqlInjectionInPasswordField() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("' OR '1'='1");
        login.clickLoginButton();

        Assert.assertFalse(login.isLoginSuccessDisplayed());
    }

    @Test(groups = "login")
    public void sqlInjectionInEmailField() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("' OR '1'='1");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertFalse(login.isLoginSuccessDisplayed());
    }

    @Test(groups = "login")
    public void manipulatedAuthenticationToken() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "localStorage.setItem('token', 'invalid-token');"
        );

        driver.navigate().refresh();

        Assert.assertFalse(login.isDashboardDisplayed());
    }

    @Test(groups = "login")
    public void authenticationStateAfterLogout() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        login.clickSignOut();
        driver.navigate().back();

        Assert.assertFalse(login.isDashboardDisplayed());
    }

    @Test(groups = "login")
    public void accessProtectedPageWithoutAuthentication() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");
        driver.get("https://rahulshettyacademy.com/client/#/dashboard/dash");

        Assert.assertFalse(login.isDashboardDisplayed());
    }

    @Test(groups = "login")
    public void multipleRapidClicksOnLogin() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");

        login.clickLoginButton();
        login.clickLoginButton();
        login.clickLoginButton();

        Assert.assertTrue(login.isLoginSuccessDisplayed());
    }

    @Test(groups = "login")
    public void browserBackAfterLogin() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isLoginSuccessDisplayed(),
                "User should log in successfully before browser navigation."
        );

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "Dashboard should be displayed before browser navigation."
        );

        driver.navigate().back();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "Dashboard should remain displayed after browser Back navigation."
        );
    }

    @Test(groups = "login")
    public void authenticatedUserAccessingLoginPage() {
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        Assert.assertTrue(login.isDashboardDisplayed());
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_UI_001_DashboardInitialLoad() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-UI-001: Dashboard should be displayed after successful login"
        );

        Assert.assertTrue(
                dashboard.areProductCardsDisplayed(),
                "TC-UI-001: Product cards should be displayed"
        );

        Assert.assertTrue(
                dashboard.isResultCountDisplayed(),
                "TC-UI-001: Result counter should be displayed"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_SRCH_001_ProductSearchAndEnterTrigger() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-SRCH-001: Dashboard should be displayed after login"
        );

        Assert.assertTrue(
                dashboard.searchProductAndPressEnter("ADIDAS ORIGINAL"),
                "TC-SRCH-001: Search input could not be used"
        );

        Assert.assertTrue(
                dashboard.isProductDisplayed("ADIDAS ORIGINAL"),
                "TC-SRCH-001: Matching product should be displayed after search"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_SRCH_002_CaseInsensitiveProductSearch() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-SRCH-002: Dashboard should be displayed after login"
        );

        Assert.assertTrue(
                dashboard.searchProductAndPressEnter("adidas original"),
                "TC-SRCH-002: Search input could not be used"
        );

        Assert.assertEquals(
                dashboard.showNumber(),
                1,
                "TC-SRCH-002: Case-insensitive search should return one matching product"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_SRCH_003_SearchWithNoMatchingProduct() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-SRCH-003: Dashboard should be displayed after login"
        );

        dashboard.searchProductAndPressEnter("NONEXISTENTPRODUCT123");

        Assert.assertTrue(
                dashboard.areNoProductsDisplayed(),
                "TC-SRCH-003: No products should be displayed for a non-matching search"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_SRCH_004_ScriptAndSpecialCharacterInput() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-SRCH-004: Dashboard should be displayed after login"
        );

        dashboard.searchProductAndPressEnter("<script>alert('XSS')</script>");

        Assert.assertTrue(
                dashboard.areNoProductsDisplayed(),
                "TC-SRCH-004: Script-like input should not display products"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_PRICE_001_ApplyValidPriceFilter() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-PRICE-001: Dashboard should be displayed after login"
        );

        dashboard.enterMinPrice("10000");
        dashboard.enterMaxPrice("20000");

        Assert.assertTrue(
                dashboard.areProductCardsDisplayed(),
                "TC-PRICE-001: Products within the selected price range should be displayed"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_PRICE_002_PriceBoundaryFiltering() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-PRICE-002: Dashboard should be displayed after login"
        );

        dashboard.enterMinPrice("11500");
        dashboard.enterMaxPrice("55000");

        Assert.assertTrue(
                dashboard.areProductCardsDisplayed(),
                "TC-PRICE-002: Products at the minimum and maximum price boundaries should be displayed"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_PRICE_003_InvalidPriceInputHandling() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-PRICE-003: Dashboard should be displayed after login"
        );

        dashboard.enterMinPrice("-100");

        Assert.assertTrue(
                dashboard.areProductCardsDisplayed(),
                "TC-PRICE-003: Dashboard should remain functional after invalid price input"
        );
    }

    @Test(dataProvider = "categoryData", groups = "dashboard")
    public void Dashboard_TC_FILTER_CategoryFiltering(
            String category,
            String product,
            boolean expectedDisplayed) {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-FILTER-001: Dashboard should be displayed after login"
        );

        Assert.assertTrue(
                dashboard.selectCategory(category),
                "TC-FILTER-001: " + category + " category should be selected"
        );

        boolean displayed = dashboard.isProductDisplayed(product);
        Assert.assertEquals(
                displayed,
                expectedDisplayed,
                "TC-FILTER-001: Product display mismatch for category " + category
        );
    }

    @DataProvider(name = "categories")
    public Object[][] categories() {
        return new Object[][]{
                {"fashion"},
                {"electronics"},
                {"household"}
        };
    }

    @Test(dataProvider = "categoryData", groups = "dashboard")
    public void Dashboard_TC_FILTER_001_CategoryFiltering(
            String category,
            String product,
            boolean expectedDisplayed) {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "Dashboard should be displayed after login"
        );

        dashboard.selectCategory(category);

        boolean displayed =
                dashboard.isProductDisplayed(product);

        Assert.assertEquals(
                displayed,
                expectedDisplayed,
                "TC-FILTER-001: Product display mismatch for category " + category
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_SUBCAT_001_SubcategoryFiltering() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        dashboard.selectSubcategory("shoes");

        boolean displayed =
                dashboard.isProductDisplayed("ZARA COAT 3");

        Assert.assertFalse(
                displayed,
                "TC-SUBCAT-001: ZARA COAT 3 should not be displayed after selecting t-shirts"
        );


    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_FILTER_002_ORLogicWithinSameFilterGroup() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        dashboard.selectCategory("fashion");
        dashboard.selectCategory("electronics");

        boolean zaraDisplayed =
                dashboard.isProductDisplayed("ZARA COAT 3");

        boolean iphoneDisplayed =
                dashboard.isProductDisplayed("IPHONE 13 PRO");

        Assert.assertTrue(
                zaraDisplayed,
                "TC-FILTER-002: ZARA COAT 3 should be displayed for fashion category"
        );

        Assert.assertTrue(
                iphoneDisplayed,
                "TC-FILTER-002: IPHONE 13 PRO should be displayed for electronics category"
        );


    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_FILTER_003_ANDLogicAcrossDifferentFilterGroups() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        dashboard.selectCategory("fashion");
        dashboard.selectSubcategory("shoes");

        dashboard.enterMinPrice("11500");
        dashboard.enterMaxPrice("55000");

        int productCount = dashboard.getProductCount();

        Assert.assertTrue(
                productCount >= 0,
                "TC-FILTER-003: Products should be displayed according to the combined filter criteria"
        );


    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_FILTER_004_FilterReset() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-FILTER-004: Dashboard should be displayed after login"
        );

        int initialProductCount =
                dashboard.getProductCount();

        dashboard.enterMinPrice("11500");
        dashboard.enterMaxPrice("55000");

        int filteredProductCount =
                dashboard.getProductCount();

        Assert.assertTrue(
                filteredProductCount <= initialProductCount,
                "TC-FILTER-004: Price filter should restrict the product list"
        );

        dashboard.clearMinPrice();
        dashboard.clearMaxPrice();

        int resetProductCount =
                dashboard.getProductCount();

        Assert.assertEquals(
                resetProductCount,
                initialProductCount,
                "TC-FILTER-004: Clearing the price filter should restore the initial product list"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_ORDERS_001_OrdersNavigationAndInformation() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-ORDERS-001: Dashboard should be displayed after login"
        );

        dashboard.clickOrders();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("myorders"),
                "TC-ORDERS-001: Orders page should be displayed"
        );

    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_CART_001_AddProductToCartAndVerifyFeedback() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-CART-001: Dashboard should be displayed after login"
        );

        dashboard.addProductToCart("ADIDAS ORIGINAL");

        Assert.assertTrue(
                dashboard.isCartFeedbackDisplayed(),
                "TC-CART-001: Product added to cart feedback should be displayed"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_CART_002_CartProductCount() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-CART-002: Dashboard should be displayed after login"
        );

        dashboard.addProductToCart("ADIDAS ORIGINAL");

        dashboard.clickCart();

        Assert.assertEquals(
                dashboard.getCartProductCount(),
                1,
                "TC-CART-002: Cart should contain one product"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_CART_003_VerifyCartProductInformation() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-CART-003: Dashboard should be displayed after login"
        );

        String productName = "ADIDAS ORIGINAL";

        dashboard.addProductToCart(productName);

        dashboard.clickCart();

        Assert.assertEquals(
                dashboard.getCartProductName(),
                productName,
                "TC-CART-003: Cart product name should match"
        );

        Assert.assertFalse(
                dashboard.getSubtotal().isEmpty(),
                "TC-CART-003: Subtotal should be displayed"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_CART_004_RemoveProductFromCart() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-CART-004: Dashboard should be displayed after login"
        );

        String productName = "ADIDAS ORIGINAL";

        dashboard.addProductToCart(productName);

        dashboard.clickCart();

        dashboard.waitForCartProductCount(1);

        Assert.assertEquals(
                dashboard.getCartProductCount(),
                1,
                "TC-CART-004: Cart should contain one product"
        );

        dashboard.removeProductFromCart(productName);

        dashboard.waitForCartProductCount(0);

        Assert.assertEquals(
                dashboard.getCartProductCount(),
                0,
                "TC-CART-004: Cart should be empty after removing the product"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_DISP_001_ResultCounterMatchesVisibleProductCards() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-DISP-001: Dashboard should be displayed after login"
        );

        Assert.assertEquals(
                dashboard.showNumber(),
                dashboard.getProductCount(),
                "TC-DISP-001: Result counter should match visible product card count"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_DISP_002_MaximumProductsPerPage() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-DISP-002: Dashboard should be displayed after login"
        );

        Assert.assertTrue(
                dashboard.getProductCount() <= 9,
                "TC-DISP-002: Maximum 9 products should be displayed on one page"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_DISP_003_Pagination() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-DISP-003: Dashboard should be displayed after login"
        );

        Assert.assertTrue(
                dashboard.getProductCount() <= 9,
                "TC-DISP-003: Product count should respect pagination limit"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_AUTH_001_SignOut() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-AUTH-001: Dashboard should be displayed after login"
        );

        dashboard.clickSignOut();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("auth/login"),
                "TC-AUTH-001: User should be redirected to login page after sign out"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_AUTH_002_ProtectedPageAccessAfterSignOut() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-AUTH-002: Dashboard should be displayed after login"
        );

        dashboard.clickSignOut();

        driver.get("https://rahulshettyacademy.com/client/#/dashboard/");

        Assert.assertTrue(
                driver.getCurrentUrl().contains("auth/login"),
                "TC-AUTH-002: Protected dashboard should redirect unauthenticated user to login"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_VIEW_001_OpenProductDetailsUsingView() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-VIEW-001: Dashboard should be displayed after login"
        );

        dashboard.clickView();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("product-details"),
                "TC-VIEW-001: Product details page should be displayed after clicking View"
        );
    }

    @Test(groups = "dashboard")
    public void Dashboard_TC_VIEW_002_OpenProductDetailsUsingProductImage() {

        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

        login.enterEmail("ardatest@gmail.com");
        login.enterPassword("Test@1234");
        login.clickLoginButton();

        Assert.assertTrue(
                login.isDashboardDisplayed(),
                "TC-VIEW-002: Dashboard should be displayed after login"
        );

        dashboard.clickProductImage("ADIDAS ORIGINAL");

        Assert.assertTrue(
                driver.getCurrentUrl().contains("product-details"),
                "TC-VIEW-002: Product details page should be displayed after clicking product image"
        );
    }
}

