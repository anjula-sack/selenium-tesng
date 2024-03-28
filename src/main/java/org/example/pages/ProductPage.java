package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage {
    private final WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getColorSelect() {
        return driver.findElement(By.id("x-msku__select-box-1000"));
    }

    public WebElement getPlugSelect() {
        return driver.findElement(By.id("x-msku__select-box-1001"));
    }

    public WebElement getQuantity() {
        return driver.findElement(By.id("qtyTextBox"));
    }

    public WebElement getAddToCartButton() {
        return driver.findElement(By.linkText("Add to cart"));
    }
}
