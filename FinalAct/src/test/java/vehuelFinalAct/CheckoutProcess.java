package vehuelFinalAct;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import vehuelFinalAct.TestComponents.BaseTest;
import vehuelFinalAct.pageobjects.CartPage;
import vehuelFinalAct.pageobjects.CheckoutPage;
import vehuelFinalAct.pageobjects.Inventory;
import vehuelFinalAct.pageobjects.LandingPage;
import vehuelstratpoint.data.dataDriven;

public class CheckoutProcess extends BaseTest {
	
	private dataDriven d;
    private ArrayList<ArrayList<String>> userData;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String zipcode;
    
    @BeforeClass
    public void setup() throws IOException {
        // Initialize dataDriven and fetch data only once
        d = new dataDriven();
        userData = d.getData();
        username = userData.get(0).get(0);
        password = userData.get(0).get(1);
        firstname =userData.get(0).get(2);
        lastname =userData.get(0).get(3);
        zipcode =userData.get(0).get(4);
        
    }
	
	String productName = "Sauce Labs Backpack";
	
	@Test(priority = 1)
	public void verifyCheckoutInformationPage() throws InterruptedException {
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.goToCheckout();
		String currentURL = driver.getCurrentUrl();
		Assert.assertEquals("https://www.saucedemo.com/checkout-step-one.html", currentURL);
		Thread.sleep(2000);
		
	}
	
	@Test(priority = 2)
	public void verifyCheckoutInformation() throws InterruptedException {
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.goToCheckout();
		// Verify all required details are displayed
        Assert.assertTrue(checkoutPage.isTitleDisplayed(), "Title is not visible.");
        Assert.assertTrue(checkoutPage.isFirstNameFieldDisplayed(), "First Name Field placeholder mismatch.");
        Assert.assertTrue(checkoutPage.isLastNameFieldDisplayed(), "Last Name Field placeholder mismatch.");
        Assert.assertTrue(checkoutPage.isZipCodeFieldDisplayed(), "Zip Code Field placeholder mismatch.");
        
        
        Assert.assertTrue(checkoutPage.isCancelButtonDisplayed(), "Cancel Button is not visible.");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("continue")));
        Assert.assertTrue(checkoutPage.isContinueButtonDisplayed(), "Continue Button is not visible.");
		Thread.sleep(2000);
		
	}
	
	@Test(priority = 3)
	public void verifyCancelButtonClicked() throws InterruptedException {
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.goToCheckout();
		checkoutPage.cancelButtonClicked();
		String currentURL = driver.getCurrentUrl();
		Assert.assertEquals("https://www.saucedemo.com/cart.html", currentURL);
		
	}
	
	@Test(priority = 4)
	public void verifyCheckoutWithBlankFields() throws InterruptedException {
		
	    // Login and navigate to Checkout Page
	    Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart("Sauce Labs Backpack"); 
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();

	    // Click Continue without filling any field
	    checkoutPage.continueButtonClicked();

	    // Verify error messages
	    Assert.assertTrue(checkoutPage.isErrorMessageDisplayed("Error: First Name is required"), "First Name error message not displayed.");
	   
	}
	
	@Test(priority = 5)
	public void missingUsername() throws IOException, InterruptedException { // username field is left blank and others filled
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart("Sauce Labs Backpack"); 
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    
	    checkoutPage.enterDetails("", lastname , zipcode);
	    
	    // Verify error messages
	    Assert.assertTrue(checkoutPage.isErrorMessageDisplayed("Error: First Name is required"), "First Name error message not displayed.");
		
	}
	
	@Test(priority = 6)
	public void missingPassword() throws IOException, InterruptedException { // password field is left blank and others filled
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart("Sauce Labs Backpack"); 
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    
	    checkoutPage.enterDetails(firstname, "" , zipcode);
	    
	    // Verify error messages
	    Assert.assertTrue(checkoutPage.isErrorMessageDisplayed("Error: Last Name is required"), "Last Name error message not displayed.");
		
	}
	
	@Test(priority = 7)
	public void missingZipCode() throws IOException, InterruptedException { // Zip/Postal field is left blank and others filled
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart("Sauce Labs Backpack"); 
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    
	    checkoutPage.enterDetails(firstname, lastname , "");
	    
	    // Verify error messages
	    Assert.assertTrue(checkoutPage.isErrorMessageDisplayed("Error: Postal Code is required"), "Postal Code error message not displayed.");
		
	}
	
	@Test(priority = 8)
	public void onlyUsernameFilled() throws IOException, InterruptedException { // username field is filled while others is blank
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart("Sauce Labs Backpack"); 
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    
	    checkoutPage.enterDetails(firstname, "" , "");
	    
	    
	    Assert.assertTrue(checkoutPage.isErrorMessageDisplayed("Error: Last Name is required"), "Last Name error message not displayed.");
		
	}
	
	@Test(priority = 9)
	public void onlyPasswordFilled() throws IOException, InterruptedException { // password field is filled while others is blank
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart("Sauce Labs Backpack"); 
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    
	    checkoutPage.enterDetails("", lastname , "");
	    
	    
	    Assert.assertTrue(checkoutPage.isErrorMessageDisplayed("Error: First Name is required"), "First Name error message not displayed.");
		
	}
	
	@Test(priority = 10)
	public void onlyZipCodeFilled() throws IOException, InterruptedException { // Zip/Postal field is filled while others is blank
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart("Sauce Labs Backpack"); 
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    
	    checkoutPage.enterDetails("", "" , zipcode);
	    
	    
	    Assert.assertTrue(checkoutPage.isErrorMessageDisplayed("Error: First Name is required"), "First Name error message not displayed.");
		
	}
	
	@Test(priority = 11)
	public void allFieldsFilled() throws IOException, InterruptedException { // All field is filled, click continue
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart("Sauce Labs Backpack"); 
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    
	    checkoutPage.enterDetails(firstname, lastname , zipcode);
	    
	    Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html", "User did not navigate to the expected page.");

	}
	

}
