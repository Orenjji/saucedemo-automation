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
import vehuelFinalAct.pageobjects.OrderPage;
import vehuelstratpoint.data.dataDriven;

public class OrderCompletion extends BaseTest {
	
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

	@Test(priority = 1)
	public void verifyFinishButton() throws IOException, InterruptedException {
	    Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart("Sauce Labs Backpack");
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    checkoutPage.enterDetails(firstname, lastname, zipcode);

	    checkoutPage.clickedFinishButton();
	    
	    OrderPage orderPage = new OrderPage(driver);

	    Assert.assertTrue(orderPage.isThankYouMessageDisplayed(), "Thank you message is missing on checkout complete page.");
	}
	
	@Test(priority = 2)
	public void verifyProceedToCheckoutCompletePage() throws IOException, InterruptedException {
		
	    Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart("Sauce Labs Backpack");
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    checkoutPage.enterDetails(firstname, lastname, zipcode);

	    checkoutPage.clickedFinishButton();

	    Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-complete.html", "User did not navigate to the expected page.");
	}
	
	@Test(priority = 3)
	public void verifyRequiredDetailsOnCheckoutCompletePage() throws IOException, InterruptedException {
	    // Add product to cart and proceed to checkout
	    Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart("Sauce Labs Backpack");
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    checkoutPage.enterDetails(firstname, lastname, zipcode);

	    OrderPage orderPage = new OrderPage(driver);
	    checkoutPage.clickedFinishButton();
	    
	    
	    Assert.assertTrue(orderPage.areRequiredDetailsDisplayed(), "Thank you message and Back Home button is missing");
	}
	
	@Test(priority = 4)
	public void verifyBackHomeButton() throws IOException, InterruptedException {
	    // Add product to cart and proceed to checkout
	    Inventory productCatalogue = landingPage.loginApplication(username, password);
	    productCatalogue.addProductToCart("Sauce Labs Backpack");
	    CartPage cartPage = productCatalogue.goToCartPage();
	    CheckoutPage checkoutPage = new CheckoutPage(driver);
	    checkoutPage.goToCheckout();
	    checkoutPage.enterDetails(firstname, lastname, zipcode);

	    OrderPage orderPage = new OrderPage(driver);
	    checkoutPage.clickedFinishButton();
	    
	    orderPage.clickBackHomeButton();
	    
	    Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "User did not navigate to the expected page.");
	}

}
