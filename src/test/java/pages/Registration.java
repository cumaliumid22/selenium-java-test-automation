package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Registration {

    WebDriver driver;
    WebDriverWait wait;

    public Registration(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Locators
    private By firstName = By.id("firstName");
    private By lastName = By.id("lastName");
    private By email = By.id("userEmail");
    private By phone = By.id("userMobile");
    private By password = By.id("userPassword");
    private By confirmPassword = By.id("confirmPassword");

    private By registerButton = By.id("login");

    private By errorDisplayed =
            By.xpath("//div[text()='*First Name must be 3 or more character long']");
    private By emailErrorDisplayed =
            By.xpath("//div[text()='*Enter Valid Email' or text()='*Email is required']");
    private By passwordErrorDisplayed =
            By.xpath("//div[text()='*Password is required']");
    private By confirmPasswordErrorDisplayed =
            By.xpath("//div[text()='Confirm Password is required' or text()='Password and Confirm Password must match with each other.']");
    private By phoneNumberErrorDisplayed =
            By.xpath("//div[text()='*Phone Number is required' or text()='*Phone Number must be 10 digit' or text()='*only numbers is allowed']");
    private By occupation = By.cssSelector("select[formcontrolname='occupation']");

    private By male =
            By.cssSelector("input[type='radio'][value='Male']");

    private By female =
            By.cssSelector("input[type='radio'][value='Female']");

    private By ageCheckbox =
            By.cssSelector("input[type='checkbox']");

    private By registrationSuccess =
            By.xpath("//div/h1[text()='Account Created Successfully']");

    private By loginButton =
            By.cssSelector(".login-wrapper-footer-text a");

    private By passwordComplexityError =
            By.xpath("//div[@role='alert' and contains(.,'Please enter 1 Special Character')]");

    private By passwordLengthError =
            By.xpath("//div[@role='alert' and normalize-space()='Password must be 8 Character Long!']");

    public boolean isPasswordComplexityErrorDisplayed() {
        try {
            WebElement error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(passwordComplexityError)
            );
            return error.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isPasswordLengthErrorDisplayed() {
        try {
            WebElement error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(passwordLengthError)
            );
            return error.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    // First Name
    public void enterFirstName(String value) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(firstName)
        ).sendKeys(value);
    }

    // Last Name
    public void enterLastName(String value) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(lastName)
        ).sendKeys(value);
    }

    // Email
    public void enterEmail(String value) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(email)
        ).sendKeys(value);
    }

    // Phone Number
    public void enterPhoneNumber(String value) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(phone)
        ).sendKeys(value);
    }

    // Password
    public void enterPassword(String value) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(password)
        ).sendKeys(value);
    }

    // Confirm Password
    public void enterConfirmPassword(String value) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(confirmPassword)
        ).sendKeys(value);
    }

    // Register Button
    public void clickRegister() {
        wait.until(
                ExpectedConditions.elementToBeClickable(registerButton)
        ).click();
    }

    // First Name Error Message
    public boolean isFirstNameErrorDisplayed() {
        try {
            WebElement error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(errorDisplayed)
            );
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordErrorDisplayed() {
        try {
            WebElement error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(passwordErrorDisplayed)
            );
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isConfirmPasswordErrorDisplayed() {
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordErrorDisplayed));
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmailErrorDisplayed() {
        try {
            WebElement error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(emailErrorDisplayed)
            );
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }

    }

    public boolean isPhoneErrorDisplayed() {
        try {
            WebElement error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(phoneNumberErrorDisplayed)
            );
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Occupation Dropdown
    public void selectOccupation(String occupationValue) {

        Select select = new Select(
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(occupation)
                )
        );

        select.selectByVisibleText(occupationValue);
    }

    // Gender Radio Button
    public void selectGender(String value) {

        if (value.equalsIgnoreCase("Male")) {

            wait.until(
                    ExpectedConditions.elementToBeClickable(male)
            ).click();

        } else if (value.equalsIgnoreCase("Female")) {

            wait.until(
                    ExpectedConditions.elementToBeClickable(female)
            ).click();
        }

    }

    public boolean isGenderSelected(String value) {
        try {
            if (value.equalsIgnoreCase("Male")) {
                return wait.until(ExpectedConditions.elementToBeClickable(male)
                ).isSelected();
            } else if (value.equalsIgnoreCase("Female")) {
                return wait.until(ExpectedConditions.elementToBeClickable(female))
                        .isSelected();
            }
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    // Age Confirmation Checkbox
    public void selectAgeConfirmation() {

        wait.until(
                ExpectedConditions.elementToBeClickable(ageCheckbox)
        ).click();
    }

    public void checkPasswords() {
        System.out.println("Password field: " +
                driver.findElement(password).getAttribute("value"));

        System.out.println("Confirm field: " +
                driver.findElement(confirmPassword).getAttribute("value"));
    }

    public boolean isRegistrationSuccessDisplayed() {
        try {
            WebElement success =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(registrationSuccess));
            return success.isDisplayed();
        } catch (Exception e) {
            return false;
        }

    }

    public String getSelectedOccupation() {
        return new Select(
                wait.until(ExpectedConditions.visibilityOfElementLocated(occupation)
                )
        ).getFirstSelectedOption().getText();
    }

    public void clickLoginLink() {
        WebElement login = wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginButton)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", login
        );
    }
}