package example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class EbayPurchaseTest {
    WebDriver driver;
    WebElement firstSearchResult;

    @BeforeTest
    @Parameters("browser")
    public void setup(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "/Users/anjula/ebay/resources/chromedriver");
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
            driver = new FirefoxDriver();
        }

        driver = new ChromeDriver();
    }

    @Test(priority = 0)
    public void navigateToEbay() {
        driver.get("https://www.ebay.com/");

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, "https://www.ebay.com/", "Failed to navigate to eBay.");
    }

    @Test(priority = 1)
    public void selectCellPhonesCategory() {
        WebElement categoryDropdown = driver.findElement(By.id("gh-shop-a"));
        categoryDropdown.click();

        WebElement cellPhonesCategory = driver.findElement(By.linkText("Cell phones & accessories"));
        cellPhonesCategory.click();

        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, "https://www.ebay.com/b/Cell-Phones-Smart-Watches-Accessories/15032/bn_1865441", "Incorrect category selected.");
    }

    @Test(priority = 2)
    public void performSearch() {
        WebElement searchInput = driver.findElement(By.id("gh-ac"));
        searchInput.click();
        String searchKey = "mobile phone";
        searchInput.sendKeys(searchKey);

        WebElement searchBtn = driver.findElement(By.id("gh-btn"));
        searchBtn.click();

        List<WebElement> searchResults = driver.findElements(By.className("s-item"));

        for (int i = 1; i < Math.min(6, searchResults.size()); i++) {
            WebElement searchResult = searchResults.get(i);
            String title = searchResult.findElement(By.className("s-item__title")).getText();
            String price = searchResult.findElement(By.className("s-item__price")).getText();

            System.out.println("Item " + i + " - Name: " + title + ", Price: " + price);
//            Assert.assertTrue(title.contains(searchKey),"Search result does not contain 'Mobile Phone' keyword.");
        }

        firstSearchResult = searchResults.get(1);

        WebElement itemLink = firstSearchResult.findElement(By.className("s-item__link"));
        itemLink.click();

        String searchResultTitle = firstSearchResult.findElement(By.className("s-item__title")).getText();

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        String selectedItemTitle = driver.findElement(By.className("x-item-title__mainTitle")).getText();

        Assert.assertEquals(selectedItemTitle, searchResultTitle, "The title and the price of the selected item does not match with the one that loads");

        Select colorSelect = new Select(driver.findElement(By.id("x-msku__select-box-1000")));
        colorSelect.selectByIndex(1);

        Select plugSelect = new Select(driver.findElement(By.id("x-msku__select-box-1001")));
        plugSelect.selectByIndex(1);

        WebElement quantity = driver.findElement(By.id("qtyTextBox"));
        quantity.click();
        quantity.clear();
        quantity.sendKeys(String.valueOf(1));

        WebElement addToCartBtn = driver.findElement(By.linkText("Add to cart"));
        addToCartBtn.click();

        String cartItemTitle = driver.findElement(By.className("item-title")).getText();
        System.out.println(cartItemTitle);
//        System.out.println(firstSearchResult.findElement(By.className("s-item__title")).getText());



    }

    @Test(priority = 2)
    void checkout() {
        WebElement checkoutBtn = driver.findElement(By.name("Go to checkout"));
        checkoutBtn.click();

        WebElement continueBtn = driver.findElement(By.id("gxo-btn"));
        continueBtn.click();

        Assert.assertEquals(driver.findElement(By.className("page-title ")).getText(), "Checkout");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
