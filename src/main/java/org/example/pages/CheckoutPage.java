package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutPage {
    private final WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getCheckoutButton() {
        return driver.findElement(By.name("Go to checkout"));
    }

    public WebElement getContinueButton() {
        return driver.findElement(By.id("gxo-btn"));
    }

    public String getPageTitle() {
        return driver.findElement(By.className("page-title")).getText();
    }
}
