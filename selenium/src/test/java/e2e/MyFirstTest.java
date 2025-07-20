package e2e;
import java.time.Duration;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MyFirstTest {
    
    @Test
    public void LoginTest(){
        //Generar el driver de  selenium e instarciarlo
        ChromeDriver web = new ChromeDriver();

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

        //imprimir por terminal que la prueba paso
        Assertions.assertTrue(isProductListVisible);
        
        
        web.quit();
        System.out.println("La prueba paso!!!");
    }

      
}

