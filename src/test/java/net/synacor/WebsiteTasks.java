package net.synacor;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WebsiteTasks {

    WebDriver driver;

    @BeforeMethod
    public void setup(){
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        driver.get("https://aperture.synacor.net/");
    }


    @Test
    public void negativeLoginFunctionality(){

        Faker faker = new Faker();

        String invalidUserName = faker.name().username();

        String invalidPassWord = faker.lorem().characters();

        WebElement logIn = driver.findElement(By.xpath("//button[@data-tracking-componentgroup='primaryLinks']"));

        logIn.click();

        WebElement userName = driver.findElement(By.id("username"));

        userName.sendKeys(invalidUserName);

        WebElement passWord = driver.findElement(By.id("password"));

        passWord.sendKeys(invalidPassWord);

        WebElement loginButton = driver.findElement(By.id("login"));

        loginButton.click();

        String expectedResult = "The email address or password you entered is incorrect. Please try again.";

        String actualResult = driver.findElement(By.xpath("//li[@class='text-sm text-center mb-1']")).getText();

        Assert.assertEquals(expectedResult, actualResult, "Expected result and actual result not match");


    }

    @Test
    public void titleVerification(){

        String expectedTitle = "Home - Welcome to Aperture";

        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle, expectedTitle, "Title is not match");

    }

    @Test
    public void searchFunctionality(){

        WebElement searchFunction = driver.findElement(By.name("q"));

        searchFunction.sendKeys("nasa");

        WebElement searchButton = driver.findElement(By.xpath("//button[.='Search']"));

        searchButton.click();

        WebElement expectedWebSites = driver.findElement(By.xpath("//a[.='NASA']"));

        String expectedResult = "https://www.nasa.gov/";

        String actualResult = expectedWebSites.getAttribute("href");

        Assert.assertEquals(actualResult, expectedResult, "Results are not match");

        expectedWebSites.click();


    }


    @AfterMethod
    public void tearDown(){
        driver.quit();
    }



}
