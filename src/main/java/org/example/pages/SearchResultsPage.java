package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultsPage {
    private final WebDriver driver;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getSearchResults() {
        return driver.findElements(By.className("s-item"));
    }

    public WebElement getItemLink(WebElement searchResult) {
        return searchResult.findElement(By.className("s-item__link"));
    }
}
