package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Dashboard {

    private WebDriver driver;
    private WebDriverWait wait;

    public Dashboard(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private By homeButton =
            By.cssSelector("button[routerlink='/dashboard/']");

    private By ordersButton =
            By.cssSelector("button[routerlink='/dashboard/myorders']");

    private By cartButton =
            By.cssSelector("button[routerlink='/dashboard/cart']");

    private By signOutButton =
            By.xpath("//button[normalize-space()='Sign Out']");

    private By spinner =
            By.cssSelector(".ngx-spinner-overlay");

    private By searchInput =
            By.cssSelector("input[type='text'][placeholder='search']");

    private By minPriceInput =
            By.xpath("//div[contains(@class,'col-md-6')][.//input[@placeholder='Min Price']]//input[@placeholder='Min Price']");

    private By maxPriceInput =
            By.xpath("//div[contains(@class,'col-md-6')][.//input[@placeholder='Max Price']]//input[@placeholder='Max Price']");

    private By productCards =
            By.cssSelector(".mb-3");

    private By resultCount =
            By.xpath("//*[contains(text(),'Showing') and contains(text(),'results')]");

    private By showResult =
            By.id("res");

    private By viewButtons =
            By.xpath("//button[normalize-space()='View']");

    private By cartProduct =
            By.cssSelector("li.items");

    private By cartFeedback =
            By.xpath("//*[contains(normalize-space(), 'Product Added To Cart')]");

    private By subtotal =
            By.cssSelector(".subtotal .value");
    private By ordersInfo =
            By.xpath("//div[text()=' * If orders Will be more than 7 your last order will get deleted ']");

    private By cartProductName =
            By.cssSelector("div[class='cart'] h3");


    private By productImage(String productName) {
        return By.xpath(
                "//h5[normalize-space()='" + productName + "']" +
                        "/ancestor::div[contains(@class,'card')]//img"
        );
    }

    public void clickProductImage(String productName) {
        wait.until(
                ExpectedConditions.elementToBeClickable(productImage(productName))
        ).click();
    }

    public String getCartProductName() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartProductName)
        ).getText();
    }


    public void clickCart() {

        wait.until(
                ExpectedConditions.elementToBeClickable(cartButton)
        );

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                driver.findElement(cartButton)
        );
    }

    public void clickOrders() {

        wait.until(
                ExpectedConditions.elementToBeClickable(ordersButton)
        ).click();
    }

    public void clickSignOut() {

        wait.until(
                ExpectedConditions.elementToBeClickable(signOutButton)
        ).click();
    }

    private By deleteButton =
            By.cssSelector(".cartSection.removeWrap .btn.btn-danger");

    public void removeProductFromCart(String productName) {

        By product =
                By.xpath(
                        "//li[contains(@class,'items')]" +
                                "[.//h3[normalize-space()='" + productName + "']]"
                );

        By deleteButton =
                By.xpath(
                        "//li[contains(@class,'items')]" +
                                "[.//h3[normalize-space()='" + productName + "']]" +
                                "//button[contains(@class,'btn-danger')]"
                );

        wait.until(
                ExpectedConditions.elementToBeClickable(deleteButton)
        ).click();

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(product)
        );
    }

    public boolean isCartProductDisplayed(String productName) {

        By productNameLocator =
                By.xpath(
                        "//h3[normalize-space()='" + productName + "']"
                );

        return driver.findElements(productNameLocator).stream()
                .anyMatch(WebElement::isDisplayed);
    }

    public void waitForCartProductCount(int expectedCount) {

        wait.until(driver ->
                getCartProductCount() == expectedCount
        );
    }

    public boolean searchProductAndPressEnter(String productName) {

        try {
            List<WebElement> searchInputs =
                    driver.findElements(searchInput);

            for (WebElement search : searchInputs) {

                if (search.isDisplayed() && search.isEnabled()) {

                    search.click();
                    search.clear();
                    search.sendKeys(productName);
                    search.sendKeys(Keys.ENTER);

                    return true;
                }
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public void enterMinPrice(String minPrice) {

        List<WebElement> inputs =
                driver.findElements(minPriceInput);

        for (WebElement input : inputs) {

            if (input.isDisplayed() && input.isEnabled()) {

                input.click();
                input.clear();
                input.sendKeys(minPrice);
                input.sendKeys(Keys.ENTER);

                return;
            }
        }
    }

    public void enterMaxPrice(String maxPrice) {

        List<WebElement> inputs =
                driver.findElements(maxPriceInput);

        for (WebElement input : inputs) {

            if (input.isDisplayed() && input.isEnabled()) {

                input.click();
                input.clear();
                input.sendKeys(maxPrice);
                input.sendKeys(Keys.ENTER);

                return;
            }
        }
    }

    public void clearMinPrice() {

        List<WebElement> inputs =
                driver.findElements(minPriceInput);

        for (WebElement input : inputs) {

            if (input.isDisplayed() && input.isEnabled()) {

                input.click();
                input.clear();
                input.sendKeys(Keys.ENTER);

                return;
            }
        }
    }

    public void clearMaxPrice() {

        List<WebElement> inputs =
                driver.findElements(maxPriceInput);

        for (WebElement input : inputs) {

            if (input.isDisplayed() && input.isEnabled()) {

                input.click();
                input.clear();
                input.sendKeys(Keys.ENTER);

                return;
            }
        }
    }

    public boolean selectCategory(String name) {

        try {
            WebDriverWait categoryWait =
                    new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

            categoryWait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//label[normalize-space(text())='" + name + "']")
                    )
            );

            WebElement label =
                    driver.findElement(
                            By.xpath("//label[normalize-space(text())='" + name + "']")
                    );

            WebElement checkbox =
                    label.findElement(
                            By.xpath("./preceding-sibling::input[@type='checkbox']")
                    );

            try {
                checkbox.click();

            } catch (Exception e) {

                JavascriptExecutor js =
                        (JavascriptExecutor) driver;

                js.executeScript(
                        "arguments[0].click();",
                        checkbox
                );
            }

            return checkbox.isSelected();

        } catch (Exception e) {

            System.out.println("Category not found: " + name);
            return false;
        }
    }

    public boolean selectSubcategory(String name) {

        By labelLocator =
                By.xpath("//label[text()='" + name + "']");

        WebElement label =
                wait.until(
                        ExpectedConditions.presenceOfElementLocated(labelLocator)
                );

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                label
        );

        By checkboxLocator =
                By.xpath(
                        "//label[text()='" + name +
                                "']/preceding-sibling::input[@type='checkbox']"
                );

        WebElement checkbox =
                driver.findElement(checkboxLocator);

        return checkbox.isSelected();
    }

    public boolean areProductCardsDisplayed() {

        List<WebElement> products =
                driver.findElements(productCards);

        for (WebElement product : products) {

            if (product.isDisplayed()) {
                return true;
            }
        }

        return false;
    }

    public boolean areNoProductsDisplayed() {

        try {

            wait.until(driver -> {

                List<WebElement> products =
                        driver.findElements(productCards);

                for (WebElement product : products) {

                    if (product.isDisplayed()) {
                        return false;
                    }
                }

                return true;
            });

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public int getProductCount() {
        return driver.findElements(productCards).size();
    }

    public boolean isProductDisplayed(String productName) {

        List<WebElement> products =
                driver.findElements(productCards);

        for (WebElement product : products) {
            try {
                if (product.findElement(By.cssSelector("b"))
                        .getText()
                        .equals(productName)) {
                    return product.isDisplayed();
                }
            } catch (StaleElementReferenceException e) {
                return false;
            }
        }

        return false;
    }

    public List<String> getDisplayedProductNames() {

        List<String> names = new ArrayList<>();

        for (WebElement title :
                driver.findElements(By.cssSelector("h4"))) {

            if (title.isDisplayed()) {
                names.add(title.getText().trim());
            }
        }

        return names;
    }

    public void clickView() {

        wait.until(
                ExpectedConditions.elementToBeClickable(viewButtons)
        ).click();
    }

    public void addProductToCart(String productName) {

        By addToCartButton =
                By.xpath(
                        "//div[contains(@class,'card')]" +
                                "[.//h5[normalize-space()='" + productName + "']]" +
                                "//button[normalize-space()='Add To Cart']"
                );

        wait.until(
                ExpectedConditions.elementToBeClickable(addToCartButton)
        ).click();
    }

    public boolean isCartFeedbackDisplayed() {

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(cartFeedback)
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public int getCartProductCount() {

        List<WebElement> products =
                driver.findElements(cartProduct);

        return (int) products.stream()
                .filter(WebElement::isDisplayed)
                .count();
    }

    public String getCartProductInformation() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartProduct)
        ).getText();
    }

    public String getSubtotal() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(subtotal)
        ).getText();
    }

    public boolean isOrdersInfoDisplayed() {

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(ordersInfo)
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isResultCountDisplayed() {

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(resultCount)
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public int showNumber() {

        String result = wait.until(
                driver -> {
                    String text = driver.findElement(showResult).getText();
                    return text.matches(".*\\d+.*") ? text : null;
                }
        );

        return Integer.parseInt(
                result.replaceAll("[^0-9]", "")
        );
    }
}