package e2e.products;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import e2e.pages.ProductPage;

public class productDetailsTest {
    
    WebDriver web;

    @BeforeEach
    public void setUp() {
        this.web = new ChromeDriver();
        web.manage().window().maximize();
        web.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        web.get("https://www.saucedemo.com");

        WebElement usernameInput = web.findElement(By.id("user-name"));
        usernameInput.sendKeys("standard_user");
        
        WebElement passwordInput = web.findElement(By.id("password"));
        passwordInput.sendKeys("secret_sauce");

        WebElement loginButton = web.findElement(By.id("login-button"));
        loginButton.click();

        WebElement productList = web.findElement(By.cssSelector("[data-test='inventory-list']"));
        Boolean isProductListVisible = productList.isDisplayed();
        Assertions.assertTrue(isProductListVisible);
    }

    @AfterEach
    public void shutdown() {
        web.quit();
    }

    @Test
    public void ShouldProductDetailsBeVisible(){
        List<WebElement> products = web.findElements(By.cssSelector("[data-test=\"inventory-item\"]"));
        Assertions.assertFalse(products.isEmpty(), "No se encontraron productos en la página.");

        int randomIndex = (int) (Math.random() * products.size());

        WebElement randomProduct = products.get(randomIndex);
        
        ProductPage givenProduct = new ProductPage(web, randomProduct);

        String expectatedProductPrice = givenProduct.productPrice.get();
        String expectatedProductTitle = givenProduct.productTitle.get();
        String expectatedProductDescription = givenProduct.productDescription.get();
        String expectatedProductImage = givenProduct.productImage.get();
        String expectatedProductCartButtonState = givenProduct.getProductCartButtonState.get();
        
        givenProduct.productClickable.get().click();

        String actualUrl = web.getCurrentUrl();
        Assertions.assertTrue(actualUrl.contains("inventory-item.html?id"));

        WebElement actualProduct = web.findElement(By.cssSelector("[data-test=\"inventory-container\"]"));
        ProductPage actualProductCard = new ProductPage(web, actualProduct);

        String actualProductPrice = actualProductCard.productPrice.get();
        String actualProductTitle = actualProductCard.productTitle.get();
        String actualProductDescription = actualProductCard.productDescription.get();
        String actualProductImage = actualProductCard.productImage.get();
        String actualProductCartButtonState = actualProductCard.getProductCartButtonState.get();

        //Assertions
        Assertions.assertEquals(expectatedProductPrice, actualProductPrice);
        Assertions.assertEquals(expectatedProductTitle, actualProductTitle);
        Assertions.assertEquals(expectatedProductDescription, actualProductDescription);
        Assertions.assertEquals(expectatedProductImage, actualProductImage);
        Assertions.assertEquals(expectatedProductCartButtonState, actualProductCartButtonState);


        System.out.println("TEST PASSED: TC1: Los Productos mantienen sus detalles al ser seleccionados");

    }
}
