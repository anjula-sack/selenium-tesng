package example;

import org.example.pages.CheckoutPage;
import org.example.pages.HomePage;
import org.example.pages.ProductPage;
import org.example.pages.SearchResultsPage;
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
    private WebDriver driver;
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ProductPage productPage;
    private CheckoutPage checkoutPage;

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

        homePage = new HomePage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        productPage = new ProductPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test(priority = 0)
    public void navigateToEbay() {
        homePage.openHomePage("https://www.ebay.com/");
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, "https://www.ebay.com/", "Failed to navigate to eBay.");
    }

    @Test(priority = 1)
    public void selectCellPhonesCategory() {
        homePage.getCategoryDropdown().click();
        homePage.getCellPhoneCategory().click();

        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, "https://www.ebay.com/b/Cell-Phones-Smart-Watches-Accessories/15032/bn_1865441", "Incorrect category selected.");
    }

    @Test(priority = 2)
    public void performSearch() {
        homePage.getSearchInput().click();
        String searchKey = "mobile phone";
        homePage.getSearchInput().sendKeys(searchKey);
        homePage.getSearchButton().click();

        List<WebElement> searchResults = searchResultsPage.getSearchResults();

        for (int i = 1; i < Math.min(6, searchResults.size()); i++) {
            WebElement searchResult = searchResults.get(i);
            String title = searchResult.findElement(By.className("s-item__title")).getText();
            String price = searchResult.findElement(By.className("s-item__price")).getText();

            System.out.println("Item " + i + " - Name: " + title + ", Price: " + price);
//            Assert.assertTrue(title.contains(searchKey),"Search result does not contain 'Mobile Phone' keyword.");
        }

        WebElement firstSearchResult = searchResults.get(1);

        WebElement itemLink = searchResultsPage.getItemLink(firstSearchResult);
        itemLink.click();

        String searchResultTitle = firstSearchResult.findElement(By.className("s-item__title")).getText();

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        String selectedItemTitle = driver.findElement(By.className("x-item-title__mainTitle")).getText();

        Assert.assertEquals(selectedItemTitle, searchResultTitle, "The title and the price of the selected item does not match with the one that loads");

        Select colorSelect = new Select(productPage.getColorSelect());
        colorSelect.selectByIndex(1);

        Select plugSelect = new Select(productPage.getPlugSelect());
        plugSelect.selectByIndex(1);

        WebElement quantity = productPage.getQuantity();
        quantity.click();
        quantity.clear();
        quantity.sendKeys(String.valueOf(1));

        productPage.getAddToCartButton().click();

        String cartItemTitle = driver.findElement(By.className("item-title")).getText();
        System.out.println(cartItemTitle);
    }

    @Test(priority = 2)
    void checkout() {
        checkoutPage.getCheckoutButton().click();
        checkoutPage.getContinueButton().click();

        Assert.assertEquals(checkoutPage.getPageTitle(), "Checkout");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
