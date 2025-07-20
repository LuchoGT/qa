package e2e.pages;

import java.util.function.Supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage {
    
    public WebDriver web;
    public WebElement product;
    public Supplier<WebElement> productClickable;
    public Supplier<String> productPrice;
    public Supplier<String> productTitle;
    public Supplier<String> productDescription;
    public Supplier<String> productImage;
    public Supplier<WebElement> productCartButton;
    public Supplier<String> getProductCartButtonState;
    public ProductPage(WebDriver web, WebElement product) {
        this.web = web;
        this.product = product;

        this.productClickable = () -> this.product.findElement(By.cssSelector("[data-test=\"inventory-item-name\"]"));

        this.productPrice = () -> this.product.findElement(By.cssSelector("[data-test=\"inventory-item-price\"]")).getText();
    
        // this.productTitle = () -> this.product.findElement(By.cssSelector("[data-test=\"inventory-item-name\"]")).getText();
   
        this.productTitle = () -> this.productClickable.get().getText();

        this.productDescription = () -> this.product.findElement(By.cssSelector("[data-test=\"inventory-item-desc\"]")).getText();
    
       this.productImage = () -> {
            try {
                // Lista de productos: busca el <img> por clase
                return this.product.findElement(By.cssSelector("img.inventory_item_img")).getAttribute("alt");
            } catch (org.openqa.selenium.NoSuchElementException e) {
                // Detalle de producto: busca el <img> por clase
                return this.product.findElement(By.cssSelector("img.inventory_details_img")).getAttribute("alt");
            }
        };
    
        this.productCartButton = () -> this.product.findElement(By.cssSelector("[data-test=\"add-to-cart-sauce-labs-backpack\"]"));

        this.getProductCartButtonState = () -> this.product.findElement(By.tagName("button")).getText();

    }
    
    public void clickAddToCart(){
        this.productCartButton.get().click();
    }
}
