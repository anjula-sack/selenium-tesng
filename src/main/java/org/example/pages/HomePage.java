package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void openHomePage(String url) {
        driver.get(url);
    }

    public WebElement getCategoryDropdown() {
        return driver.findElement(By.id("gh-shop-a"));
    }

    public WebElement getCellPhoneCategory() {
        return driver.findElement(By.linkText("Cell phones & accessories"));
    }
    public WebElement getSearchInput() {
        return driver.findElement(By.id("gh-ac"));
    }

    public WebElement getSearchButton() {
        return driver.findElement(By.id("gh-btn"));
    }
}
