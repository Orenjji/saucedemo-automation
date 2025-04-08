package vehuelFinalAct;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import vehuelFinalAct.TestComponents.BaseTest;
import vehuelFinalAct.pageobjects.CartPage;
import vehuelFinalAct.pageobjects.CheckoutPage;
import vehuelFinalAct.pageobjects.Inventory;
import vehuelstratpoint.data.dataDriven;

public class CheckoutOverview extends BaseTest {
	
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
	public void accessOverviewPage() throws IOException, InterruptedException { // redirect to Checkout Overview Page
		
		Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart(productName); 
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    
	    checkoutPage.enterDetails(firstname, lastname , zipcode);
	    
	    // Verify after clicking continue button, user gets redirected to checkout step two
	    Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html", "User did not navigate to the expected page.");

	}
	
	@Test(priority = 2)
	public void verifyCheckoutOverviewDetails() throws IOException, InterruptedException {
		
	    Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart(productName); 
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    
	    checkoutPage.enterDetails(firstname, lastname , zipcode);

	    Assert.assertTrue(checkoutPage.isProductDisplayed("Sauce Labs Backpack"), "Product is not displayed in checkout overview.");
	    Assert.assertTrue(checkoutPage.isPaymentInfoDisplayed(), "Payment Information is missing.");
	    Assert.assertTrue(checkoutPage.isShippingInfoDisplayed(), "Shipping Information is missing.");
	    Assert.assertTrue(checkoutPage.isPriceTotalDisplayed(), "Price Total details are missing.");
	    Assert.assertTrue(checkoutPage.isCancelButtonDisplayed(), "Cancel button is missing.");
	    Assert.assertTrue(checkoutPage.isFinishButtonDisplayed(), "Finish button is missing.");
	}
	
	@Test(priority = 3)
	public void verifyCancelButtonRedirectsToProductListing() throws IOException, InterruptedException {
		
	    Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart(productName);
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    checkoutPage.enterDetails(firstname, lastname, zipcode);
	    
	    checkoutPage.cancelButtonClicked();
	    

	    Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "User was not redirected to product listing page.");
	    
	}
	
	 @Test(priority = 4)
	 public void ItemsDetailedDisplayedInCartPage() throws InterruptedException {
	    	
		Inventory productCatalogue = landingPage.loginApplication(username, password);
	    Thread.sleep(2000);
	    productCatalogue.addProductToCart(productName);
	    CartPage cartPage = productCatalogue.goToCartPage();
	    
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    checkoutPage.enterDetails(firstname, lastname, zipcode);
	    	
	    Assert.assertTrue(checkoutPage.isProductQuantityDisplayed(), "Product Quantity is not displayed");
        Assert.assertTrue(checkoutPage.isProductTitleDisplayed(), "Product Title is not displayed");
        //Assert.assertTrue(cartPage.isProductImageDisplayed(), "Product Image is not displayed");
        Assert.assertTrue(checkoutPage.isProductDescriptionDisplayed(), "Product Description is not displayed");
        Assert.assertTrue(checkoutPage.isProductPriceDisplayed(), "Product Price is not displayed");


	}
	 
	 @Test(priority = 5)
	 public void verifyTotalPriceCalculation() throws IOException, InterruptedException {
		 
	     Inventory productCatalogue = landingPage.loginApplication(username, password);
	     productCatalogue.addProductToCart("Sauce Labs Backpack");
	     CartPage cartPage = productCatalogue.goToCartPage();
	     CheckoutPage checkoutPage = new CheckoutPage(driver);
	     checkoutPage.goToCheckout();
	     checkoutPage.enterDetails(firstname, lastname, zipcode);
	     
	     
	     String expectedTotal = checkoutPage.expectedTotal();
	     String actualTotal = checkoutPage.calculateTotal();
	     Assert.assertEquals(actualTotal, expectedTotal);
	 }

	
}
