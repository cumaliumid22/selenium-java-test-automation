package pages;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {
    WebDriver driver;
    WebDriverWait wait;

    public Login(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    private By email = By.id("userEmail");
    private By emailError =
            By.xpath("//div[text()='*Email is required' or text()='*Enter Valid Email']");
    private By password = By.id("userPassword");
    private By passwordError =
            By.xpath("//div[text()='*Password is required']");
    private By login = By.id("login");

    private By forgotPassword =
            By.cssSelector("a.forgot-password-link");

    private By loginError =
            By.xpath("//div[@id='toast-container']//div[contains(@class,'toast-error') and contains(.,'Incorrect email or password.')]");

    private By loginSuccess =
            By.xpath("//div[@id='toast-container']//div[contains(@class,'toast-success') and contains(.,'Login Successfully')]");

    private By logOut =
            By.xpath("//div[@aria-label='Logout Successfully']");
    private By signOut = By.xpath("//button[normalize-space()='Sign Out']");

    private By dashboard = By.cssSelector("button[routerlink='/dashboard/']");

    public boolean isDashboardDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(dashboard)
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailErrorMessage(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailError)).getText();
    }

    public String getLoginErrorMessage(){
        return  wait.until(ExpectedConditions.visibilityOfElementLocated(loginError)).getText();
    }

    public String getPasswordErrorMessage(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError)).getText();
    }

    public void clickSignOut(){
        wait.until(ExpectedConditions.elementToBeClickable(signOut)).click();
    }

    public boolean isLogOutSuccessDisplayed(){
        try{
            WebElement success = wait.until(ExpectedConditions.visibilityOfElementLocated(logOut));

            return success.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void pressEnterOnPassword() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(password)
        ).sendKeys(Keys.ENTER);
    }

    public void clearLoginFields() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(email)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(password)).clear();
    }

    public boolean isLoginSuccessDisplayed(){
        try {
            WebElement success = wait.until(ExpectedConditions.visibilityOfElementLocated(loginSuccess));
            return success.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isLoginErrorDisplayed(){
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(loginError));

            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public void enterEmail(String value){
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(email))
                .sendKeys(value);
    }

    public void enterPassword(String value){
        wait.until(ExpectedConditions.visibilityOfElementLocated(password))
                .sendKeys(value);
    }

    public boolean isEmailErrorDisplayed(){
        try{
            WebElement error =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(emailError));

        return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordErrorDisplayed(){
        try {
            WebElement error =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(
                            passwordError
                    ));
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLoginButton(){
        wait.until(ExpectedConditions.elementToBeClickable(login)).click();
    }

    public void clickForgotPasswordButton(){
        wait.until(ExpectedConditions.elementToBeClickable(
                forgotPassword
        )).click();
    }

    public boolean isDashboardUrlDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.urlContains("/dashboard")
            );
        } catch (TimeoutException e) {
            return false;
        }
    }


    public boolean isLoginPageDisplayed() {
        return wait.until(
                ExpectedConditions.urlContains("/auth/login"));
    }


}
